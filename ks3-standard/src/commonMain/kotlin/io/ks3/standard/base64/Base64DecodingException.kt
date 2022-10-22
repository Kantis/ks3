package io.ks3.standard.base64

import kotlinx.serialization.SerializationException

class Base64DecodingException(message: String? = null, cause: Throwable? = null) : SerializationException(message, cause)
