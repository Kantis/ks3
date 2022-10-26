## `java.time`

| Type          | Serializer                      | Example                 |
|---------------|---------------------------------|-------------------------|
| LocalDate     | LocalDateAsStringSerializer     | `"2022-10-23"`          |
| LocalDateTime | LocalDateTimeAsStringSerializer | `"2022-10-23T21:44:00"` |
| YearMonth     | YearMonthAsStringSerializer     | `"2022-10"`             |
| Year          | YearAsStringSerializer          | `"2022"`                |
| Year          | YearAsIntSerializer             | `2022`                  |

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
