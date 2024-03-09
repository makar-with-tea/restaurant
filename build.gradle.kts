plugins {
    kotlin("jvm") version "1.9.22"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.0-rc1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(20)
}