package io.ks3.java.net

import io.kotest.core.spec.style.FunSpec
import io.kotest.property.exhaustive.exhaustive
import io.ks3.test.generateSerializerTests
import java.net.URI

class UriSerializerTests : FunSpec(
   {
      val someUris =
         listOf(
            "https://john.doe@www.example.com:123/forum/questions/?tag=networking&order=newest#top",
            "ldap://[2001:db8::7]/c=GB?objectClass?one",
            "mailto:John.Doe@example.com",
            "news:comp.infosystems.www.servers.unix",
            "tel:+1-816-555-1212",
            "telnet://192.0.2.16:80/",
            "urn:oasis:names:specification:docbook:dtd:xml:4.1.2",
         ).map(::URI)

      include(generateSerializerTests(UriSerializer, someUris.exhaustive()))
   },
)
