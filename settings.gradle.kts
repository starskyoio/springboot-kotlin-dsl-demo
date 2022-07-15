rootProject.name = "springboot-kotlin-dsl-demo"

pluginManagement {
    plugins {
        id("org.springframework.boot") version "2.6.9"
        id("io.spring.dependency-management") version "1.0.12.RELEASE"
        id("com.google.cloud.tools.jib") version "3.2.1"
    }
}

include("common")
include("hello-server")
