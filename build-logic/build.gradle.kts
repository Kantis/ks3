import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
   `kotlin-dsl`
   kotlin("jvm") version embeddedKotlinVersion
}

dependencies {
   implementation(platform(libs.kotlin.bom))
   implementation(libs.gradlePlugin.kotlin)
   implementation(libs.gradlePlugin.kotlinSerialization)
   implementation(libs.gradlePlugin.testlogger)
   implementation(libs.gradlePlugin.gitVersioning)
   implementation(libs.gradlePlugin.kotest)
   implementation(libs.gradlePlugin.dokka)
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

kotlinDslPluginOptions {
   jvmTarget.set(buildLogicJvmTarget)
}
