package ks3.conventions.lang

import ks3.conventions.Ks3BuildLogicSettings
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
   id("ks3.conventions.base")
   kotlin("js")
   kotlin("plugin.serialization")
}


val ks3Settings = extensions.getByType<Ks3BuildLogicSettings>()


tasks.withType<KotlinCompile>().configureEach {
   kotlinOptions {
      apiVersion =  ks3Settings.kotlinTarget.get()
      languageVersion =  ks3Settings.kotlinTarget.get()
   }

   kotlinOptions.freeCompilerArgs += listOf(
      "-opt-in=kotlin.RequiresOptIn",
      "-opt-in=kotlin.ExperimentalStdlibApi",
      "-opt-in=kotlin.time.ExperimentalTime",
   )
}
