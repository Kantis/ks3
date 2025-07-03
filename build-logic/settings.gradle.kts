rootProject.name = "build-logic"

apply(from = "repositories.gradle.kts")

plugins {
   id("org.gradle.toolchains.foojay-resolver-convention") version("1.0.0")
}

dependencyResolutionManagement {
   @Suppress("UnstableApiUsage")
   versionCatalogs {
      create("libs") {
         from(files("../gradle/libs.versions.toml"))
      }
   }
}
