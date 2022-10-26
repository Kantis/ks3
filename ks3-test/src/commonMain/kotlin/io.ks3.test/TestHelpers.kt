package io.ks3.test

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.internal.decodeStringToJsonTree
import kotlinx.serialization.json.internal.readJson
import kotlinx.serialization.json.okio.decodeFromBufferedSource
import okio.Buffer

typealias Encoder<T> = T.() -> String
typealias Decoder<T> = String.() -> T

data class Encoders<T>(
   val encoders: List<Encoder<T>>,
   val decoders: List<Decoder<T>>,
)

inline fun <reified T> Json.generateEncoders(serializer: KSerializer<T>) =
   Encoders(
      encoders(serializer),
      decoders(serializer),
   )

inline fun <reified T> Json.encoders(serializer: KSerializer<T>): List<Encoder<T>> =
   listOf(
      { encodeToString(serializer, this) },
   )

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
inline fun <reified T> Json.decoders(serializer: KSerializer<T>): List<Decoder<T>> =
   listOf(
      { decodeFromString(serializer, this) },
      {
         val tree = decodeStringToJsonTree(serializer, this)
         readJson(tree, serializer)
      },
      {
         val buffer = Buffer()
         buffer.writeUtf8(this)
         decodeFromBufferedSource(serializer, buffer)
      },
   )
