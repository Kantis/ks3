package io.ks3.java.util

import io.ks3.standard.stringSerializer
import kotlinx.serialization.KSerializer
import java.util.Currency

object CurrencySerializer : KSerializer<Currency> by stringSerializer(Currency::getInstance)
