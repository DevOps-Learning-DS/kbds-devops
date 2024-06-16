pluginManagement {
    plugins {
        kotlin("jvm") version "1.9.23"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "kbds-devops"
include("webflux-service")
include("auth-service")
include("join-service")
include("event-service")
include("push-service")
include("memberinfo-service")
include("devops-library")
include("webclient-service")
