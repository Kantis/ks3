package io.ks3.java.time

import io.ks3.standard.longSerializer
import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import java.time.Instant

object InstantAsStringSerializer : KSerializer<Instant> by stringSerializer(
   Instant::parse,
   Instant::toString,
)

/**
 * Serializes an [Instant] to its' epoch millis.
 * ⚠️ Nanosecond precision is lost when encoding/decoding [Instant]s with this serializer
 */
object InstantAsLongSerializer : KSerializer<Instant> by longSerializer(
   Instant::ofEpochMilli,
   Instant::toEpochMilli,
)
