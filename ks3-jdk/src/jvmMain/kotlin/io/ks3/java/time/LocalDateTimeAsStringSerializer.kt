package io.ks3.java.time

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import java.time.LocalDateTime

/**
 * Uses [ISO8601](https://en.wikipedia.org/wiki/ISO_8601) format, with no timezone information
 * Example: `2020-01-01T18:49:00`
 */
object LocalDateTimeAsStringSerializer : KSerializer<LocalDateTime> by stringSerializer(LocalDateTime::parse)
