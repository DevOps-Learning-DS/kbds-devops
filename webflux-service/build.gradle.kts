plugins {
    kotlin("jvm")
}
object Version {
   const val jsr = "2.13.5"
}


dependencies {
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Version.jsr}")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    testImplementation("io.projectreactor:reactor-test")
    implementation(kotlin("stdlib-jdk8"))
}
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(8)
}