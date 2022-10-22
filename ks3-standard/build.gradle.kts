plugins {
   id("ks3-jvm-library-conventions")
}

dependencies {
   implementation(libs.kotlinx.serialization.core)
   testImplementation(libs.kotest.runner.junit5)
   testImplementation(libs.kotlinx.serialization.json)
}
