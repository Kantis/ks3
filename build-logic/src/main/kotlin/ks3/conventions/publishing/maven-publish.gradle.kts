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

// can be set with environment variables: ORG_GRADLE_PROJECT_ossrhUsername and ORG_GRADLE_PROJECT_ossrhPassword
val ossrhUsername: Provider<String> = providers.gradleProperty("ossrhUsername")
val ossrhPassword: Provider<String> = providers.gradleProperty("ossrhPassword")
val signingKey: Provider<String> = providers.gradleProperty("signingKey")
val signingPassword: Provider<String> = providers.gradleProperty("signingPassword")

val isReleaseVersion = provider { version.toString().matches(Ks3BuildLogicSettings.releaseVersionRegex) }

val sonatypeReleaseUrl: Provider<String> = isReleaseVersion.map { isRelease ->
   if (isRelease) {
      "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
   } else {
      "https://s01.oss.sonatype.org/content/repositories/snapshots/"
   }
}

signing {
   useGpgCmd()
   if (signingKey.isPresent && signingPassword.isPresent) {
      logger.lifecycle("[maven-publish convention] signing is enabled for ${project.path}")
      useInMemoryPgpKeys(signingKey.get(), signingPassword.get())
   }
}

// Gradle hasn't updated the signing plugin to be compatible with lazy-configuration, so it needs weird workarounds:
afterEvaluate {
   // Register signatures in afterEvaluate, otherwise the signing plugin creates the signing tasks
   // too early, before all the publications are added. Use .all { }, not .configureEach { },
   // otherwise the signing plugin doesn't create the tasks soon enough.

   if (signingKey.isPresent && signingPassword.isPresent) {
      publishing.publications.all publication@{
         logger.lifecycle("[maven-publish convention] configuring signature for publication ${this@publication.name} in ${project.path}")
         // closureOf is a Gradle Kotlin DSL workaround: https://github.com/gradle/gradle/issues/19903
         signing.sign(closureOf<SignOperation> { signing.sign(this@publication) })
      }
   }
}

publishing {
   repositories {
      if (ossrhUsername.isPresent && ossrhPassword.isPresent) {
         maven(sonatypeReleaseUrl) {
            name = "SonatypeRelease"
            credentials {
               username = ossrhUsername.get()
               password = ossrhPassword.get()
            }
         }
      }

      // Publish to a project-local Maven directory, for verification. To test, run:
      // ./gradlew publishAllPublicationsToMavenProjectLocalRepository
      // and check $rootDir/build/maven-project-local
      maven(rootProject.layout.buildDirectory.dir("maven-project-local")) {
         name = "MavenProjectLocal"
      }
   }

   publications.withType<MavenPublication>().configureEach {

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

tasks.withType<AbstractPublishToMaven>().configureEach {
   // Gradle warns about some signing tasks using publishing task outputs without explicit dependencies.
   // Here's a quick fix.
   dependsOn(tasks.withType<Sign>())
   mustRunAfter(tasks.withType<Sign>())

   // use a val for the GAV to avoid Gradle Configuration Cache issues
   val publicationGAV = publication?.run { "$group:$artifactId:$version" }

   doLast {
      if (publicationGAV != null) {
         logger.lifecycle("[task: ${path}] $publicationGAV")
      }
   }
}


// Kotlin Multiplatform specific publishing configuration
plugins.withType<KotlinMultiplatformPluginWrapper>().configureEach {

   tasks.withType<AbstractPublishToMaven>().configureEach {
      // use vals - improves Gradle Config Cache compatibility
      val publicationName = publication.name
      val enabledPublicationNamePrefixes = ks3Settings.enabledPublicationNamePrefixes

      val kotlinPublicationEnabled = enabledPublicationNamePrefixes.map { names ->
         names.any { it.startsWith(publicationName, ignoreCase = true) }
      }

      // register an input so Gradle can do up-to-date checks
      inputs.property("kotlinPublicationEnabled", kotlinPublicationEnabled)

      onlyIf {
         val enabled = kotlinPublicationEnabled.get()
         if (!enabled) {
            logger.lifecycle("[task: $path] publishing for $publicationName is disabled")
         }
         enabled
      }
   }
}
