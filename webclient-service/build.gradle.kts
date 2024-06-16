object Versions {
    const val openFeignVersion = "2.2.3.RELEASE"
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:${Versions.openFeignVersion}")
    implementation("io.github.openfeign:feign-jackson:12.0")
    implementation(project(":webflux-service"))
}
