plugins {
    id("java")
    id("org.springframework.boot") version "2.5.6" apply false
    id("io.spring.dependency-management") version "1.0.15.RELEASE" apply false
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-web"){
            exclude(module = "spring-boot-starter-tomcat")
        }
        implementation("org.springframework.boot:spring-boot-starter-jetty")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        testImplementation("org.springframework.boot:spring-boot-starter-test")

        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")

        testImplementation(platform("org.junit:junit-bom:5.9.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    repositories {
        mavenLocal()
        mavenCentral()
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.withType(JavaCompile::class.java) {
        options.encoding = "UTF-8"
    }
}


allprojects {
    group = "com.kbds.devops"
    version = "1.0-SNAPSHOT"

    java {
        sourceCompatibility  = JavaVersion.VERSION_1_8
    }
}
