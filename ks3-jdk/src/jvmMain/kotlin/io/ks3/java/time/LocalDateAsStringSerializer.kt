package io.ks3.java.time

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import java.time.LocalDate

/**
 * Uses [ISO8601](https://en.wikipedia.org/wiki/ISO_8601) format
 * Example: `"2020-01-01"`
 */
object LocalDateAsStringSerializer : KSerializer<LocalDate> by stringSerializer(
   { if (it.endsWith("T00:00:00")) LocalDate.parse(it.split("T")[0]) else LocalDate.parse(it) },
   LocalDate::toString,
)
