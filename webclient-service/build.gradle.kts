object Versions {
    const val openFeignVersion = "2.2.3.RELEASE"
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:${Versions.openFeignVersion}")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}
