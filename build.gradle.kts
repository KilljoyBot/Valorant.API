import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"

    maven
}

group = "com.github.killjoybot"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("org.json:json:20201115")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.useIR = true
        kotlinOptions.jvmTarget = "1.8"
    }
}