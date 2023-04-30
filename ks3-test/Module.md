# Module ks3-test
Utilities for testing serializers using [Kotest](https://kotest.io).

`generateSerializerTests` returns a Kotest [test factory](https://kotest.io/docs/framework/test-factories.html) for testing a serializer.

It uses property testing to test round-trip serialization of random inputs.
