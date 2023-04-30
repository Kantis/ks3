plugins {
   id("ks3.conventions.lang.kotlin-multiplatform-jvm") // Kotlin/JVM only
   id("ks3.conventions.publishing.maven-publish")
}

dokkatoo {
   dokkatooSourceSets.configureEach {
      includes.from("Module.md")
   }

   dokkatooSourceSets.named("jvmMain"){
      sourceLink {
         localDirectory.set(file("src/jvmMain/kotlin"))
         remoteUrl("https://github.com/Kantis/ks3/blob/main/ks3-jdk/src/jvmMain/kotlin")
         remoteLineSuffix.set("#L")
      }
   }

   modulePath.set("ks3-jdk") // match the original dokka default
}

tasks.withType<dev.adamko.dokkatoo.tasks.DokkatooPrepareParametersTask>().configureEach {
   dokkaSourceSets.configureEach {
      sourceSetScope.set(":ks3-jdk:dokkaHtmlPartial")
   }
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
