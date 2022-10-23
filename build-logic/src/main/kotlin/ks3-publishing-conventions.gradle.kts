plugins {
   signing
   `maven-publish`
   id("ks3-base-conventions")
}

val javadocJarStub by tasks.registering(Jar::class) {
   group = JavaBasePlugin.DOCUMENTATION_GROUP
   description = "Empty Javadoc Jar (required by Maven Central)"
   archiveClassifier.set("javadoc")
}

// it would be nicer to change the environment variables to be ORG_GRADLE_PROJECT_ossrhUsername and ORG_GRADLE_PROJECT_ossrhPassword
val ossrhUsername = providers.environmentVariable("OSSRH_USERNAME").orElse(providers.gradleProperty("ossrhUsername"))
val ossrhPassword = providers.environmentVariable("OSSRH_PASSWORD").orElse(providers.gradleProperty("ossrhPassword"))
val signingKey: String? by project
val signingPassword: String? by project


val isSnapshotVersion = provider { version.toString().endsWith("SNAPSHOT") }

val sonatypeReleaseUrl = isSnapshotVersion.map { isSnapshot ->
   if (isSnapshot) {
      "https://s01.oss.sonatype.org/content/repositories/snapshots/"
   } else {
      "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
   }
}

signing {
   useGpgCmd()
   if (signingKey != null && signingPassword != null) {
      useInMemoryPgpKeys(signingKey, signingPassword)
      // Gradle hasn't updated the signing plugin to be compatible with lazy-configuration,
      // so it needs weird workarounds: https://github.com/gradle/gradle/issues/19903
      sign(closureOf<SignOperation> { sign(publishing.publications) })
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

      // if (Ci.isRelease)
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

   doLast {
      logger.lifecycle("[task: ${this@configureEach.path}] ${publication.groupId}:${publication.artifactId}:${publication.version}")
   }
}
