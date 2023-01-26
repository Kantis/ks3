package io.ks3.java.net

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import java.net.URL

object UrlSerializer : KSerializer<URL> by stringSerializer(::URL, URL::toExternalForm)
