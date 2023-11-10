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
        implementation ("org.springdoc:springdoc-openapi-starter-webflux-ui:2.2.0")
    }
}

// all service
configure(
    listOf(
        project(":boot:service:admin"),
        project(":boot:service:batch"),
        project(":boot:service:book"),
        project(":boot:service:customer"),
        project(":boot:service:dashboard"),
    )
) {
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
        // implementation(project(":data:customer"))
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