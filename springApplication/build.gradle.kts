
tasks.getByName("bootJar"){
    enabled = true
}

tasks.getByName("jar"){
    enabled = false
}


dependencies{
    //api
    implementation(project(":api"))

    //spring
    implementation(project(":security"))

    //support
    implementation(project(":support:log"))
    implementation(project(":support:storage"))
    implementation(project(":support:file"))

    implementation("org.springframework.boot:spring-boot-starter-web")
}