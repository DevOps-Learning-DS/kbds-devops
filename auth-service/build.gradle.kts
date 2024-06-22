object Version {
    const val jsr = "2.13.5"
    const val embeddedMongo = "2.13.5"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:${Version.embeddedMongo}")
}

tasks.test {
    useJUnitPlatform()
}