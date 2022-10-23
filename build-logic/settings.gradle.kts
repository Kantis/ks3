rootProject.name = "build-logic"

dependencyResolutionManagement {
   versionCatalogs {
      create("libs") {
         from(files("../gradle/libs.versions.toml"))
      }
   }
}

@Suppress("UnstableApiUsage") // Central declaration of repositories is an incubating feature
dependencyResolutionManagement {

   repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

   repositories {
      mavenCentral()
      gradlePluginPortal()
   }

   pluginManagement {
      repositories {
         gradlePluginPortal()
         mavenCentral()
      }
   }
}
