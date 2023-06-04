rootProject.name = "ks3"

plugins {
   id("com.gradle.enterprise") version "3.13.3"
}

apply(from = "build-logic/repositories.gradle.kts")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

includeBuild("build-logic")

include(
   ":ks3-jdk",
   ":ks3-standard",
   ":ks3-test",
   ":ks3-core",
)


gradleEnterprise {

   buildScan {
      val isCI = providers.environmentVariable("CI").orNull.toBoolean()

      tag(if (isCI) "CI" else "local")
      tag(providers.systemProperty("os.name").orNull)
      tag(providers.systemProperty("os.arch").orNull)

      if (isCI) {
         // only automatically enable build scan on CI
         termsOfServiceUrl = "https://gradle.com/terms-of-service"
         termsOfServiceAgree = "yes"
         publishAlways()
         isUploadInBackground = false

         val ghServer = providers.environmentVariable("GITHUB_SERVER_URL").orNull
         val ghRepo = providers.environmentVariable("GITHUB_REPOSITORY").orNull
         val giRunId = providers.environmentVariable("GITHUB_RUN_ID").orNull
         link("GitHub Workflow run", "$ghServer/$ghRepo/actions/runs/$giRunId")
      }
   }
}
