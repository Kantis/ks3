package ks3.conventions.lang

import ks3.conventions.Ks3BuildLogicSettings
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
import java.awt.GridBagConstraints.BOTH

plugins {
   id("ks3.conventions.base")
   kotlin("multiplatform")
   kotlin("plugin.serialization")
   id("io.kotest.multiplatform")
}


val ks3BuildLogicSettings = extensions.getByType<Ks3BuildLogicSettings>()


extensions.configure<KotlinMultiplatformExtension> {
//kotlin {
   targets.configureEach {
      compilations.configureEach {
         kotlinOptions {
            apiVersion = ks3BuildLogicSettings.kotlinTarget.get()
            languageVersion = ks3BuildLogicSettings.kotlinTarget.get()
         }
      }
   }
   targets.withType<KotlinJvmTarget>().configureEach {
      testRuns["test"].executionTask.configure {
         useJUnitPlatform()
      }
   }
   sourceSets.configureEach {
      languageSettings {
         languageVersion = ks3BuildLogicSettings.kotlinTarget.get()
         apiVersion = ks3BuildLogicSettings.kotlinTarget.get()
      }
   }
}


if (ks3BuildLogicSettings.enableKotlinMultiplatformJvm.get()) {
   kotlin {
      jvm {
         withJava()
      }
   }
}


if (ks3BuildLogicSettings.enableKotlinMultiplatformJs.get()) {
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


if (ks3BuildLogicSettings.enableKotlinMultiplatformNative.get()) {
   kotlin {
      targets {
         linuxX64()

         mingwX64()

         macosX64()
         macosArm64()

         tvos()
         tvosSimulatorArm64()

         watchosArm32()
         watchosArm64()
         watchosX86()
         watchosX64()
         watchosSimulatorArm64()

         iosX64()
         iosArm64()
         iosArm32()
         iosSimulatorArm64()
      }

      @Suppress("UNUSED_VARIABLE")
      sourceSets {

         // Main source sets
         val commonMain by getting {}

         val desktopMain by creating { dependsOn(commonMain) }

         val macosX64Main by getting { dependsOn(desktopMain) }
         val macosArm64Main by getting { dependsOn(desktopMain) }

         val mingwX64Main by getting { dependsOn(desktopMain) }

         val linuxX64Main by getting { dependsOn(desktopMain) }

         val iosX64Main by getting { dependsOn(desktopMain) }
         val iosArm64Main by getting { dependsOn(desktopMain) }
         val iosArm32Main by getting { dependsOn(desktopMain) }
         val iosSimulatorArm64Main by getting { dependsOn(desktopMain) }

         val watchosArm32Main by getting { dependsOn(desktopMain) }
         val watchosArm64Main by getting { dependsOn(desktopMain) }
         val watchosX86Main by getting { dependsOn(desktopMain) }
         val watchosX64Main by getting { dependsOn(desktopMain) }
         val watchosSimulatorArm64Main by getting { dependsOn(desktopMain) }

         val tvosMain by getting { dependsOn(desktopMain) }
         val tvosSimulatorArm64Main by getting { dependsOn(desktopMain) }

         // Test source sets
         val commonTest by getting

         val nativeTest by creating { dependsOn(commonTest) }

         val macosX64Test by getting { dependsOn(nativeTest) }
         val macosArm64Test by getting { dependsOn(nativeTest) }

         val mingwX64Test by getting { dependsOn(nativeTest) }

         val linuxX64Test by getting { dependsOn(nativeTest) }

         val iosX64Test by getting { dependsOn(nativeTest) }
         val iosArm64Test by getting { dependsOn(nativeTest) }
         val iosArm32Test by getting { dependsOn(nativeTest) }
         val iosSimulatorArm64Test by getting { dependsOn(nativeTest) }

         val watchosArm32Test by getting { dependsOn(nativeTest) }
         val watchosArm64Test by getting { dependsOn(nativeTest) }
         val watchosX86Test by getting { dependsOn(nativeTest) }
         val watchosX64Test by getting { dependsOn(nativeTest) }
         val watchosSimulatorArm64Test by getting { dependsOn(nativeTest) }

         val tvosTest by getting { dependsOn(nativeTest) }
         val tvosSimulatorArm64Test by getting { dependsOn(nativeTest) }
      }
   }
}
