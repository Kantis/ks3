plugins {
   id("ks3.conventions.lang.kotlin-multiplatform-js")
   id("ks3.conventions.lang.kotlin-multiplatform-jvm")
   id("ks3.conventions.lang.kotlin-multiplatform-native")
   id("ks3.conventions.publishing.maven-publish")
}

dokkatoo {
   dokkatooSourceSets.configureEach {
      includes.from("Module.md")
   }

   dokkatooSourceSets.named("commonMain") {
      sourceLink {
         localDirectory.set(file("src/commonMain/kotlin"))
         remoteUrl("https://github.com/Kantis/ks3/blob/main/ks3-standard/src/commonMain/kotlin")
         remoteLineSuffix.set("#L")
      }
   }

   dokkatooSourceSets.named("jvmMain") {
      sourceLink {
         localDirectory.set(file("src/jvmMain/kotlin"))
         remoteUrl("https://github.com/Kantis/ks3/blob/main/ks3-standard/src/jvmMain/kotlin")
         remoteLineSuffix.set("#L")
      }
   }

   modulePath.set("ks3-standard") // match the original dokka default
}

tasks.withType<dev.adamko.dokkatoo.tasks.DokkatooGenerateTask>().configureEach {
   generator.dokkaSourceSets.configureEach {
      sourceSetScope.set(":ks3-standard:dokkaHtmlPartial")
   }
}

kotlin {
   sourceSets {
      commonMain {
         dependencies {
            implementation(libs.kotlinxSerialization.core)
            implementation(libs.kotlinxSerialization.json)
         }
      }

      commonTest {
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
         jvmMain {
            dependencies {
               implementation(kotlin("reflect"))
               implementation(projects.ks3Core)
            }
         }

         jvmTest {
            dependencies {
               implementation(libs.kotest.runnerJunit5)
            }
         }
      }
   }
}
