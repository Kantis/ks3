package io.ks3.java.time

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import java.time.LocalTime

/**
 * Uses [ISO8601](https://en.wikipedia.org/wiki/ISO_8601) format, with no timezone information
 * Example: `12:00:00` means 12 'o clock at noon.
 */
object LocalTimeAsStringSerializer : KSerializer<LocalTime> by stringSerializer(LocalTime::parse)
