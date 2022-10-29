package io.ks3.java.time

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import java.time.YearMonth

typealias YearMonthAsString = @Serializable(with = YearMonthAsStringSerializer::class) YearMonth

object YearMonthAsStringSerializer : KSerializer<YearMonth> by stringSerializer(
   YearMonthAsStringSerializer::class.qualifiedName!!,
   YearMonth::parse,
   YearMonth::toString,
)
