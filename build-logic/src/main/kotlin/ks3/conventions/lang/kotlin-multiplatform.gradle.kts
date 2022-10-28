package ks3.conventions.lang

import ks3.conventions.Ks3BuildLogicSettings
import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
import org.jetbrains.kotlin.gradle.testing.KotlinTaskTestRun

plugins {
   id("ks3.conventions.base")
   kotlin("multiplatform")
   kotlin("plugin.serialization")
   id("io.kotest.multiplatform")
}


val ks3Settings = extensions.getByType<Ks3BuildLogicSettings>()


// quick hacks because IntelliJ is refusing to index generated accessors
val kotlin: KotlinMultiplatformExtension get() = extensions.getByType()
fun kotlin(configure: KotlinMultiplatformExtension.() -> Unit) = extensions.configure(configure)


kotlin {
   jvmToolchain {
      languageVersion.set(JavaLanguageVersion.of(ks3Settings.jvmTarget.get()))
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


if (ks3Settings.enableKotlinJvm.get()) {
   kotlin {
      jvm {
         withJava()
      }
   }
}


if (ks3Settings.enableKotlinJs.get()) {
   kotlin {
      targets {
         js(BOTH) {
            browser()
            nodejs()
         }
      }
   }

   relocateKotlinJsStore()
}


if (ks3Settings.enableKotlinNative.get()) {
   kotlin {

      // Native targets all extend commonMain and commonTest.
      //
      // Some targets (ios, tvos, watchos) are shortcuts provided by the Kotlin DSL, that
      // provide additional targets, except for 'simulators' which must be defined manually.
      // https://kotlinlang.org/docs/multiplatform-share-on-platforms.html#use-target-shortcuts
      //
      // common/
      // └── native/
      //     ├── linuxX64
      //     ├── mingwX64
      //     ├── macosX64
      //     ├── macosArm64
      //     ├── ios/ (shortcut)
      //     │   ├── iosArm64
      //     │   ├── iosX64
      //     │   └── iosSimulatorArm64
      //     ├── tvos/ (shortcut)
      //     │   ├── tvosArm64
      //     │   ├── tvosX64
      //     │   └── tvosSimulatorArm64Main
      //     └── watchos/ (shortcut)
      //         ├── watchosArm32
      //         ├── watchosArm64
      //         ├── watchosX64
      //         └── watchosSimulatorArm64Main

      targets {
         linuxX64()

         mingwX64()

         macosX64()
         macosArm64()

         // https://kotlinlang.org/docs/multiplatform-share-on-platforms.html#use-target-shortcuts
         ios()     // iosArm64, iosX64
         watchos() // watchosArm32, watchosArm64, watchosX64
         tvos()    // tvosArm64, tvosX64

         iosSimulatorArm64()
         tvosSimulatorArm64()
         watchosSimulatorArm64()
      }

      @Suppress("UNUSED_VARIABLE")
      sourceSets {
         val commonMain by getting {}
         val commonTest by getting {}

         val nativeMain by creating { dependsOn(commonMain) }
         val nativeTest by creating { dependsOn(commonTest) }

         // Linux
         val linuxX64Main by getting { dependsOn(nativeMain) }
         val linuxX64Test by getting { dependsOn(nativeTest) }

         // Windows - MinGW
         val mingwX64Main by getting { dependsOn(nativeMain) }
         val mingwX64Test by getting { dependsOn(nativeTest) }

         // Apple - macOS
         val macosArm64Main by getting { dependsOn(nativeMain) }
         val macosArm64Test by getting { dependsOn(nativeTest) }

         val macosX64Main by getting { dependsOn(nativeMain) }
         val macosX64Test by getting { dependsOn(nativeTest) }

         // Apple - iOS
         val iosMain by getting { dependsOn(nativeMain) }
         val iosTest by getting { dependsOn(nativeTest) }

         val iosSimulatorArm64Main by getting { dependsOn(iosMain) }
         val iosSimulatorArm64Test by getting { dependsOn(iosTest) }

         // Apple - tvOS
         val tvosMain by getting { dependsOn(nativeMain) }
         val tvosTest by getting { dependsOn(nativeTest) }

         val tvosSimulatorArm64Main by getting { dependsOn(tvosMain) }
         val tvosSimulatorArm64Test by getting { dependsOn(tvosTest) }

         // Apple - watchOS
         val watchosMain by getting { dependsOn(nativeMain) }
         val watchosTest by getting { dependsOn(nativeTest) }

         val watchosSimulatorArm64Main by getting { dependsOn(watchosMain) }
         val watchosSimulatorArm64Test by getting { dependsOn(watchosTest) }

         // val iosArm32Main by getting { dependsOn(desktopMain) }
         // val iosArm32Test by getting { dependsOn(nativeTest) }
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
