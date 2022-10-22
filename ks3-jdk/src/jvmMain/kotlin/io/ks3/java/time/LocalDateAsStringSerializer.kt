package io.ks3.java.time

import kotlinx.serialization.KSerializer
import java.time.LocalDate

object LocalDateAsStringSerializer : KSerializer<LocalDate> by stringSerializer(
   "io.ks3.java.time.LocalDateAsStringSerializer",
   { if (it.endsWith("T00:00:00")) LocalDate.parse(it.split("T")[0]) else LocalDate.parse(it) },
)
