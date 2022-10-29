package ks3.conventions.lang

import ks3.conventions.Ks3BuildLogicSettings
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
   id("ks3.conventions.base")
   kotlin("jvm")
   kotlin("plugin.serialization")
}


val ks3Settings = extensions.getByType<Ks3BuildLogicSettings>()


tasks.withType<KotlinCompile>().configureEach {

   kotlinOptions {
      jvmTarget = ks3Settings.jvmTarget.get()
      apiVersion =  ks3Settings.kotlinTarget.get()
      languageVersion =  ks3Settings.kotlinTarget.get()
   }

   kotlinOptions.freeCompilerArgs += listOf(
      "-opt-in=kotlin.RequiresOptIn",
      "-opt-in=kotlin.ExperimentalStdlibApi",
      "-opt-in=kotlin.time.ExperimentalTime",
   )
}


kotlin {
   jvmToolchain {
      languageVersion.set(JavaLanguageVersion.of(ks3Settings.jvmTarget.get()))
   }
}


tasks.withType<Test>().configureEach {
   useJUnitPlatform()
}
