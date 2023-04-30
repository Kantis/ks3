package io.ks3.java.time

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import java.time.OffsetDateTime

/**
 * Serializes an [OffsetDateTime] to a string, including offset. Example: `"2020-01-01T18:49:00+01:00"`
 */
object OffsetDateTimeAsStringSerializer : KSerializer<OffsetDateTime> by stringSerializer(OffsetDateTime::parse)
