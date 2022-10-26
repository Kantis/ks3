package io.ks3.java.time

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

typealias OffsetDateTimeAsString = @Serializable(with = OffsetDateTimeAsStringSerializer::class) OffsetDateTime

object OffsetDateTimeAsStringSerializer : KSerializer<OffsetDateTime> by stringSerializer(
   OffsetDateTimeAsStringSerializer::class.qualifiedName!!,
   OffsetDateTime::parse,
   OffsetDateTime::toString,
)
