package io.ks3.java.time

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Suppress("unused") // Part of the public API.
typealias LocalDateAsString = @Serializable(with = LocalDateAsStringSerializer::class) LocalDate

object LocalDateAsStringSerializer : KSerializer<LocalDate> by stringSerializer(
   { if (it.endsWith("T00:00:00")) LocalDate.parse(it.split("T")[0]) else LocalDate.parse(it) },
   LocalDate::toString,
)
