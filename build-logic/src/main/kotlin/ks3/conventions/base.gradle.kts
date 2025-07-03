package ks3.conventions

plugins {
   base
   id("com.adarshr.test-logger")
   id("dev.adamko.dokkatoo-html")
   id("com.gradleup.nmcp")
}

// common config for all subprojects

if (project != rootProject) {
   project.version = rootProject.version
   project.group = rootProject.group
}

extensions.create(Ks3BuildLogicSettings.EXTENSION_NAME, Ks3BuildLogicSettings::class)

//testlogger {
//   showPassed = false
//}

tasks.withType<AbstractArchiveTask>().configureEach {
   // https://docs.gradle.org/current/userguide/working_with_files.html#sec:reproducible_archives
   isPreserveFileTimestamps = false
   isReproducibleFileOrder = true
}

