package io.ks3.java.time

import io.ks3.java.intSerializer
import io.ks3.java.stringSerializer
import kotlinx.serialization.KSerializer
import java.time.Year

object YearAsStringSerializer : KSerializer<Year> by stringSerializer(
   YearAsStringSerializer::class.qualifiedName!!,
   Year::parse,
)

object YearAsIntSerializer : KSerializer<Year> by intSerializer(
   YearAsIntSerializer::class.qualifiedName!!,
   Year::of,
   Year::getValue,
)
