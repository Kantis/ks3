rootProject.name = "ks3"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

includeBuild("build-logic")

include(
   ":ks3-jdk",
   ":ks3-standard",
)

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
      gradlePluginPortal() // tvOS builds need to be able to fetch a kotlin gradle plugin
   }

   pluginManagement {
      repositories {
         gradlePluginPortal()
         mavenCentral()
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
