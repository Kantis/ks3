package io.ks3.java.io

import io.kotest.core.spec.style.FunSpec
import io.kotest.property.exhaustive.exhaustive
import io.ks3.test.generateSerializerTests
import java.io.File

class FileSerializerTests : FunSpec(
   {
      val someFiles = listOf(
         "/",
         "/home/kotlin",
         "relative/to/linux",
         "C:",
         "C:\\Windows\\System32",
         "relative\\to\\windows"
      ).map(::File)

      include(generateSerializerTests(FilePathSerializer, someFiles.exhaustive()))
   },
)
