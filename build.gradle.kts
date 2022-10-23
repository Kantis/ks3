plugins {
   base
   idea
}

group = "io.ks3"
version = "0.0.1-SNAPSHOT"


idea {
   module {
      isDownloadSources = true
      isDownloadJavadoc = false
      excludeDirs = excludeDirs + layout.files(
         ".idea",
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
