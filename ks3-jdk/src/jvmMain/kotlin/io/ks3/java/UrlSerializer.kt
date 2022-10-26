package io.ks3.java

import kotlinx.serialization.KSerializer
import java.net.URL

object UrlSerializer : KSerializer<URL> by stringSerializer(
   UrlSerializer::class.qualifiedName!!,
   ::URL,
   URL::toExternalForm,
)
