
pipeline {
    agent any
    options {
        disableConcurrentBuilds() // 파이프 라인의 동시 실행 X
    }
    triggers {
        pollSCM('H/2 * * * *') // 2분마다 pollSCM
    }
    stages {
        def affectedServices;
        stage('get affected services') {
            steps {
                // GitChangeSetList
                script {
                    if (currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause').size() > 0) {
                        affectedServices = sh 'gradle getAllServices'
                        return
                    }
                    // 변경된 파일목록 생성
                    def changes = []
                    def changeLogSets = currentBuild.changeSets
                    for (int i = 0; i < changeLogSets.size(); i++) {
                        def entries = changeLogSets[i].items
                        for (int j = 0; j < entries.length; j++) {
                            def entry = entries[j] // ChangeLogSet.Entry
                            def files = new ArrayList(entry.affectedFiles) // ChangeLogSet.AffectedFile
                            for (int k = 0; k < files.size(); k++) {
                                def file = files[k]
                                // echo "filepath: ${file.path}, editType:${file.editType.name}"
                                changes.add(file.path)
                            }
                        }
                    }
                    def modulePaths = new HashSet<String>() // 변경된 모듈 paths
                    for(def change : changes) {
                        if (change.endsWith("build.gradle.kts")) {
                            // build.gradle.kts 가 변경되었다면 모든 서비스 빌드
                            affectedServices = sh 'gradle getAllServices'
                            break
                        } else {
                            if (change.startsWith("boot")) {
                                def cs = change.split("/")
                                if (cs != null && cs.size() >= 3) {
                                    def modulePath = cs[0..2].join(':')
                                    modulePaths.add(modulePath)
                                }
                            }
                            else if(change.startsWith("data")) {
                                def cs = change.split("/")
                                if (cs != null && cs.size() >= 2) {
                                    def modulePath = cs[0..1].join(':')
                                    modulePaths.add(modulePath)
                                }
                            }
                        }
                    }
                    if (modulePaths.size() != 0) {
                        def inputs = modulePaths.join(',')
                        echo inputs
                        gradleResult = sh "gradle getAffectedServices -P${inputs}"
                    }
                    print(gradleResult)
                } // end script
            }
        }
        stage('build service') {
            steps {
                // TODO
            }
        }
    }
}