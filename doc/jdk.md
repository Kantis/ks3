## `java.time`

| Type           | Serializer                      | Typealias              | Example                       |
|----------------|---------------------------------|------------------------|-------------------------------|
| Instant        | InstantAsStringSerializer       | InstantAsString        | `2023-04-27T21:24:37.476555Z` |
| Instant        | InstantAsLongSerializer         | InstantAsLong          | `1682630695852`               |
| LocalDate      | LocalDateAsStringSerializer     | LocalDateAsString      | `"2022-10-23"`                |
| LocalDateTime  | LocalDateTimeAsStringSerializer | LocalDateTimeAsString  | `"2022-10-23T21:44:00"`       |
| LocalTime      | LocalTimeAsStringSerializer     | LocalTimeAsString      | `"21:44:00"`                  |
| OffsetDateTime | OffsetDateTimeSerializer        | OffsetDateTimeAsString | `2022`                        |
| YearMonth      | YearMonthAsStringSerializer     | YearMonthAsString      | `"2022-10"`                   |
| Year           | YearAsStringSerializer          | not defined            | `"2022"`                      |
| Year           | YearAsIntSerializer             | not defined            | `2022`                        |

## `java.io`

| Type | Serializer         | Typealias    | Example                 |
|------|--------------------|--------------|-------------------------|
| File | FilePathSerializer | FileAsString | `"/home/emil/file.txt"` |

## `java.math`

| Type       | Serializer                   | Typealias               | Example                    |
|------------|------------------------------|-------------------------|----------------------------|
| BigDecimal | BigDecimalAsStringSerializer | BigDecimalAsString      | `"3.12345678901234567890"` |
| BigDecimal | BigDecimalAsDouble           | BigDecimalAsDouble      | `3.1234567890123457`       |
| BigDecimal | BigDecimalAsJsonLiteral      | BigDecimalAsJsonLiteral | `3.12345678901234567890`   |
| BigInteger | BigIntegerAsString           | BigIntegerAsString      | `"9223372036854775808"`    |
| BigInteger | BigIntegerAsJsonLiteral      | BigIntegerAsJsonLiteral | `9223372036854775808`      |

## `java.net`

| Type | Serializer    | Example                     |
|------|---------------|-----------------------------|
| URL  | UrlSerializer | `"https://www.github.com"`  |
| URI  | UriSerializer | `"telnet://192.0.2.16:80/"` |

## `java.util`

| Type          | Serializer              | Example                                  |
|---------------|-------------------------|------------------------------------------|
| UUID          | UuidSerializer          | `"8d44ac12-394b-4a50-ad6a-eff2cff129c4"` |
| AtomicInteger | AtomicIntegerSerializer | `5`                                      |
| AtomicBoolean | AtomicBooleanSerializer | `true`                                   |
| AtomicLong    | AtomicLongSerializer    | `22`                                     |
| Locale        | LocaleSerializer        | `"en_US"`                                |
