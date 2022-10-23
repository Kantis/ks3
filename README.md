# KotlinX Serialization Standard Serializers (KS3)

This project aims to provide a set of serializers for common types.

> ⚠️ Consider this project to be [Alpha](https://kotlinlang.org/docs/components-stability.html) for the time being. We would be happy for you to use it, and appreciate your feedback, but things may be re-written. We hope to reach [Beta](https://kotlinlang.org/docs/components-stability.html) relatively quick.

## Getting started

> ⚠️ This library is currently being built and not published anywhere yet.

Add the dependency. With gradle:

```kotlin
dependencies {
    implementation("io.ks3:ks3-jdk:<version>")
}
```

Maven:

```xml
<dependency>
  <groupId>io.ks3</groupId>
  <artifactId>ks3-jdk</artifactId>
  <version>$version</version>
</dependency>
```

Now you can start using the provided serializers. There's several possible ways to do this.

* Using [contextual serialization](https://github.com/Kotlin/kotlinx.serialization/blob/1.4.1-release/docs/serializers.md#contextual-serialization)
* [Passing a serializer manually](https://github.com/Kotlin/kotlinx.serialization/blob/1.4.1-release/docs/serializers.md#passing-a-serializer-manually)
* [Specifying serializer on a property](https://github.com/Kotlin/kotlinx.serialization/blob/1.4.1-release/docs/serializers.md#specifying-serializer-on-a-property)
* [Specifying serializers for a file](https://github.com/Kotlin/kotlinx.serialization/blob/1.4.1-release/docs/serializers.md#specifying-serializers-for-a-file)
* [Specifying serializer using a typealias](https://github.com/Kotlin/kotlinx.serialization/blob/1.4.1-release/docs/serializers.md#specifying-serializer-globally-using-typealias)


### Using typealias

```kotlin
typealias LocalDateTimeAsString = @Serializable(LocalDateTimeAsStringSerializer::class) LocalDateTime

data class Appointment(
  val datetime: LocalDateTimeAsString
)
```

> For details, see the [kotlinx.serialization guide](https://github.com/Kotlin/kotlinx.serialization/blob/1.4.1-release/docs/serializers.md#specifying-serializer-globally-using-typealias)

This method is most useful when you want to use different serial formats for the same type, or when you can't configure the serializer itself.

### Using `@Contextual`

> See the details in [kotlinx.serialization guide](https://github.com/Kotlin/kotlinx.serialization/blob/1.4.1-release/docs/serializers.md#contextual-serialization)

```kotlin
@Serializable
class ProgrammingLanguage(
    val name: String,
    @Contextual
    val stableReleaseDate: Date
)

private val module = SerializersModule {
  contextual(DateAsLongSerializer)
}

val format = Json { serializersModule = module }
```
