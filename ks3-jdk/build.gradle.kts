plugins {
   id("ks3-jvm-library-conventions")
}

kotlin {
   sourceSets {
      val commonMain by getting {
         dependencies {
            implementation(dependencies.platform(libs.kotlin.bom))
            implementation(dependencies.platform(libs.kotlinxSerialization.bom))

            implementation(libs.kotlinxSerialization.core)
         }
      }

      val commonTest by getting {
         dependencies {
            implementation(dependencies.platform(libs.kotest.bom))

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
