import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
   `kotlin-dsl`
   kotlin("jvm") version embeddedKotlinVersion
}

dependencies {
   implementation(libs.gradlePlugin.kotlin)
   implementation(libs.gradlePlugin.kotlinSerialization)
   implementation(libs.gradlePlugin.testlogger)
   implementation(libs.gradlePlugin.kotest)
   implementation(libs.gradlePlugin.dokkatoo)
   implementation(libs.gradlePlugin.ktlint)
   implementation(libs.gradlePlugin.kotlinBinaryCompatibilityValidator)

   testImplementation(libs.kotest.runnerJunit5)
}

val buildLogicJvmTarget = "11"

kotlin {
   jvmToolchain {
      languageVersion.set(JavaLanguageVersion.of(buildLogicJvmTarget))
   }
}

tasks.withType<KotlinCompile>().configureEach {
   kotlinOptions {
      jvmTarget = buildLogicJvmTarget
   }
}

tasks.withType<Test>().configureEach {
   useJUnitPlatform()
}

kotlinDslPluginOptions {
   jvmTarget.set(buildLogicJvmTarget)
}
