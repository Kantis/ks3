package io.ks3.java.time

import io.ks3.standard.longSerializer
import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import java.time.Instant

/**
 * Serializes an [Instant] to its' [String] representation. Example: `"2023-01-01T18:49:00.123456Z"`
 */
object InstantAsStringSerializer : KSerializer<Instant> by stringSerializer(
   Instant::parse,
   Instant::toString,
)

/**
 * Serializes an [Instant] to its' epoch millis.
 * WARNING!️ Nanosecond precision is lost when encoding/decoding [Instant]s with this serializer.
 * Considering using [InstantAsStringSerializer] instead.
 */
object InstantAsLongSerializer : KSerializer<Instant> by longSerializer(
   Instant::ofEpochMilli,
   Instant::toEpochMilli,
)
