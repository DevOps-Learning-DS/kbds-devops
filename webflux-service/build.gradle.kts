object Version {
    const val jsr = "2.13.5"
    const val embeddedMongo = "2.13.5"
}

dependencies {
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Version.jsr}")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:${Version.embeddedMongo}")
}
repositories {
    mavenCentral()
}
