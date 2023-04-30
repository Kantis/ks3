# Module ks3-jdk

Serializers for Java types. Available for JVM targets.

# Package io.ks3.java.io

Contains serializers for types in the `java.io`-package, such as `java.io.File`.

## Typealiases

| Type | Typealias    | Example                               |
|------|--------------|---------------------------------------|
| File | FileAsString | `"/Users/emil/Documents/theFile.txt"` |

# Package io.ks3.java.net

Contains serializers for networking-related types, such as `java.net.URL`.

## Typealiases

| Type | Typealias   | Example                     |
|------|-------------|-----------------------------|
| URL  | UrlAsString | `"https://www.github.com"`  |
| URI  | UriAsString | `"telnet://192.0.2.16:80/"` |

# Package io.ks3.java.time

Contains serializers for time-related types, such as `java.time.Instant`.

## Typealiases

| Type           | Typealias              | Example                         |
|----------------|------------------------|---------------------------------|
| Instant        | InstantAsString        | `"2023-04-27T21:24:37.476555Z"` |
| Instant        | InstantAsLong          | `1682630695852`                 |
| LocalDate      | LocalDateAsString      | `"2022-10-23"`                  |
| LocalDateTime  | LocalDateTimeAsString  | `"2022-10-23T21:44:00"`         |
| LocalTime      | LocalTimeAsString      | `"21:44:00"`                    |
| OffsetDateTime | OffsetDateTimeAsString | `"2022-10-23T21:44:00+02:00"`   |
| YearMonth      | YearMonthAsString      | `"2022-10"`                     |

# Package io.ks3.java.typealias

Contains `typealias`es for easily defining types where the Java type uses a pre-determined `Serializer`.

See each package for available `typealias`es.

# Package io.ks3.java.util

Contains serializers for types in `java.util`, such as `UUID` or `AtomicLong`.
