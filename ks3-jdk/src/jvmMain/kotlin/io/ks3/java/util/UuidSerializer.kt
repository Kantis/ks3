package io.ks3.java.util

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Suppress("unused") // Part of the public API.
typealias UuidAsString = @Serializable(with = UuidSerializer::class) UUID

object UuidSerializer : KSerializer<UUID> by stringSerializer(UUID::fromString)
