import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
   kotlin("multiplatform")
   id("com.adarshr.test-logger")
   id("ks3-base-conventions")
}

testlogger {
   showPassed = false
}

tasks.withType<Test>().configureEach {
   useJUnitPlatform()
   filter {
      isFailOnNoMatchingTests = false
   }
}

tasks.withType<KotlinCompile>().configureEach {
   kotlinOptions {
      freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
      jvmTarget = "1.8"
      apiVersion = "1.6"
      languageVersion = "1.6"
   }
}

kotlin {
   sourceSets.configureEach {
      languageSettings {
         optIn("kotlin.time.ExperimentalTime")
         optIn("kotlin.experimental.ExperimentalTypeInference")
         optIn("kotlin.contracts.ExperimentalContracts")
      }
   }
}
