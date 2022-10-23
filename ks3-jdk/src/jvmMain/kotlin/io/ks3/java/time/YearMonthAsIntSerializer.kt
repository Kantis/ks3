package io.ks3.java.time

import io.ks3.java.intSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import java.time.YearMonth
import kotlin.math.abs

typealias YearMonthAsInt = @Serializable(with = YearMonthAsIntSerializer::class) YearMonth

object YearMonthAsIntSerializer : KSerializer<YearMonth> by intSerializer(
   YearMonthAsIntSerializer::class.qualifiedName!!,
   { YearMonth.of(it / 100, abs(it % 100)) },
   {
      if (it.year == 0) it.monthValue
      else it.year * 100 + (Integer.signum(it.year) * it.monthValue)
   },
)
