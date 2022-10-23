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

extensions.create(Ks3BuildLogicSettings.EXTENSION_NAME, Ks3BuildLogicSettings::class)

testlogger {
   showPassed = false
}
