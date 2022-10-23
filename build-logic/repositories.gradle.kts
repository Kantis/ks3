// Shared repository config, for use in both build-logic and the root project


@Suppress("UnstableApiUsage") // Central declaration of repositories is an incubating feature
dependencyResolutionManagement {

   repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

   repositories {
      mavenCentral()

      // Declare the Node.js download repository
      ivy("https://nodejs.org/dist/") {
         name = "Node Distributions at $url"
         patternLayout { artifact("v[revision]/[artifact](-v[revision]-[classifier]).[ext]") }
         metadataSources { artifact() }
         content { includeModule("org.nodejs", "node") }
      }
      ivy("https://github.com/yarnpkg/yarn/releases/download") {
         name = "Yarn Distributions at $url"
         patternLayout { artifact("v[revision]/[artifact](-v[revision]).[ext]") }
         metadataSources { artifact() }
         content { includeModule("com.yarnpkg", "yarn") }
      }
      sonatypeSnapshots()
      google()
      gradlePluginPortal() // tvOS builds need to be able to fetch a Kotlin Gradle plugin
   }

   pluginManagement {
      repositories {
         gradlePluginPortal()
         mavenCentral()
         google()
         sonatypeSnapshots()
      }
   }
}

fun RepositoryHandler.sonatypeSnapshots() {
   maven("https://s01.oss.sonatype.org/content/repositories/snapshots/") {
      name = "SonatypeSnapshotS01"
      mavenContent { snapshotsOnly() }
   }
   maven("https://oss.sonatype.org/content/repositories/snapshots/") {
      name = "SonatypeSnapshotsOSS"
      mavenContent { snapshotsOnly() }
   }
}
