package io.ks3.java.io

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import java.io.File

object FilePathSerializer : KSerializer<File> by stringSerializer(::File, File::invariantSeparatorsPath)
