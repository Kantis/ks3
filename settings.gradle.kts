rootProject.name = "ks3"

plugins {
   id("com.gradle.enterprise") version "3.11.2"
}

apply(from = "build-logic/repositories.gradle.kts")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

includeBuild("build-logic")

include(
   ":ks3-jdk",
   ":ks3-standard",
)


gradleEnterprise {

   buildScan {
      val isCI = providers.systemProperty("CI").get().toBoolean()

      tag(if (isCI) "CI" else "local")
      tag(System.getProperty("os.name"))
      tag(System.getProperty("os.arch"))

      if (isCI) {
         // only automatically enable build scan on CI
         termsOfServiceUrl = "https://gradle.com/terms-of-service"
         termsOfServiceAgree = "yes"
         publishAlways()
         tag("CI")
         isUploadInBackground = false

         tag(System.getProperty("GITHUB_ACTION")) // name of the action currently running, or step ID
         tag(System.getProperty("GITHUB_REF")) // fully-formed ref of the branch or tag that triggered the workflow run
      }
   }
}
