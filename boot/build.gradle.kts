import kotlin.collections.emptyList
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.setOf
import kotlin.collections.listOf

plugins {
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management") apply false
    id("com.google.cloud.tools.jib")
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter-json")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-reactor-netty")
        // swagger
        implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.2.0")
    }

    tasks.getByName("bootJar") {
        enabled = false
    }

    tasks.getByName("jar") {
        enabled = true
    }
}

// all service
configure(
    listOf(
        project(":boot:service:admin"),
        project(":boot:service:book"),
        project(":boot:service:customer"),
        project(":boot:service:dashboard"),
    )
) {
    apply(plugin = "com.google.cloud.tools.jib")

    val uniqueBuildId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
    val dockerhubUsername: String = project.properties["dhUsername"].toString()
    val dockerhubPassword: String = project.properties["dhPassword"].toString()
    jib {
        from {
            image = "docker.io/library/openjdk:17-jdk-slim"
        }
        to {
            image = "kgy1996/${project.name}-service"
            tags = setOf(uniqueBuildId)
            // ./gradlew clean :boot:service:amin:jib -PdhUsername=foo -PdhPassword=bar
            auth {
                username = dockerhubUsername
                password = dockerhubPassword
            }
        }
        container {
            creationTime = "USE_CURRENT_TIMESTAMP"
            jvmFlags = listOf(
                "-Dserver.port=8080",
                "-Dfile.encoding=UTF-8",
                "-XshowSettings:vm",
                "-XX:+UseContainerSupport",
                "-XX:MinRAMPercentage=50.0",
                "-XX:MaxRAMPercentage=80.0",
            )
        }
    }

    tasks.getByName("bootJar") {
        enabled = true
    }

    tasks.getByName("jar") {
        enabled = false
    }

    dependencies {
        implementation(project(":boot:core:web"))

        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-starter-aop")

        testImplementation("io.projectreactor:reactor-test")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }
}

// rdb service
configure(
    listOf(
        project(":boot:service:customer"),
    )
) {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
        implementation("io.r2dbc:r2dbc-h2")
    }
}

// es service
configure(
    listOf(
        project(":boot:service:book"),
    )
) {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
    }
}

// customer service
project(":boot:service:customer") {
    dependencies {
        implementation(project(":data:customer-data"))
    }
}

// book service
project(":boot:service:book") {
    dependencies {
        implementation(project(":data:book-data"))
    }
}

// dashboard service
project(":boot:service:dashboard") {
    dependencies {
        implementation(project(":data:book-data"))
        implementation(project(":data:customer-data"))
    }
}

/**
 * 해당 모듈로부터 의존되는 서비스 목록 반환
 * @param input 변화한 모듈 리스트
 * */
fun getAffectedServices(vararg input: String): Set<String> {
    val result = mutableSetOf<String>() // affected service list
    val services = rootProject.allprojects.filter { it.path.startsWith(":boot:service:") }
    services.forEach { service ->
        val implementationDependencies = service.configurations["implementation"].dependencies
        val projectDependencies = implementationDependencies
            .filterIsInstance<ProjectDependency>() // 직접 구현한 dependency 필터링
            .map { it.dependencyProject }
        // module path 에 현재 서비스도 추가
        val modulePaths: List<String> = projectDependencies.map { it.path } + service.path
        // input 과 겹치는 module path 가 있다면 의존성이 있다고 판단
        if (input.intersect(modulePaths).isNotEmpty()) {
            result.add(service.path)
        }
    }
    return result;
}

tasks.register("getAffectedServices") {
    val inputs: List<String> =
        project.findProperty("modules")?.toString()?.split(",") ?: emptyList()
    doLast {
        val services = getAffectedServices(*inputs.toTypedArray())
        println(services.joinToString(","))
    }
}

tasks.register("getAllServices") {
    doLast {
        val services = rootProject.allprojects
            .filter { it.path.startsWith(":boot:service:") }
            .map { service -> service.path }
            .toSet()
        println(services.joinToString(","))
    }
}
