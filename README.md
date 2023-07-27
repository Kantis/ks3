# KotlinX Serialization Standard Serializers (KS3)
![Stability Status - Alpha](https://kotl.in/badges/alpha.svg)
![GitHub commit checks state](https://img.shields.io/github/checks-status/Kantis/ks3/main?label=build%40main)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/io.ks3/ks3-standard?label=latest%20&server=https%3A%2F%2Fs01.oss.sonatype.org)

This project aims to provide a set of serializers for common types, as well as provide [utilities](/doc/builders.md) to make it more convenient to build custom serializers for your needs.

> ⚠️ Consider this project to be [Alpha](https://kotlinlang.org/docs/components-stability.html) for the time being. We would be happy for you to use it, and appreciate your feedback, but things may be re-written. We hope to reach [Beta](https://kotlinlang.org/docs/components-stability.html) relatively quick.

## Getting started

Add the dependency. With Gradle:

```kotlin
dependencies {
    implementation("io.ks3:ks3-jdk:0.3.1")
}
```

Maven:

```xml
<dependency>
  <groupId>io.ks3</groupId>
  <artifactId>ks3-jdk-jvm</artifactId>
  <version>$version</version>
</dependency>
```

> Note that when using Maven dependencies must specify the multiplatform variant. For example, append `-jvm` to specify the JVM variant of `ks3-jdk`.

Now you can start using the provided serializers. There's several possible ways to do this.

* Using [contextual serialization](https://github.com/Kotlin/kotlinx.serialization/blob/1.4.1-release/docs/serializers.md#contextual-serialization)
* [Passing a serializer manually](https://github.com/Kotlin/kotlinx.serialization/blob/1.4.1-release/docs/serializers.md#passing-a-serializer-manually)
* [Specifying serializer on a property](https://github.com/Kotlin/kotlinx.serialization/blob/1.4.1-release/docs/serializers.md#specifying-serializer-on-a-property)
* [Specifying serializers for a file](https://github.com/Kotlin/kotlinx.serialization/blob/1.4.1-release/docs/serializers.md#specifying-serializers-for-a-file)
* [Specifying serializer using a typealias](https://github.com/Kotlin/kotlinx.serialization/blob/1.4.1-release/docs/serializers.md#specifying-serializer-globally-using-typealias)


### Using `typealias`

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
