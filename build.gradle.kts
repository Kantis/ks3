@Suppress("DSL_SCOPE_VIOLATION")
plugins {
   id("ks3.conventions.base")
   idea
   alias(libs.plugins.kotlinBinaryCompatibilityValidator)
}

group = "io.ks3"

idea {
   module {
      isDownloadSources = true
      isDownloadJavadoc = false
      excludeDirs = excludeDirs + layout.files(
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
