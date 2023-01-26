package io.ks3.java.time

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import java.time.LocalTime

@Suppress("unused") // Part of the public API.
typealias LocalTimeAsString = @Serializable(with = LocalTimeAsStringSerializer::class) LocalTime

object LocalTimeAsStringSerializer : KSerializer<LocalTime> by stringSerializer(LocalTime::parse)
