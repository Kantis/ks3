import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Note: AWS supports only JDK 11 for Lambdas so far
val targetJdk = JavaVersion.VERSION_17

plugins {
    kotlin("jvm") version "1.7.20"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.property)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = targetJdk.toString()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
