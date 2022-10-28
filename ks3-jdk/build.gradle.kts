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
            implementation(platform(libs.kotest.bom))

            implementation(kotlin("test"))

            implementation(projects.ks3Test)

            implementation(libs.kotest.frameworkEngine)
            implementation(libs.kotest.assertionsCore)
            implementation(libs.kotest.assertionsJson)
            implementation(libs.kotest.property)

            implementation(libs.kotlinxSerialization.json)
         }
      }

      val jvmMain by getting {
         dependencies {
            implementation(projects.ks3Standard)
         }
      }

      val jvmTest by getting {
         dependencies {
            implementation(libs.kotest.runnerJunit5)
         }
      }
   }
}
