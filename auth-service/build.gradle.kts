
dependencies {
    val embeddedMongoVer = rootProject.extra.get("embeddedMongo")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:${embeddedMongoVer}")
}
