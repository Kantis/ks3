package io.ks3.java.util

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import java.util.UUID

object UuidSerializer : KSerializer<UUID> by stringSerializer(UUID::fromString)
