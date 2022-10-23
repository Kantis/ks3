package io.ks3.test

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

data class EncoderDecoder<T>(
   val encoder: T.() -> String,
   val decoder: String.() -> T,
)

inline fun <reified T> Json.encoders(serializer: KSerializer<T>) =
   EncoderDecoder(
      { encodeToString(serializer, this) },
      { decodeFromString(serializer, this) },
   )
