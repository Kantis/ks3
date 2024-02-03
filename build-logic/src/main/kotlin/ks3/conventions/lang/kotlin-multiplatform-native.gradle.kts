package ks3.conventions.lang

import ks3.conventions.Ks3BuildLogicSettings

// conventions for a Kotlin/Native subproject

plugins {
   id("ks3.conventions.lang.kotlin-multiplatform-base")
}

val ks3Settings = extensions.getByType<Ks3BuildLogicSettings>()

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

      linuxX64()

      mingwX64()

      macosX64()
      macosArm64()

      iosX64()
      iosArm64()
      iosSimulatorArm64()

      watchosX64()
      watchosArm32()
      watchosArm64()
      watchosSimulatorArm64()

      tvosX64()
      tvosArm64()
      tvosSimulatorArm64()

      // Targets inherit in a natural way since Kotlin 1.9
      // https://kotlinlang.org/docs/multiplatform-hierarchy.html#default-hierarchy-template
   }
}
