
dependencies {
    val embeddedMongoVer = rootProject.extra["embeddedMongo"]

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:${embeddedMongoVer}")
}
