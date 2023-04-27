plugins {
   id("ks3.conventions.lang.kotlin-multiplatform-jvm") // Kotlin/JVM only
   id("ks3.conventions.publishing.maven-publish")
}

kotlin {
   sourceSets {
      commonMain {
         dependencies {
            implementation(projects.ks3Core)
            implementation(projects.ks3Standard)
            implementation(libs.kotlinxSerialization.core)
         }
      }

      commonTest {
         dependencies {
            implementation(kotlin("test"))

            implementation(projects.ks3Test)

            implementation(libs.kotest.frameworkEngine)
            implementation(libs.kotest.assertionsCore)
            implementation(libs.kotest.assertionsJson)
            implementation(libs.kotest.property)

            implementation(libs.kotlinxSerialization.json)
         }
      }

      if (ks3Settings.enableKotlinJvm.get()) {
         jvmTest {
            dependencies {
               implementation(libs.kotest.runnerJunit5)
            }
         }
      }
   }
}
