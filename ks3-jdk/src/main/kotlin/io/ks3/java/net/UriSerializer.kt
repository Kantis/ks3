package io.ks3.java.net

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import java.net.URI

object UriSerializer : KSerializer<URI> by stringSerializer(
   UriSerializer::class.qualifiedName!!,
   ::URI,
   URI::toASCIIString,
)
