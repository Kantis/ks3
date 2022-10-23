rootProject.name = "build-logic"

apply(from = "repositories.gradle.kts")

dependencyResolutionManagement {
   @Suppress("UnstableApiUsage")
   versionCatalogs {
      create("libs") {
         from(files("../gradle/libs.versions.toml"))
      }
   }
}
