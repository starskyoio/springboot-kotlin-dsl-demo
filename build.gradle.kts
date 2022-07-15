buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.google.cloud.tools.jib")
    id("java")
}

val springBootVersion= "2.6.9"
val springCloudVersion = "2021.0.3"
val springCloudAlibabaVersion = "2021.0.1.0"

allprojects {
    group = "io.starskyoio"
    version = "${properties["version"]}"

    apply {
        plugin("java")
        plugin("io.spring.dependency-management")
    }

//    apply(plugin = "java")
//    apply(plugin = "io.spring.dependency-management")

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    repositories {
        mavenLocal()
        mavenCentral()
    }


    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:${springBootVersion}")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
            mavenBom("com.alibaba.cloud:spring-cloud-alibaba-dependencies:${springCloudAlibabaVersion}")
        }
    }


    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }
}

subprojects {
    if(name.endsWith("server")){
        apply {
            plugin("org.springframework.boot")
            plugin("com.google.cloud.tools.jib")
        }
        jib {
            from {
                image = "registry.cn-chengdu.aliyuncs.com/dlhope-common/zulu-openjdk-apline:8u322-jre-headless"
            }
            to {
                image = "registry.cn-chengdu.aliyuncs.com/dlhope/${project.name}"
                auth {
                    username = "${properties["dockerUser"]}"
                    password =  "${properties["dockerPwd"]}"
                }
                tags = setOf("${project.version}", "latest")
            }
            container {
                creationTime = "USE_CURRENT_TIMESTAMP"
                jvmFlags = listOf(
                        "-Djava.security.egd=file:/dev/./urandom",
                        "-Dfile.encoding=utf-8",
                        "-Duser.timezone=GMT+08"
                )
                ports = listOf("8080")
            }
        }
    }


    dependencies {
        if(name.endsWith("server")){
            implementation(project(":common"))
            implementation("org.springframework.boot:spring-boot-starter-web")
            testImplementation("org.springframework.boot:spring-boot-starter-test")
        }
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        testCompileOnly("org.projectlombok:lombok")
        testAnnotationProcessor("org.projectlombok:lombok")
    }

}
