# (U)Int128Kotlin

Int128 and UInt128 class for Kotlin.

JVM Kotlin, being build on top of the JVM, has no native support for integer-sizes over 64 bit. Here you go: Classes for 128-bit integers.

Initialization:

Both classes are backed by two ULong fields. Direct initialization is possible, but a few static `valueOf` methods are also provided.

```kotlin
Int128(high: ULong, low: ULong)
UInt128(high: ULong, low: ULong)

Int128.valueOf(high: ULong, low: ULong): Int128
Int128.valueOf(low: ULong): Int128
Int128.valueOf(low: UInt): Int128
Int128.valueOf(low: Int): Int128
Int128.valueOf(low: Long): Int128
Int128.valueOf(s: String, base: Int = 10): Int128 // Throws IllegalArgumentException for invalid base or NumberFormatException if the string is invalid.
Int128.valueOfOrNull(s: String, base: Int = 10): Int128?

UInt128.valueOf(high: ULong, low: ULong): UInt128
UInt128.valueOf(low: ULong): UInt128
UInt128.valueOf(low: UInt): UInt128
UInt128.valueOf(low: Int): UInt128 // Fails if the argument is negative
UInt128.valueOf(s: String, base: Int = 10): UInt128 // Throws IllegalArgumentException for invalid base or NumberFormatException if the string is invalid.
UInt128.valueOfOrNull(s: String, base: Int = 10): UInt128?
```



Operations:

| Operation             | Int128 | UInt128 | Notes                                                 |
|:----------------------|:------:|:-------:|:------------------------------------------------------|
| plus                  |   ☑    |    ☑    | Overloads for some primitives are included.           |
| minus                 |   ☑    |    ☑    | Overloads for some primitives are included.           |
| unaryMinus            |   ☑    |         |                                                       |
| times                 |   ☑    |    ☑    | Overloads for some primitives are included.           |
| div                   |   ☑    |    ☑    | Overloads for some primitives are included.           |
| rem                   |   ☑    |    ☑    | Overloads for some primitives are included.           |
| divMod                |   ☑    |    ☑    |                                                       |
| decrement             |   ☑    |    ☑    |                                                       |
| increment             |   ☑    |    ☑    |                                                       |
| *Shifts*              |        |         |                                                       |
| shl                   |   ☑    |    ☑    |                                                       |
| shr                   |   ☑    |    ☑    |                                                       |
| ushr                  |   ☑    |         |                                                       |
| rotateLeft            |   ☑    |    ☑    |                                                       |
| rotateRight           |   ☑    |    ☑    |                                                       |
| *Binary Logic*        |        |         |                                                       |
| and                   |   ☑    |    ☑    |                                                       |
| or                    |   ☑    |    ☑    |                                                       |
| xor                   |   ☑    |    ☑    |                                                       |
| inv                   |   ☑    |    ☑    | Bit inversion.                                        |
| *Utility checks*      |        |         |                                                       |
| isNegative            |   ☑    |         |                                                       |
| isPositive            |   ☑    |    ☑    |                                                       |
| isZero                |   ☑    |    ☑    |                                                       |
| *Bit counting*        |        |         |                                                       |
| countOneBits          |   ☑    |    ☑    |                                                       |
| numberOfLeadingZeros  |   ☑    |    ☑    |                                                       |
| numberOfTrailingZeros |   ☑    |    ☑    |                                                       |
| *Conversions*         |        |         |                                                       |
| toString              |   ☑    |    ☑    | Defaults to base 10, but supports bases 2..32 and 64. |
| toByte                |   ☑    |    ☑    | Uses `toLong` as an intermediate.                     |
| toDouble              |   ☑    |    ☑    | Uses `toLong` as an intermediate.                     |
| toFloat               |   ☑    |    ☑    | Uses `toLong` as an intermediate.                     |
| toInt                 |   ☑    |    ☑    | Uses `toLong` as an intermediate.                     |
| toLong                |   ☑    |    ☑    |                                                       |
| toShort               |   ☑    |    ☑    | Uses `toLong` as an intermediate.                     |
| toUByte               |   ☑    |    ☑    | Uses `toULong` as an intermediate.                    |
| toUInt                |   ☑    |    ☑    | Uses `toULong` as an intermediate.                    |
| toULong               |   ☑    |    ☑    | Uses `toULong` as an intermediate.                    |
| toUShort              |   ☑    |    ☑    | Uses `toULong` as an intermediate.                    |
| *Comparisons*         |        |         |                                                       |
| equals                |   ☑    |    ☑    |                                                       |
| compareTo             |   ☑    |    ☑    |                                                       |
