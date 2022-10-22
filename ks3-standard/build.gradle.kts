@Suppress("DSL_SCOPE_VIOLATION")
plugins {
   id("ks3-multiplatform-library-conventions")
   alias(libs.plugins.kotest.multiplatform)
   alias(libs.plugins.kotlin.plugin.serialization)
}

dependencies {
   implementation(libs.kotlinx.serialization.core)
   testImplementation(libs.kotest.assertions.json)
   testImplementation(libs.kotest.runner.junit5)
   testImplementation(libs.kotlinx.serialization.json)
}
