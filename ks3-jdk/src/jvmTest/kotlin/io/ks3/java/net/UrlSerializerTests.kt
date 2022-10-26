package io.ks3.java.net

import io.kotest.core.spec.style.FunSpec
import io.kotest.property.exhaustive.exhaustive
import io.ks3.test.generateSerializerTests
import java.net.URL

class UrlSerializerTests : FunSpec(
   {
      val someUrls = listOf(
         "http://localhost:1234/path?param=7",
         "https://www.google.com:1234",
         "ftp://www.dn.se",
      ).map(::URL)

      include(generateSerializerTests(UrlSerializer, someUrls.exhaustive()))
   },
)
