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
            implementation(libs.kotlinxSerialization.core)
         }
      }

      val commonTest by getting {
         dependencies {
            implementation(kotlin("test"))

            implementation(libs.kotest.frameworkEngine)
            implementation(libs.kotest.assertionsCore)
            implementation(libs.kotest.assertionsJson)
            implementation(libs.kotest.property)

            implementation(libs.okio.core)

            implementation(libs.kotlinxSerialization.json)
         }
      }

      if (ks3Settings.enableKotlinJvm.get()) {
         val jvmTest by getting {
            dependencies {
               implementation(libs.kotest.runnerJunit5)
            }
         }
      }
   }
}
