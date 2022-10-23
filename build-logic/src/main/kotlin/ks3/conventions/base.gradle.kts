package ks3.conventions

plugins {
   base
   id("com.adarshr.test-logger")
}

// common config for all subprojects

if (project != rootProject) {
   project.version = rootProject.version
   project.group = rootProject.group
}

extensions.create("ks3BuildLogicSettings", Ks3BuildLogicSettings::class)


tasks.withType<Test>().configureEach {
   filter {
      isFailOnNoMatchingTests = false
   }
}

testlogger {
   showPassed = false
}
