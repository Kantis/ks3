package io.ks3.standard

import kotlinx.serialization.SerializationException

class EnumDecodingException(message: String? = null, cause: Throwable? = null) : SerializationException(message, cause)
