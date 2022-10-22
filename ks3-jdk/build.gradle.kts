@Suppress("DSL_SCOPE_VIOLATION")
plugins {
   id("ks3-jvm-library-conventions")
   alias(libs.plugins.kotlin.plugin.serialization)
}

dependencies {
   implementation(libs.kotlinx.serialization.core)
   testImplementation(libs.kotest.runner.junit5)
   testImplementation(libs.kotlinx.serialization.json)
}
