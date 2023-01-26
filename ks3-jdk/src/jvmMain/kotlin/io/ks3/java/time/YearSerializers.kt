package io.ks3.java.time

import io.ks3.standard.intSerializer
import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import java.time.Year

object YearAsStringSerializer : KSerializer<Year> by stringSerializer(
   { Year.of(it.toInt()) },
)

object YearAsIntSerializer : KSerializer<Year> by intSerializer(
   Year::of,
   Year::getValue,
)
