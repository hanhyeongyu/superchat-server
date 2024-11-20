dependencies{
    implementation(project(":platform"))
    implementation(project(":security"))

    compileOnly("org.springframework:spring-context")
    compileOnly("org.springframework:spring-tx")
    compileOnly("org.springframework.boot:spring-boot-starter-data-jpa")
}