package ks3.conventions

import org.gradle.kotlin.dsl.configure

plugins {
   id("org.jetbrains.kotlinx.binary-compatibility-validator")
}

apiValidation {
   @OptIn(kotlinx.validation.ExperimentalBCVApi::class)
   klib {
      enabled = System.getProperty("enableKlibValidation") != null
   }
}
