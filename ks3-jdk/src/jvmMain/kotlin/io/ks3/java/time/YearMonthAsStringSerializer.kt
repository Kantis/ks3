package io.ks3.java.time

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import java.time.YearMonth

/**
 * Serializes a [YearMonth] to a string. Example: `2020-01`
 */
object YearMonthAsStringSerializer : KSerializer<YearMonth> by stringSerializer(YearMonth::parse)
