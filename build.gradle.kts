import dev.adamko.dokkatoo.dokka.plugins.DokkaHtmlPluginParameters
import ks3.conventions.Ks3BuildLogicSettings

plugins {
   id("ks3.conventions.base")
   id("ks3.conventions.api-validation")
   idea
   id("com.gradleup.nmcp.aggregation").version("1.0.1")
   `maven-publish`
}

group = "io.ks3"

dependencies {
   dokkatoo(projects.ks3Jdk)
   dokkatoo(projects.ks3Standard)
   dokkatoo(projects.ks3Test)
   dokkatooPluginHtml(libs.dokka.templating)
   dokkatooPluginHtml(libs.dokka.allModulesPage)

   // Add all dependencies for publishing here
   nmcpAggregation(projects.ks3Jdk)
   nmcpAggregation(projects.ks3Standard)
   nmcpAggregation(projects.ks3Test)
   nmcpAggregation(projects.ks3Core)
}

dokkatoo {
   moduleName.set("KS3")

   dokkatooPublications.configureEach {
      includes.from("README.md")
   }

   pluginsConfiguration.named<DokkaHtmlPluginParameters>("html") {
      customStyleSheets.from(
         "./dokka/custom-styles.css",
      )
   }
}

nmcpAggregation {
   centralPortal {
      username.set(System.getenv("SONATYPE_USERNAME"))
      password.set(System.getenv("SONATYPE_PASSWORD"))
      publishingType = "USER_MANAGED"
   }
}

val isReleaseVersion = provider { version.toString().matches(Ks3BuildLogicSettings.releaseVersionRegex) }

val publishToAppropriateCentralRepository by tasks.registering {
   group = "publishing"
   if (isReleaseVersion.get()){
      dependsOn(tasks.named("publishAggregationToCentralPortal"))
   } else {
      dependsOn(tasks.named("publishAggregationToCentralPortalSnapshots"))
   }
}

idea {
   module {
      isDownloadSources = true
      isDownloadJavadoc = false
      excludeDirs = excludeDirs +
         layout.files(
            ".idea",
            "gradle/kotlin-js-store", // location of the lock file, overridden by Kotlin/JS convention
            "gradle/wrapper",
         )

      // exclude generated Gradle code, so it doesn't clog up search results
      excludeDirs.addAll(
         layout.files(
            "build-logic/build/generated-sources/kotlin-dsl-accessors/kotlin/gradle",
            "build-logic/build/generated-sources/kotlin-dsl-external-plugin-spec-builders/kotlin/gradle",
            "build-logic/build/generated-sources/kotlin-dsl-plugins/kotlin",
            "build-logic/build/pluginUnderTestMetadata",
         ),
      )
   }
}
