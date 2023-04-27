package ks3.conventions.lang

import ks3.conventions.Ks3BuildLogicSettings
import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
import org.jetbrains.kotlin.gradle.testing.KotlinTaskTestRun

plugins {
   id("ks3.conventions.base")
   kotlin("multiplatform")
   kotlin("plugin.serialization")
   id("io.kotest.multiplatform")
}

// Base configuration for all Kotlin/Multiplatform conventions.
// This plugin does not enable any Kotlin target. To enable a target in a subproject, prefer
// applying specific Kotlin target convention plugins.


val ks3Settings = extensions.getByType<Ks3BuildLogicSettings>()


kotlin {
   jvmToolchain {
      languageVersion.set(JavaLanguageVersion.of(ks3Settings.jvmTarget.get()))
   }

   sourceSets {
      all {
         languageSettings.optIn("io.ks3.core.Ks3Internal")
         languageSettings.optIn("kotlin.RequiresOptIn")
      }
   }

   targets.configureEach {
      compilations.configureEach {
         kotlinOptions {
            apiVersion = ks3Settings.kotlinTarget.get()
            languageVersion = ks3Settings.kotlinTarget.get()
         }
      }
   }

   // configure all Kotlin/JVM Tests to use JUnit
   targets.withType<KotlinJvmTarget>().configureEach {
      testRuns.configureEach {
         executionTask.configure {
            useJUnitPlatform()
         }
      }
   }

   sourceSets.configureEach {
      languageSettings {
         languageVersion = ks3Settings.kotlinTarget.get()
         apiVersion = ks3Settings.kotlinTarget.get()
      }
   }
}

// create lifecycle task for each Kotlin Platform, that will run all tests
KotlinPlatformType.values().forEach { kotlinPlatform ->
   val kotlinPlatformName = kotlinPlatform.name.capitalized()

   val testKotlinTargetLifecycleTask = tasks.create("allKotlin${kotlinPlatformName}Tests") {
      group = LifecycleBasePlugin.VERIFICATION_GROUP
      description = "Run all Kotlin/${kotlinPlatformName} tests"
   }

   kotlin.testableTargets.matching {
      it.platformType == kotlinPlatform
   }.configureEach {
      testRuns.configureEach {
         if (this is KotlinTaskTestRun<*, *>) {
            testKotlinTargetLifecycleTask.dependsOn(executionTask)
         }
      }
   }
}
