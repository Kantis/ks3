package io.ks3.java

import kotlinx.serialization.KSerializer
import java.util.UUID

object UuidSerializer : KSerializer<UUID> by stringSerializer(
   UuidSerializer::class.qualifiedName!!,
   UUID::fromString,
   UUID::toString,
)
