package io.ks3.java.time

import io.ks3.standard.longSerializer
import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import java.time.Instant

typealias InstantAsString = @Serializable(with = InstantAsStringSerializer::class) Instant
typealias InstantAsLong = @Serializable(with = InstantAsLongSerializer::class) Instant

object InstantAsStringSerializer : KSerializer<Instant> by stringSerializer(
   InstantAsStringSerializer::class.qualifiedName!!,
   Instant::parse,
   Instant::toString,
)

/**
 * Serializes an [Instant] to its' epoch millis.
 * ⚠️ Nanosecond precision is lost when encoding/decoding [Instant]s with this serializer
 */
object InstantAsLongSerializer : KSerializer<Instant> by longSerializer(
   InstantAsLongSerializer::class.qualifiedName!!,
   Instant::ofEpochMilli,
   Instant::toEpochMilli,
)
