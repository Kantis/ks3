plugins {
   id("ks3.conventions.lang.kotlin-multiplatform")
   id("ks3.conventions.publishing.maven-publish")
}

kotlin {
   sourceSets {
      val commonMain by getting {
         dependencies {
            implementation(platform(libs.kotlin.bom))
            implementation(platform(libs.kotlinxSerialization.bom))

            implementation(libs.kotlinxSerialization.core)
         }
      }

      val commonTest by getting {
         dependencies {
            implementation(kotlin("test"))

            implementation(platform(libs.kotest.bom))

            implementation(libs.kotest.frameworkEngine)
            implementation(libs.kotest.assertionsCore)
            implementation(libs.kotest.assertionsJson)

            implementation(libs.kotlinxSerialization.json)
         }
      }

      val jvmTest by getting {
         dependencies {
            implementation(libs.kotest.runnerJunit5)
         }
      }
   }
}
