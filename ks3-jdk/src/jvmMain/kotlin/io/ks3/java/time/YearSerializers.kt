package io.ks3.java.time

import io.ks3.standard.intSerializer
import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import java.time.Year

/**
 * Serializes a [Year] to a string. Example: `"2020"`
 */
object YearAsStringSerializer : KSerializer<Year> by stringSerializer(
   { Year.of(it.toInt()) },
)

/**
 * Serializes a [Year] to an integer. Example: `2020`
 */
object YearAsIntSerializer : KSerializer<Year> by intSerializer(
   Year::of,
   Year::getValue,
)
