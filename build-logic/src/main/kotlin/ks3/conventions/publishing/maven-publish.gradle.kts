package ks3.conventions.publishing

import ks3.conventions.Ks3BuildLogicSettings
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper

plugins {
   signing
   `maven-publish`
}


val ks3Settings = extensions.getByType<Ks3BuildLogicSettings>()


val javadocJarStub by tasks.registering(Jar::class) {
   group = JavaBasePlugin.DOCUMENTATION_GROUP
   description = "Empty Javadoc Jar (required by Maven Central)"
   archiveClassifier.set("javadoc")
}

publishing {
   repositories {
      // Publish to a project-local Maven directory, for verification. To test, run:
      // ./gradlew publishAllPublicationsToMavenProjectLocalRepository
      // and check $rootDir/build/maven-project-local
      maven(rootProject.layout.buildDirectory.dir("maven-project-local")) {
         name = "MavenProjectLocal"
      }
   }

   publications.withType<MavenPublication>().forEach {
      it.apply {

         artifact(javadocJarStub)

         pom {
            name.set("Ks3")
            description.set("KotlinX Serialization standard serializers")
            url.set("https://github.com/Kantis/ks3")

            scm {
               connection.set("scm:git:https://github.com/Kantis/ks3/")
               developerConnection.set("scm:git:https://github.com/Kantis/")
               url.set("https://github.com/Kantis/ks3")
            }

            licenses {
               license {
                  name.set("Apache-2.0")
                  url.set("https://opensource.org/licenses/Apache-2.0")
               }
            }

            developers {
               developer {
                  id.set("Kantis")
                  name.set("Emil Kantis")
                  email.set("emil.kantis@protonmail.com")
               }
            }
         }
      }
   }
}

signing {
   val signingKey: String? by project
   val signingPassword: String? by project

   logger.lifecycle("[maven-publish convention] signing is enabled for ${project.path}")
   useGpgCmd()
   useInMemoryPgpKeys(signingKey, signingPassword)
   sign(publishing.publications)
}

// https://youtrack.jetbrains.com/issue/KT-46466
val signingTasks = tasks.withType<Sign>()

tasks.withType<AbstractPublishToMaven>().configureEach {
   // Gradle warns about some signing tasks using publishing task outputs without explicit dependencies.
   // Here's a quick fix.
   dependsOn(signingTasks)
   mustRunAfter(signingTasks)

   // use a val for the GAV to avoid Gradle Configuration Cache issues
   val publicationGAV = publication?.run { "$group:$artifactId:$version" }

   doLast {
      if (publicationGAV != null) {
         logger.lifecycle("[task: ${path}] $publicationGAV")
      }
   }
}

tasks.withType<AbstractPublishToMaven>().configureEach {
   // use vals - improves Gradle Config Cache compatibility
   val publicationName = publication.name
   val enabledPublicationNamePrefixes = ks3Settings.enabledPublicationNamePrefixes

   val isPublicationEnabled = enabledPublicationNamePrefixes.map { prefixes ->
      prefixes.any { prefix -> publicationName.startsWith(prefix, ignoreCase = true) }
   }

   // register an input so Gradle can do up-to-date checks
   inputs.property("publicationEnabled", isPublicationEnabled)

   onlyIf {
      val enabled = isPublicationEnabled.get()
      if (!enabled) {
         logger.lifecycle("[task: $path] publishing for $publicationName is disabled")
      }
      enabled
   }
}

// Kotlin Multiplatform specific publishing configuration
plugins.withType<KotlinMultiplatformPluginWrapper>().configureEach {
   // nothing yet!
}
