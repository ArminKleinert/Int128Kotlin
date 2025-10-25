package de.kleinert.numerics

data class UInt128(val high: ULong, val low: ULong) : Comparable<UInt128>, Number() {
    companion object {
        val ZERO: UInt128 = UInt128(0UL, 0UL)
        val ONE: UInt128 = UInt128(0UL, 1UL)
        val MAX_VALUE: UInt128 = UInt128(ULong.MAX_VALUE, ULong.MAX_VALUE)
        val MIN_VALUE: UInt128 = ZERO

        const val SIZE_BYTES: Int = 16
        const val SIZE_BITS: Int = 128

        fun valueOf(high: ULong, low: ULong): UInt128 = UInt128(high, low)
        fun valueOf(low: ULong): UInt128 = UInt128(0uL, low)
        fun valueOf(low: UInt): UInt128 = UInt128(0uL, low.toULong())
        fun valueOf(low: Int): UInt128 = if (low < 0) throw IllegalArgumentException() else UInt128(0uL, low.toULong())

        fun valueOf(s: String, base: Int = 10): UInt128 {
            require(base in 2..36)

            if (s.isEmpty())
                throw NumberFormatException()

            val str = s.trimStart(' ', '\t', '+')

            if (str.isEmpty())
                throw NumberFormatException()

            var res = ZERO
            for (c in str) {
                if (c == ' ' || c == '\t') break

                var digit = c.code

                digit -= if (base > 10) {
                    when (digit) {
                        in 'A'.code..'Z'.code -> 'A'.code - 10
                        in 'a'.code..'z'.code -> 'a'.code - 10
                        in '0'.code..'9'.code -> '0'.code
                        else -> throw NumberFormatException()
                    }
                } else {
                    if (digit !in '0'.code..(base + '0'.code))
                        throw NumberFormatException()
                    '0'.code
                }

                res *= base.toULong()
                res += digit.toULong()
            }

            return res
        }

        fun valueOfOrNull(s: String, base: Int = 10): UInt128? = try {
            valueOf(s, base)
        } catch (nfe: NumberFormatException) {
            null
        }

        @Suppress("NOTHING_TO_INLINE")
        internal inline fun uint128MulHelper(lhs: ULong, rhs: ULong): UInt128 {
            val uint32Max = UInt.MAX_VALUE.toULong()

            val leftLo32 = lhs and uint32Max
            val leftHi32 = lhs shr 32
            val rightLo32 = rhs and uint32Max
            val rightHi32 = rhs shr 32

            // Compute each component of the product as the sum of multiple 32 bit
            // products.
            val loLo = leftLo32 * rightLo32
            val loHi = leftLo32 * rightHi32
            val hiLo = leftHi32 * rightLo32
            val hiHi = leftHi32 * rightHi32

            val carry = ((loLo shr 32) + (loHi and uint32Max) + (hiLo and uint32Max)) shr 32

            // Assemble the final product from these components, adding the carry
            // to the high 64 bits.
            return UInt128(
                hiHi + (loHi shr 32) + (hiLo shr 32) + carry,
                loLo + (loHi shl 32) + (hiLo shl 32),
            )
        }
    }

    override fun compareTo(other: UInt128): Int {
        if (high == other.high) return low.compareTo(other.low)
        return high.compareTo(other.high)
    }

    infix operator fun plus(other: UInt): UInt128 = plus(UInt128(0uL, other.toULong()))
    infix operator fun plus(other: ULong): UInt128 = plus(UInt128(0uL, other))

    infix operator fun plus(other: UInt128): UInt128 {
        val newLow = low + other.low
        var newHigh = high + other.high
        if (newLow < low) newHigh++
        return UInt128(newHigh, newLow)
    }

    infix operator fun minus(other: UInt): UInt128 = minus(valueOf(other))
    infix operator fun minus(other: ULong): UInt128 = minus(valueOf(other))

    infix operator fun minus(other: UInt128): UInt128 {
        val newLow = this.low - other.low
        val borrow = if (this.low < other.low) 1uL else 0uL // <-- use 1uL (ULong)
        val newHigh = this.high - other.high - borrow
        return UInt128(newHigh, newLow)
    }

    infix operator fun times(other: UInt): UInt128 = times(valueOf(other))
    infix operator fun times(other: ULong): UInt128 = times(valueOf(other))

    infix operator fun times(other: UInt128): UInt128 {
        val i = uint128MulHelper(low, other.low)
        val j = uint128MulHelper(low, other.high)
        val k = uint128MulHelper(high, other.low)

        val temp = i.plus(UInt128(j.low, 0uL))
        return temp.plus(UInt128(k.low, 0uL))
    }

    fun divMod(other: UInt): Pair<UInt128, UInt128> = divMod(valueOf(other))

    infix fun shl(count: Int): UInt128 {
        if (count == 0) return this
        if (count >= 128) return ZERO
        if (count >= 64) return UInt128(low shl (count - 64), 0uL)

        val newHigh = (high shl count) or (low shr (64 - count))
        val newLow = low shl count
        return UInt128(newHigh, newLow)
    }

    infix fun shr(count: Int): UInt128 {
        if (count == 0) return this
        if (count >= 128) return ZERO
        if (count >= 64) return UInt128(0uL, high shr (count - 64))

        val newLow = (low shr count) or (high shl (64 - count))
        val newHigh = high shr count
        return UInt128(newHigh, newLow)
    }

