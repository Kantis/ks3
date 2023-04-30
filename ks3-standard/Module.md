# Module ks3-standard
* Serializers for standard Kotlin types, available on all (or at least, most) platforms.
* Functions for more conveniently creating serializers.

## Serializer builders

Serializer builders provide a convenient way of creating a serializer that turns `T` into a primitive, by simply providing the two
conversion methods `(T) -> <primitive>` and `(<primitive>) -> T`.

The return value of serializer builders is an anonymous object implementing `KSerializer<T>`.

### Example:

```kotlin
// Delegate the implementation to the return value of `stringSerializer(..)`
object UriSerializer : KSerializer<URI> by stringSerializer(
    ::URI,
    URI::toASCIIString,
)
```

Here we create a serializer for `URI`. Our object implements `KSerializer<URI>` by delegating to the result of `stringSerializer(..)`.

The first parameter, `::URI`, is the _decoding_ method which takes our primitive (`String`) and returns a `URI`.

The second parameter is the _encoding_ method, which takes our type and returns the primitive.

Since the methods to go back and forth from String to URI already exists in Java, this serializer is easily created using `stringSerializer`.


### Available builders are:

* `intSerializer`
* `longSerializer`
* `stringSerializer`
