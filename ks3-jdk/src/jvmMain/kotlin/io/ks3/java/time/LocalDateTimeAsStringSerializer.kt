package io.ks3.java.time

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Suppress("unused") // Part of the public API.
typealias LocalDateTimeAsString = @Serializable(with = LocalDateTimeAsStringSerializer::class) LocalDateTime

object LocalDateTimeAsStringSerializer : KSerializer<LocalDateTime> by stringSerializer(LocalDateTime::parse)
