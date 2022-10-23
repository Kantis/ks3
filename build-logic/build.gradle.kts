plugins {
   `kotlin-dsl`
}

dependencies {
   implementation(platform(libs.kotlin.bom))
   implementation(libs.gradlePlugin.kotlin)
   implementation(libs.gradlePlugin.kotlinSerialization)
   implementation(libs.gradlePlugin.testlogger)
   implementation(libs.gradlePlugin.kotest)
}
