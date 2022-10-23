rootProject.name = "ks3"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

includeBuild("build-logic")

include(
   ":ks3-jdk",
   ":ks3-standard",
)

apply(from = "build-logic/repositories.gradle.kts")
