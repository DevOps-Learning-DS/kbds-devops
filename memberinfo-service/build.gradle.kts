dependencies {
    val jsonFormatJsrVer = rootProject.extra["jsonFormatJsr"]
    val embeddedMongoVer = rootProject.extra["embeddedMongo"]

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jsonFormatJsrVer")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:${embeddedMongoVer}")
}