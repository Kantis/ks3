@file:Suppress("unused")

package io.ks3.java.`typealias`

import io.ks3.java.io.FilePathSerializer
import io.ks3.java.net.UriSerializer
import io.ks3.java.net.UrlSerializer
import io.ks3.java.time.InstantAsLongSerializer
import io.ks3.java.time.InstantAsStringSerializer
import io.ks3.java.time.LocalDateAsStringSerializer
import io.ks3.java.time.LocalDateTimeAsStringSerializer
import io.ks3.java.time.LocalTimeAsStringSerializer
import io.ks3.java.time.OffsetDateTimeAsStringSerializer
import io.ks3.java.time.YearMonthAsStringSerializer
import io.ks3.java.util.UuidSerializer
import kotlinx.serialization.Serializable
import java.io.File
import java.net.URI
import java.net.URL
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.YearMonth
import java.util.UUID

// -- IO --
@Deprecated(
   "Changed package to io.ks3.java.typealiases to avoid need for backticks in imports. This package will be removed in 1.0",
   ReplaceWith("FileAsString", "io.ks3.java.typealiases.FileAsString"),
)
typealias FileAsString =
   @Serializable(with = FilePathSerializer::class)
   File

// -- NET --
@Deprecated(
   "Changed package to io.ks3.java.typealiases to avoid need for backticks in imports. This package will be removed in 1.0",
   ReplaceWith("UrlAsString", "io.ks3.java.typealiases.UrlAsString"),
)
typealias UrlAsString =
   @Serializable(with = UrlSerializer::class)
   URL

@Deprecated(
   "Changed package to io.ks3.java.typealiases to avoid need for backticks in imports. This package will be removed in 1.0",
   ReplaceWith("UriAsString", "io.ks3.java.typealiases.UriAsString"),
)
typealias UriAsString =
   @Serializable(with = UriSerializer::class)
   URI

// -- TIME --
@Deprecated(
   "Changed package to io.ks3.java.typealiases to avoid need for backticks in imports. This package will be removed in 1.0",
   ReplaceWith("YearMonthAsString", "io.ks3.java.typealiases.YearMonthAsString"),
)
typealias YearMonthAsString =
   @Serializable(with = YearMonthAsStringSerializer::class)
   YearMonth

@Deprecated(
   "Changed package to io.ks3.java.typealiases to avoid need for backticks in imports. This package will be removed in 1.0",
   ReplaceWith("InstantAsString", "io.ks3.java.typealiases.InstantAsString"),
)
typealias InstantAsString =
   @Serializable(with = InstantAsStringSerializer::class)
   Instant

@Deprecated(
   "Changed package to io.ks3.java.typealiases to avoid need for backticks in imports. This package will be removed in 1.0",
   ReplaceWith("InstantAsLong", "io.ks3.java.typealiases.InstantAsLong"),
)
typealias InstantAsLong =
   @Serializable(with = InstantAsLongSerializer::class)
   Instant

@Deprecated(
   "Changed package to io.ks3.java.typealiases to avoid need for backticks in imports. This package will be removed in 1.0",
   ReplaceWith("LocalDateAsString", "io.ks3.java.typealiases.LocalDateAsString"),
)
typealias LocalDateAsString =
   @Serializable(with = LocalDateAsStringSerializer::class)
   LocalDate

@Deprecated(
   "Changed package to io.ks3.java.typealiases to avoid need for backticks in imports. This package will be removed in 1.0",
   ReplaceWith("LocalDateTimeAsString", "io.ks3.java.typealiases.LocalDateTimeAsString"),
)
typealias LocalDateTimeAsString =
   @Serializable(with = LocalDateTimeAsStringSerializer::class)
   LocalDateTime

@Deprecated(
   "Changed package to io.ks3.java.typealiases to avoid need for backticks in imports. This package will be removed in 1.0",
   ReplaceWith("LocalTimeAsString", "io.ks3.java.typealiases.LocalTimeAsString"),
)
typealias LocalTimeAsString =
   @Serializable(with = LocalTimeAsStringSerializer::class)
   LocalTime

@Deprecated(
   "Changed package to io.ks3.java.typealiases to avoid need for backticks in imports. This package will be removed in 1.0",
   ReplaceWith("OffsetDateTimeAsString", "io.ks3.java.typealiases.OffsetDateTimeAsString"),
)
typealias OffsetDateTimeAsString =
   @Serializable(with = OffsetDateTimeAsStringSerializer::class)
   OffsetDateTime

// -- UTIL --
@Deprecated(
   "Changed package to io.ks3.java.typealiases to avoid need for backticks in imports. This package will be removed in 1.0",
   ReplaceWith("UuidAsString", "io.ks3.java.typealiases.UuidAsString"),
)
typealias UuidAsString =
   @Serializable(with = UuidSerializer::class)
   UUID
