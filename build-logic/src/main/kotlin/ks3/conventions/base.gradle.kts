package ks3.conventions

import org.jetbrains.dokka.gradle.DokkaTaskPartial

plugins {
   base
   id("com.adarshr.test-logger")
   id("org.jetbrains.dokka")
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

tasks.withType<DokkaTaskPartial>().configureEach {
   // ..
   // general configuration section
   // ..

   dokkaSourceSets.configureEach {
      // ..
      // source set configuration section
      // ..
      includes.from("Module.md")

      sourceLink {
         localDirectory.set(projectDir.resolve("src"))
         remoteUrl.set(java.net.URL("https://github.com/Kantis/ks3/tree/main/${project.name}/src"))
         remoteLineSuffix.set("#L")
      }
   }
}
