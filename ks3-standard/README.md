Serializers for standard Kotlin types, available on all (or at least, most) platforms.


## Serializers

### EnumAsOrdinalSerializer

Used to serialize/deserialize enums to their ordinal value.

Example usage:

```kotlin
enum class Shape {
    SQUARE, CIRCLE;
}

object ShapeSerializer: KSerializer<Shape> by enumAsOrdinalSerializer()
```

Now you can use the `ShapeSerializer` like you would any other.
