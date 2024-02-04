| Type    | Serializer              | Example |
|---------|-------------------------|---------|
| Enum<*> | EnumAsOrdinalSerializer | `0`     |

### EnumAsOrdinalSerializer

Encodes/decodes an enum to its ordinal int value.

Example usage:

```kotlin
enum class Shape {
  SQUARE, CIRCLE;
}

object ShapeSerializer : KSerializer<Shape> by enumAsOrdinalSerializer()

println(Json.encodeToString(ShapeSerializer, Shape.CIRCLE)) // "1"
```

### TypeOmittingTransformer

A function that generates a JsonTransformingSerializer that removes class discriminators.

Useful when you serialize a sealed class hierarchy and don't want to include the class discriminator ("type" property, by default)
in the JSON.
