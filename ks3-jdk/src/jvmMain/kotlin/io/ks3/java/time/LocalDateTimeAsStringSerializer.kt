package io.ks3.java.time

import kotlinx.serialization.KSerializer
import java.time.LocalDateTime

object LocalDateTimeAsStringSerializer : KSerializer<LocalDateTime> by stringSerializer(
    "io.ks3.java.time.LocalDateTimeAsStringSerializer",
    { LocalDateTime.parse(it) },
)
