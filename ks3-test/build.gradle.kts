plugins {
   id("ks3.conventions.lang.kotlin-multiplatform-js")
   id("ks3.conventions.lang.kotlin-multiplatform-jvm")
   id("ks3.conventions.lang.kotlin-multiplatform-native")
   id("ks3.conventions.publishing.maven-publish")
}

kotlin {
   sourceSets {
      val commonMain by getting {
         dependencies {
            implementation(platform(libs.kotlin.bom))
            implementation(platform(libs.kotlinxSerialization.bom))
            implementation(platform(libs.okio.bom))

            implementation(libs.kotest.property)
            implementation(libs.kotest.frameworkEngine)

            implementation(libs.kotlinxSerialization.core)
            implementation(libs.kotlinxSerialization.json)
            implementation(libs.kotlinxSerialization.jsonOkio)
            implementation(libs.okio.core)
         }
      }
   }
}
