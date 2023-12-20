@NonCPS
def getChanges() {
    def changes = []
    // GitChangeSetList
    def changeLogSets = currentBuild.changeSets
    for (int i = 0; i < changeLogSets.size(); i++) {
        def entries = changeLogSets[i].items
        for (int j = 0; j < entries.length; j++) {
            def entry = entries[j] // ChangeLogSet.Entry
            def files = new ArrayList(entry.affectedFiles) // ChangeLogSet.AffectedFile
            for (int k = 0; k < files.size(); k++) {
                def file = files[k]
                changes.add(file.path)
            }
        }
    }
    return changes
}

def affectedServices = []

pipeline {
    agent any
    options {
        disableConcurrentBuilds() // 파이프 라인의 동시 실행 X
    }
    triggers {
        pollSCM('H/2 * * * *') // 2분마다 pollSCM
    }
    environment {
        CREDENTIALS_ID = 'kgy1996_docker_hub'
    }
    stages {
        stage('get affected services') {
            steps {
                script {
                    if (currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause').size() > 0) {
                        def outputs = sh(script: "./gradlew -q getAllServices", returnStdout: true).trim()
                        echo "output result: $outputs"
                        affectedServices = outputs.split(',')
                        return
                    }
                    echo 'generate chage list'
                    // 변경된 파일목록 생성
                    def changes = getChanges()
                    def modulePaths = new HashSet<String>() // 변경된 모듈 paths
                    for(def change : changes) {
                        if (change.endsWith("build.gradle.kts")) {
                            // build.gradle.kts 가 변경되었다면 모든 서비스 빌드
                            def outputs = sh(script: "./gradlew -q getAllServices", returnStdout: true).trim()
                            echo "output result: $outputs"
                            affectedServices = outputs.split(',')
                            break
                        } else {
                            // directory 구조를 module path 형태로 변환
                            if (change.startsWith("boot")) {
                                def cs = change.split("/")
                                if (cs != null && cs.size() >= 3) {
                                    def modulePath = cs[0..2].join(':')
                                    modulePaths.add(":" + modulePath)
                                }
                            }
                            else if(change.startsWith("data")) {
                                def cs = change.split("/")
                                if (cs != null && cs.size() >= 2) {
                                    def modulePath = cs[0..1].join(':')
                                    modulePaths.add(":" + modulePath)
                                }
                            }
                        }
                    }
                    if (modulePaths.size() != 0) {
                        def inputs = modulePaths.join(',')
                        def outputs = sh(script: "./gradlew -q getAffectedServices -Pmodules=${inputs}", returnStdout: true).trim()
                        echo "output result: $outputs"
                        affectedServices = outputs.split(',')
                    }
                    echo "affectedServices: $affectedServices"
                } // end script
            }
        }
        stage('build affected services') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: CREDENTIALS_ID, passwordVariable: 'CREDENTIALS_PASSWORD', usernameVariable: 'CREDENTIALS_USERNAME')]) {
                        for (def service in affectedServices) {
                            echo "${service} build start"
                            def command = service + ":jib"
                            sh "./gradlew clean ${command} -PdhUsername=${CREDENTIALS_USERNAME} -PdhPassword=${CREDENTIALS_PASSWORD}"
                            echo "${service} build end"
                        }
                    }
                }
            }
        }
    }
}