dependencies {
    val jsonFormatJsrVer = rootProject.extra.get("jsonFormatJsr")
    val embeddedMongoVer = rootProject.extra.get("embeddedMongo")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jsonFormatJsrVer")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:${embeddedMongoVer}")
}