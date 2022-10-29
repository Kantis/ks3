plugins {
   id("ks3.conventions.lang.kotlin-jvm")
   id("ks3.conventions.publishing.maven-publish")
}

dependencies {
   implementation(platform(libs.kotlin.bom))
   implementation(platform(libs.kotlinxSerialization.bom))

   implementation(projects.ks3Standard)

   implementation(libs.kotlinxSerialization.core)

   testImplementation(platform(libs.kotest.bom))

   testImplementation(kotlin("test"))

   testImplementation(projects.ks3Test)

   testImplementation(libs.kotest.frameworkEngine)
   testImplementation(libs.kotest.assertionsCore)
   testImplementation(libs.kotest.assertionsJson)
   testImplementation(libs.kotest.property)

   testImplementation(libs.kotlinxSerialization.json)
   testImplementation(libs.kotest.runnerJunit5)
}
