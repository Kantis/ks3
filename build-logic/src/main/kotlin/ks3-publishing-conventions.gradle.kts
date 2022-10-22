plugins {
   signing
   `java-library`
   `maven-publish`
}

val publications: PublicationContainer = (extensions.getByName("publishing") as PublishingExtension).publications

val javadoc = tasks.named("javadoc")

group = "io.ks3"
version = "0.0.1-SNAPSHOT"

val javadocJar by tasks.creating(Jar::class) {
   group = JavaBasePlugin.DOCUMENTATION_GROUP
   description = "Assembles java doc to jar"
   archiveClassifier.set("javadoc")
   from(javadoc)
}

publishing {
   publications.withType<MavenPublication>().forEach {
      it.apply {
         artifact(javadocJar)
      }
   }
}

val ossrhUsername: String by project
val ossrhPassword: String by project
val signingKey: String? by project
val signingPassword: String? by project

signing {
   useGpgCmd()
   if (signingKey != null && signingPassword != null) {
      @Suppress("UnstableApiUsage")
      useInMemoryPgpKeys(signingKey, signingPassword)
   }
   // TODO
//    if (Ci.isRelease) {
//        sign(publications)
//    }
}

publishing {
   repositories {
      maven {
//            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
         val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots/")
         name = "deploy"
//            url = if (Ci.isRelease) releasesRepoUrl else snapshotsRepoUrl
         url = snapshotsRepoUrl
         credentials {
            username = System.getenv("OSSRH_USERNAME") ?: ossrhUsername
            password = System.getenv("OSSRH_PASSWORD") ?: ossrhPassword
         }
      }
   }

   publications.withType<MavenPublication>().forEach {
      it.apply {
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
}
