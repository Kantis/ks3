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

   testImplementation(platform(libs.kotest.bom))
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
