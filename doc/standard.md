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

