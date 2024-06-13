object Version {
   const val jsr = "2.13.5"
}


dependencies {
   implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Version.jsr}")
   testImplementation("io.projectreactor:reactor-test")
}
