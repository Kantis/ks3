package io.ks3.java.util

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import java.util.Locale

object LocaleSerializer : KSerializer<Locale> by stringSerializer(::Locale)
