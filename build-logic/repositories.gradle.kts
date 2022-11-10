// Shared repository config, for use in both build-logic and the root project


@Suppress("UnstableApiUsage") // Central declaration of repositories is an incubating feature
dependencyResolutionManagement {

   repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

   repositories {
      mavenCentral()

      // Declare the Node.js & Yarn download repositories
      exclusiveContent {
         forRepository {
            ivy("https://nodejs.org/dist/") {
               name = "Node Distributions at $url"
               patternLayout { artifact("v[revision]/[artifact](-v[revision]-[classifier]).[ext]") }
               metadataSources { artifact() }
               content { includeModule("org.nodejs", "node") }
            }
         }
         filter { includeGroup("org.nodejs") }
      }

      exclusiveContent {
         forRepository {
            ivy("https://github.com/yarnpkg/yarn/releases/download") {
               name = "Yarn Distributions at $url"
               patternLayout { artifact("v[revision]/[artifact](-v[revision]).[ext]") }
               metadataSources { artifact() }
               content { includeModule("com.yarnpkg", "yarn") }
            }
         }
         filter { includeGroup("com.yarnpkg") }
      }

      // workaround for https://youtrack.jetbrains.com/issue/KT-51379
      exclusiveContent {
         forRepository {
            ivy("https://download.jetbrains.com/kotlin/native/builds") {
               name = "Kotlin Native"
               patternLayout {

                  // example download URLs:
                  // https://download.jetbrains.com/kotlin/native/builds/releases/1.7.20/linux-x86_64/kotlin-native-prebuilt-linux-x86_64-1.7.20.tar.gz
                  // https://download.jetbrains.com/kotlin/native/builds/releases/1.7.20/windows-x86_64/kotlin-native-prebuilt-windows-x86_64-1.7.20.zip
                  // https://download.jetbrains.com/kotlin/native/builds/releases/1.7.20/macos-x86_64/kotlin-native-prebuilt-macos-x86_64-1.7.20.tar.gz
                  listOf(
                     "macos-x86_64",
                     "macos-aarch64",
                     "osx-x86_64",
                     "osx-aarch64",
                     "linux-x86_64",
                     "windows-x86_64",
                  ).forEach { os ->
                     listOf("dev", "releases").forEach { stage ->
                        artifact("$stage/[revision]/$os/[artifact]-[revision].[ext]")
                     }
                  }
               }
               metadataSources { artifact() }
            }
         }
         filter { includeModuleByRegex(".*", ".*kotlin-native-prebuilt.*") }
      }

      sonatypeSnapshots()
      google()
      gradlePluginPortal() // tvOS builds need to be able to fetch a Kotlin Gradle plugin

      // I hacked together a deploy script for the latest KxS version - this should be removed ASAP
      // 1.5.0 is required for BigDecimal JSON encoding support
      // ks3 probably shouldn't be published while this dependency exists
      maven("https://raw.githubusercontent.com/aSemy/kotlinx.serialization/artifacts/m2/") {
         mavenContent {
            includeGroup("org.jetbrains.kotlinx")
            snapshotsOnly()
         }
      }
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
