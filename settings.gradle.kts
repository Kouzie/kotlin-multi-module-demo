rootProject.name = "demo"

include(
    "boot:core:web",

    "boot:service:admin",
    "boot:service:batch",
    "boot:service:book",
    "boot:service:customer",
    "boot:service:dashboard",

    "data:book-data",
    "data:customer-data",
    "data:reply-data",
)

pluginManagement {
    plugins{
        kotlin("jvm") version "1.7.22"
        kotlin("plugin.spring") version "1.7.22"
        id("org.springframework.boot") version "3.0.12"
        id("io.spring.dependency-management") version "1.1.3"

    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}