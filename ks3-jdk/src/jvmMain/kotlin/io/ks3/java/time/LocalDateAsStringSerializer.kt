package io.ks3.java.time

import io.ks3.java.stringSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

typealias LocalDateAsString = @Serializable(with = LocalDateAsStringSerializer::class) LocalDate

object LocalDateAsStringSerializer : KSerializer<LocalDate> by stringSerializer(
   LocalDateAsStringSerializer::class.qualifiedName!!,
   { if (it.endsWith("T00:00:00")) LocalDate.parse(it.split("T")[0]) else LocalDate.parse(it) },
   LocalDate::toString,
)