    fun divMod(other: UInt128): Pair<UInt128, UInt128> {
        if (other == ZERO) throw ArithmeticException("Division by zero.")
        if (compareTo(other) < 0) return ZERO to this
        if (equals(other)) return ONE to ZERO

        var quotient = ZERO
        var remainder = ZERO

        for (i in 127 downTo 0) {
            // Shift remainder left by 1 and bring down the next bit
            remainder = remainder.shl(1)
            if ((this shr i).low and 1uL == 1uL) {
                remainder += ONE
            }

            if (remainder >= other) {
                remainder -= other
                quotient += (ONE shl i)
            }
        }
        return Pair(quotient, remainder)
    }

    fun divMod(other: ULong): Pair<UInt128, ULong> {
        if (other == 0uL)
            throw ArithmeticException("Division by zero")
        val rem = this.high % other
        val highQuotient = this.high / other

        // Step 2: divide combined (rem:low)
        val (lowQuotient, finalRem) = run {
            // Emulate 128/64 division
            var quotient = 0uL
            var r = rem
            for (i in 63 downTo 0) {
                r = (r shl 1) or ((this.low shr i) and 1uL)
                if (r >= other) {
                    r -= other
                    quotient = quotient or (1uL shl i)
                }
            }
            Pair(quotient, r)
        }

        return Pair(UInt128(highQuotient, lowQuotient), finalRem)
    }

    infix operator fun div(other: UInt): UInt128 = div(other.toULong())
    infix operator fun div(other: ULong): UInt128 = divMod(other).first

    infix operator fun div(other: UInt128): UInt128 = divMod(other).first

    infix operator fun rem(other: UInt): UInt128 = rem(valueOf(other))
    infix operator fun rem(other: ULong): UInt128 = divMod(other).first

    infix operator fun rem(other: UInt128): UInt128 = divMod(other).second

    fun inv(): UInt128 = UInt128(high.inv(), low.inv())

    infix fun and(other: UInt128): UInt128 = UInt128(high and other.high, low and other.low)
    infix fun or(other: UInt128): UInt128 = UInt128(high or other.high, low or other.low)
    infix fun xor(other: UInt128): UInt128 = UInt128(high xor other.high, low xor other.low)

    fun toULong(): ULong = low
    fun toUByte(): UByte = low.toUByte()
    fun toUShort(): UShort = low.toUShort()
    fun toUInt(): UInt = low.toUInt()
    override fun toLong(): Long = low.toLong()
    override fun toByte(): Byte = toLong().toByte()
    override fun toShort(): Short = toLong().toShort()
    override fun toInt(): Int = toLong().toInt()
    override fun toFloat(): Float = toLong().toFloat()
    override fun toDouble(): Double = toLong().toDouble()
    fun toInt128(): Int128 = Int128(high, low)

    fun isZero(): Boolean = high == 0uL && low == 0uL

    fun toString(base: Int): String {
        require((base in 2..32) || base == 64) { "Base must be between 2..32 or 64" }

        if (isZero()) return "0"

        val digits: CharArray = when (base) {
            in 2..32 -> "0123456789abcdefghijklmnopqrstuvwxyz".take(base).toCharArray()
            64 -> "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray()
            else -> throw IllegalArgumentException("Unsupported base: $base")
        }

        var value = this
        val sb = StringBuilder()

        val divisor = valueOf(base)

        while (!value.isZero()) {
            sb.append(digits[(value % divisor).toInt()])
            value /= divisor
        }

        return sb.reverse().toString()
    }

    override fun toString(): String = toString(10)

    fun numberOfLeadingZeros(): Int {
        if (high == 0uL) return low.countLeadingZeroBits() + 64
        return high.countLeadingZeroBits()
    }

    fun numberOfTrailingZeros(): Int {
        if (low == 0uL) return high.countTrailingZeroBits() + 64
        return low.countTrailingZeroBits()
    }

    fun countOneBits(): Int = high.countOneBits() + low.countOneBits()
    fun increment(): UInt128 = plus(ONE)
    fun decrement(): UInt128 = minus(ONE)

    operator fun rangeTo(other: UInt128) = UInt128Range(this, other)
    operator fun rangeUntil(other: UInt128) = UInt128Range(this, other.increment())

    infix fun rotateRight(dist: Int): UInt128 {
        val shift = dist and 127
        return (this shr shift) or (this shl (128 - shift))
    }

    infix fun rotateLeft(dist: Int): UInt128 {
        val shift = dist and 127
        return (this shr (128 - shift)) or (this shl shift)
    }

//    override infix fun equals(other: Any?): Boolean {
//        if (this === other)
//            return true
//        return when (other) {
//            is UInt128 -> high == other.high && low == other.low
//            is Int128 -> other.isPositive() && other.high == high && other.low == low
//            is Byte, is Short, is Int, is Long -> high == 0uL && (other as Number).toLong() >= 0L && low == other
//            is UByte, is UShort, is UInt, is ULong -> high == 0uL && other == low
//
//            is Float, is Double -> {
//                require(other is Number)
//                val d = other.toDouble()
//                val n = d.toLong()
//                if (n - d != 0.0) false // The number is not exact -> can't be equal
//                else equals(n)
//            }
//
//            else -> false
//        }
//    }
//
//    override fun hashCode(): Int {
//        var result = high.hashCode()
//        result = 31 * result + low.hashCode()
//        return result
//    }
}