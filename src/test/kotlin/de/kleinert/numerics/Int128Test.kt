package de.kleinert.numerics

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Int128Test {
    @Test
    fun sizeBytes(){
        Assertions.assertEquals(16, Int128.SIZE_BYTES)
    }
    @Test
    fun sizeBits(){
        Assertions.assertEquals(128, Int128.SIZE_BITS)
    }
    @Test
    fun testToString() {
        var v = Int128.ZERO
        Assertions.assertEquals("0", v.toString())
        Assertions.assertEquals("0", v.toString(2))
        Assertions.assertEquals("0", v.toString(8))
        Assertions.assertEquals("0", v.toString(10))
        Assertions.assertEquals("0", v.toString(16))

        v = Int128.ONE
        Assertions.assertEquals("1", v.toString())
        Assertions.assertEquals("1", v.toString(2))
        Assertions.assertEquals("1", v.toString(8))
        Assertions.assertEquals("1", v.toString(10))
        Assertions.assertEquals("1", v.toString(16))

        v = Int128.MINUS_ONE
        Assertions.assertEquals("-1", v.toString())
        Assertions.assertEquals("-1", v.toString(2))
        Assertions.assertEquals("-1", v.toString(8))
        Assertions.assertEquals("-1", v.toString(10))
        Assertions.assertEquals("-1", v.toString(16))

        v = Int128(0x100uL, 0x000000000000000uL)
        Assertions.assertEquals("4722366482869645213696", v.toString())
        Assertions.assertEquals(
            "1000000000000000000000000000000000000000000000000000000000000000000000000",
            v.toString(2)
        )
        Assertions.assertEquals("1000000000000000000000000", v.toString(8))
        Assertions.assertEquals("4722366482869645213696", v.toString(10))
        Assertions.assertEquals("1000000000000000000", v.toString(16))
    }

    @Test
    fun valueOfULong() {
        Assertions.assertEquals(Int128.ZERO, Int128.valueOf(0uL))
        Assertions.assertEquals(Int128.ONE, Int128.valueOf(1uL))
        Assertions.assertEquals(Int128(0uL, ULong.MAX_VALUE), Int128.valueOf(ULong.MAX_VALUE))
    }

    @Test
    fun valueOfUInt() {
        Assertions.assertEquals(Int128.ZERO, Int128.valueOf(0u))
        Assertions.assertEquals(Int128.ONE, Int128.valueOf(1u))
        Assertions.assertEquals(Int128(0uL, UInt.MAX_VALUE.toULong()), Int128.valueOf(UInt.MAX_VALUE))
    }

    @Test
    fun valueOfInt() {
        //Assertions.assertThrows(IllegalArgumentException::class.java) { Int128.valueOf(-1) }
        //Assertions.assertThrows(IllegalArgumentException::class.java) { Int128.valueOf(Int.MIN_VALUE) }
        Assertions.assertEquals(Int128.ZERO, Int128.valueOf(0))
        Assertions.assertEquals(Int128.ONE, Int128.valueOf(1))
        Assertions.assertEquals(Int128(0uL, Int.MAX_VALUE.toULong()), Int128.valueOf(Int.MAX_VALUE))
    }

    @Test
    fun valueOfLong() {
        //Assertions.assertThrows(IllegalArgumentException::class.java) { Int128.valueOf(-1L) }
        //Assertions.assertThrows(IllegalArgumentException::class.java) { Int128.valueOf(Long.MIN_VALUE) }
        Assertions.assertEquals(Int128.ZERO, Int128.valueOf(0L))
        Assertions.assertEquals(Int128.ONE, Int128.valueOf(1L))
        Assertions.assertEquals(Int128(0uL, Long.MAX_VALUE.toULong()), Int128.valueOf(Long.MAX_VALUE))
    }

    @Test
    fun valueOfString() {
        Assertions.assertThrows(IllegalArgumentException::class.java) { Int128.valueOf("") }
        Assertions.assertThrows(NumberFormatException::class.java) { Int128.valueOf("-") }
        Assertions.assertEquals(Int128.ZERO, Int128.valueOf("0"))
        Assertions.assertEquals(Int128.ONE, Int128.valueOf("1"))
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.valueOf("-1"))
        Assertions.assertEquals(
            Int128(0xFFFFFFFFFFFFFF00uL, 0x000000000000000uL),
            Int128.valueOf("-4722366482869645213696")
        )
        Assertions.assertEquals(Int128(0x100uL, 0x000000000000000uL), Int128.valueOf("4722366482869645213696"))
        Assertions.assertEquals(Int128(0x100uL, 0x000000000000000uL), Int128.valueOf("1000000000000000000", 16))
    }

    @Test
    fun valueOfStringOrNull() {
        Assertions.assertNull(Int128.valueOfOrNull(""))
        Assertions.assertNull(Int128.valueOfOrNull("-"))
        Assertions.assertEquals(Int128.ZERO, Int128.valueOfOrNull("0"))
        Assertions.assertEquals(Int128.ONE, Int128.valueOfOrNull("1"))
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.valueOfOrNull("-1"))
        Assertions.assertEquals(
            Int128(0xFFFFFFFFFFFFFF00uL, 0x000000000000000uL),
            Int128.valueOfOrNull("-4722366482869645213696")
        )
        Assertions.assertEquals(Int128(0x100uL, 0x000000000000000uL), Int128.valueOfOrNull("4722366482869645213696"))
        Assertions.assertEquals(Int128(0x100uL, 0x000000000000000uL), Int128.valueOfOrNull("1000000000000000000", 16))
    }

    @Test
    fun compareTo() {
        Assertions.assertTrue(Int128.ONE > Int128.ZERO)
        Assertions.assertTrue(Int128.MAX_VALUE > Int128.ZERO)
        Assertions.assertTrue(Int128.MAX_VALUE > Int128.MIN_VALUE)

        Assertions.assertTrue(Int128.ZERO < Int128.ONE)
        Assertions.assertTrue(Int128.ZERO < Int128.MAX_VALUE)
        Assertions.assertTrue(Int128.MINUS_ONE < Int128.MAX_VALUE)
    }

    @Test
    fun equals() {
        Assertions.assertTrue(Int128.ZERO == Int128.valueOf(0uL, 0uL))
        Assertions.assertTrue(Int128.ONE == Int128.valueOf(0uL, 1uL))
        Assertions.assertTrue(Int128.ONE != Int128.valueOf(0uL, 0uL))
        Assertions.assertTrue(Int128.MAX_VALUE == Int128(Long.MAX_VALUE.toULong(), ULong.MAX_VALUE))
        Assertions.assertTrue(Int128.MIN_VALUE == Int128(Long.MIN_VALUE.toULong(), 0uL))
    }

    @Test
    fun isNegative() {
        Assertions.assertFalse(Int128.ZERO.isNegative())
        Assertions.assertFalse(Int128.ONE.isNegative())
        Assertions.assertFalse(Int128.MAX_VALUE.isNegative())
        Assertions.assertTrue(Int128.MIN_VALUE.isNegative())
    }

    @Test
    fun isPositive() {
        Assertions.assertTrue(Int128.ZERO.isPositive())
        Assertions.assertTrue(Int128.ONE.isPositive())
        Assertions.assertTrue(Int128.MAX_VALUE.isPositive())
        Assertions.assertFalse(Int128.MIN_VALUE.isPositive())
    }

    @Test
    fun isZero() {
        Assertions.assertTrue(Int128.ZERO.isZero())
        Assertions.assertFalse(Int128.ONE.isZero())
        Assertions.assertFalse(Int128.MAX_VALUE.isZero())
        Assertions.assertFalse(Int128.MIN_VALUE.isZero())
    }

    @Test
    fun and() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO and Int128.ONE)
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO and Int128.ZERO)
        Assertions.assertEquals(Int128.ZERO, Int128.MAX_VALUE and Int128.ZERO)
        Assertions.assertEquals(Int128.ONE, Int128.ONE and Int128.ONE)
        Assertions.assertEquals(Int128.ONE, Int128.valueOf(3L) and Int128.ONE)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE and Int128.MAX_VALUE)
    }

    @Test
    fun or() {
        Assertions.assertEquals(Int128.ONE, Int128.ZERO or Int128.ONE)
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO or Int128.ZERO)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE or Int128.ZERO)
        Assertions.assertEquals(Int128.ONE, Int128.ONE or Int128.ONE)
        Assertions.assertEquals(Int128.valueOf(3L), Int128.valueOf(3L) or Int128.ONE)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE or Int128.MAX_VALUE)
    }

    @Test
    fun xor() {
        Assertions.assertEquals(Int128.ONE, Int128.ZERO xor Int128.ONE)
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO xor Int128.ZERO)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE xor Int128.ZERO)
        Assertions.assertEquals(Int128.ZERO, Int128.ONE xor Int128.ONE)
        Assertions.assertEquals(Int128.valueOf(2L), Int128.valueOf(3L) xor Int128.ONE)
        Assertions.assertEquals(Int128.ZERO, Int128.MAX_VALUE xor Int128.MAX_VALUE)
    }

    @Test
    fun shiftRight() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO shr 1)
        Assertions.assertEquals(Int128.ZERO, Int128.MAX_VALUE shr 256)
        Assertions.assertEquals(Int128.ONE, Int128.ONE shr 0)
        Assertions.assertEquals(Int128.valueOf(2), Int128.valueOf(4) shr 1)
        Assertions.assertEquals(Int128.valueOf(2), Int128.valueOf(5) shr 1)
        Assertions.assertEquals(Int128.valueOf(3), Int128.valueOf(6) shr 1)
        Assertions.assertEquals(Int128.valueOf(3), Int128.valueOf(7) shr 1)
        Assertions.assertEquals(Int128.valueOf(1uL, 0uL), Int128.ONE shr 64)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE, 0uL), Int128.MAX_VALUE shr 64)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE shr 1, 0uL), Int128.MAX_VALUE shr 65)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE, ULong.MAX_VALUE), Int128.MINUS_ONE shr 65)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE, ULong.MAX_VALUE shl 62), Int128.MIN_VALUE shr 65)
    }

    @Test
    fun shiftLeft() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO shl 1)
        Assertions.assertEquals(Int128.valueOf(0uL, 0uL), Int128.ONE shl 256)
        Assertions.assertEquals(Int128.ONE, Int128.ONE shl 0)
        Assertions.assertEquals(Int128.valueOf(2), Int128.ONE shl 1)
        Assertions.assertEquals(Int128.valueOf(1uL, 0uL), Int128.ONE shl 64)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE, 0uL), Int128.MAX_VALUE shl 64)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE shl 1, 0uL), Int128.MAX_VALUE shl 65)
    }

    @Test
    fun shiftRightUnsigned() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO ushr 1)
        Assertions.assertEquals(Int128.valueOf(0uL, 0uL), Int128.MAX_VALUE ushr 256)
        Assertions.assertEquals(Int128.ONE, Int128.ONE ushr 0)
        Assertions.assertEquals(Int128.valueOf(2), Int128.valueOf(4) ushr 1)
        Assertions.assertEquals(Int128.valueOf(2), Int128.valueOf(5) ushr 1)
        Assertions.assertEquals(Int128.valueOf(3), Int128.valueOf(6) ushr 1)
        Assertions.assertEquals(Int128.valueOf(3), Int128.valueOf(7) ushr 1)
        Assertions.assertEquals(Int128.valueOf(1uL, 0uL), Int128.ONE ushr 64)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE, 0uL), Int128.MAX_VALUE ushr 64)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE shr 1, 0uL), Int128.MAX_VALUE ushr 65)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE shr 1, 0uL), Int128.MINUS_ONE ushr 65)
        Assertions.assertEquals(Int128.valueOf(0uL, 0uL), Int128.MIN_VALUE ushr 65)
    }

    @Test
    fun numberOfLeadingZeros() {
        Assertions.assertEquals(128, Int128.ZERO.numberOfLeadingZeros())
        Assertions.assertEquals(127, Int128.ONE.numberOfLeadingZeros())
        Assertions.assertEquals(1, Int128.MAX_VALUE.numberOfLeadingZeros())
        Assertions.assertEquals(0, Int128.MIN_VALUE.numberOfLeadingZeros())

        Assertions.assertEquals(126, Int128.valueOf(2L).numberOfLeadingZeros())
    }

    @Test
    fun numberOfTrailingZeros() {
        Assertions.assertEquals(128, Int128.ZERO.numberOfTrailingZeros())
        Assertions.assertEquals(0, Int128.ONE.numberOfTrailingZeros())
        Assertions.assertEquals(0, Int128.MAX_VALUE.numberOfTrailingZeros())
        Assertions.assertEquals(127, Int128.MIN_VALUE.numberOfTrailingZeros())

        Assertions.assertEquals(1, Int128.valueOf(2L).numberOfTrailingZeros())
    }

    @Test
    fun bitCount() {
        Assertions.assertEquals(0, Int128.ZERO.countOneBits())
        Assertions.assertEquals(1, Int128.ONE.countOneBits())
        Assertions.assertEquals(127, Int128.MAX_VALUE.countOneBits())
        Assertions.assertEquals(1, Int128.MIN_VALUE.countOneBits())
        Assertions.assertEquals(128, Int128.MINUS_ONE.countOneBits())

        Assertions.assertEquals(1, Int128.valueOf(2L).countOneBits())

        Assertions.assertEquals(2, Int128.valueOf(3L).countOneBits())
        Assertions.assertEquals(2, Int128.valueOf(1uL, 1uL).countOneBits())
    }

    @Test
    fun plusInt128() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO + Int128.ZERO)
        Assertions.assertEquals(Int128.ONE, Int128.ZERO + Int128.ONE)
        Assertions.assertEquals(Int128.ONE, Int128.ONE + Int128.ZERO)
        Assertions.assertEquals(Int128(1uL, 0uL), Int128(0uL, ULong.MAX_VALUE) + Int128.ONE)
        Assertions.assertEquals(Int128(1uL, 0uL), Int128.ONE + Int128(0uL, ULong.MAX_VALUE))
        Assertions.assertEquals(Int128(9223372036854775808uL, 0uL), Int128.MAX_VALUE + Int128.ONE)
    }

    @Test
    fun testPlusLong() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO + 0L)
        Assertions.assertEquals(Int128.ONE, Int128.ZERO + 1L)
        Assertions.assertEquals(Int128.ONE, Int128.ONE + 0L)
        Assertions.assertEquals(Int128(1uL, 0uL), Int128(0uL, ULong.MAX_VALUE) + 1L)
        Assertions.assertEquals(Int128(9223372036854775808uL, 0uL), Int128.MAX_VALUE + 1L)
    }

    @Test
    fun testPlusInt() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO + 0)
        Assertions.assertEquals(Int128.ONE, Int128.ZERO + 1)
        Assertions.assertEquals(Int128.ONE, Int128.ONE + 0)
        Assertions.assertEquals(Int128(1uL, 0uL), Int128(0uL, ULong.MAX_VALUE) + 1)
        Assertions.assertEquals(Int128(9223372036854775808uL, 0uL), Int128.MAX_VALUE + 1u)
    }

    @Test
    fun testPlusUInt() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO + 0u)
        Assertions.assertEquals(Int128.ONE, Int128.ZERO + 1u)
        Assertions.assertEquals(Int128.ONE, Int128.ONE + 0u)
        Assertions.assertEquals(Int128(1uL, 0uL), Int128(0uL, ULong.MAX_VALUE) + 1L)
        Assertions.assertEquals(Int128(9223372036854775808uL, 0uL), Int128.MAX_VALUE + 1u)
    }

    @Test
    fun testPlusULong() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO + 0L)
        Assertions.assertEquals(Int128.ONE, Int128.ZERO + 1L)
        Assertions.assertEquals(Int128.ONE, Int128.ONE + 0L)
        Assertions.assertEquals(Int128(1uL, 0uL), Int128(0uL, ULong.MAX_VALUE) + 1L)
        Assertions.assertEquals(Int128(1uL, 0uL), Int128.ONE + ULong.MAX_VALUE)
        Assertions.assertEquals(Int128(9223372036854775808uL, 0uL), Int128.MAX_VALUE + 1uL)
    }

    @Test
    fun minusInt128() {
        Assertions.assertEquals(Int128.ZERO, Int128.ONE - Int128.ONE)
        Assertions.assertEquals(Int128.ONE, Int128.ONE - Int128.ZERO)
        Assertions.assertEquals(Int128.ZERO, Int128.MINUS_ONE - Int128.MINUS_ONE)
        Assertions.assertEquals(Int128.ONE, Int128.ZERO - Int128.MINUS_ONE)
        Assertions.assertEquals(Int128(Long.MAX_VALUE.toULong(), ULong.MAX_VALUE - 1u), Int128.MAX_VALUE - Int128.ONE)
    }

    @Test
    fun minusLong() {
        Assertions.assertEquals(Int128.ZERO, Int128.ONE - 1L)
        Assertions.assertEquals(Int128.ONE, Int128.ONE - 0L)
        Assertions.assertEquals(Int128.ZERO, Int128.MINUS_ONE - -1L)
        Assertions.assertEquals(Int128.ONE, Int128.ZERO - -1L)
        Assertions.assertEquals(Int128(Long.MAX_VALUE.toULong(), ULong.MAX_VALUE - 1u), Int128.MAX_VALUE - 1L)
    }

    @Test
    fun minusInt() {
        Assertions.assertEquals(Int128.ZERO, Int128.ONE - 1)
        Assertions.assertEquals(Int128.ONE, Int128.ONE - 0)
        Assertions.assertEquals(Int128.ZERO, Int128.MINUS_ONE - -1)
        Assertions.assertEquals(Int128.ONE, Int128.ZERO - -1)
        Assertions.assertEquals(Int128(Long.MAX_VALUE.toULong(), ULong.MAX_VALUE - 1u), Int128.MAX_VALUE - 1)
    }

    @Test
    fun timesInt128() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO * Int128.MAX_VALUE)
        Assertions.assertEquals(Int128.ZERO, Int128.MAX_VALUE * Int128.ZERO)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.ONE * Int128.MAX_VALUE)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE * Int128.ONE)
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.MINUS_ONE * Int128.ONE)
        Assertions.assertEquals(Int128.MIN_VALUE + Int128.ONE, Int128.MINUS_ONE * Int128.MAX_VALUE)
    }

    @Test
    fun timesLong() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO * 55L)
        Assertions.assertEquals(Int128.ZERO, Int128.MAX_VALUE * 0L)
        Assertions.assertEquals(Int128.valueOf(55L), Int128.ONE * 55L)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE * 1L)
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.MINUS_ONE * 1L)
    }

    @Test
    fun timesInt() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO * 55)
        Assertions.assertEquals(Int128.ZERO, Int128.MAX_VALUE * 0)
        Assertions.assertEquals(Int128.valueOf(55), Int128.ONE * 55)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE * 1)
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.MINUS_ONE * 1)
    }

    @Test
    fun divInt128() {
        Assertions.assertThrows(ArithmeticException::class.java) { Int128.MAX_VALUE / Int128.ZERO }
        Assertions.assertEquals(Int128.ZERO, Int128.ONE / Int128.valueOf(2))
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE / Int128.ONE)
        Assertions.assertEquals(Int128.ONE, Int128.MAX_VALUE / Int128.MAX_VALUE)
        Assertions.assertEquals(Int128.valueOf(2), Int128.valueOf(4) / Int128.valueOf(2))

        Assertions.assertEquals(Int128.ONE, Int128.MINUS_ONE / Int128.MINUS_ONE)
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.ONE / Int128.MINUS_ONE)
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.MINUS_ONE / Int128.ONE)

        Assertions.assertEquals(Int128.valueOf(2), Int128.valueOf(-4) / Int128.valueOf(-2))
        Assertions.assertEquals(Int128.valueOf(-2), Int128.valueOf(-4) / Int128.valueOf(2))
        Assertions.assertEquals(Int128.valueOf(-2), Int128.valueOf(4) / Int128.valueOf(-2))

        Assertions.assertEquals(Int128.ONE, Int128.valueOf(-6) / Int128.valueOf(-5)) // Round down
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.valueOf(-6) / Int128.valueOf(5)) // Rounding to ZERO
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.valueOf(6) / Int128.valueOf(-5)) // Rounding to ZERO

        Assertions.assertEquals(Int128.valueOf(5), (Int128.valueOf(500) / Int128.valueOf(10)) / Int128.valueOf(10))
    }

    @Test
    fun remInt128() {
        Assertions.assertThrows(ArithmeticException::class.java) { Int128.MAX_VALUE % Int128.ZERO }
        Assertions.assertEquals(Int128.ONE, Int128.ONE % Int128.valueOf(2))
        Assertions.assertEquals(Int128.ONE, Int128.valueOf(3) % Int128.valueOf(2))
        Assertions.assertEquals(Int128.ZERO, Int128.MAX_VALUE % Int128.ONE)
        Assertions.assertEquals(Int128.ZERO, Int128.MAX_VALUE % Int128.MAX_VALUE)
        Assertions.assertEquals(Int128.ZERO, Int128.valueOf(4) % Int128.valueOf(2))
        Assertions.assertEquals(Int128.valueOf(4), Int128.valueOf(4) % Int128.valueOf(6))
        Assertions.assertEquals(Int128.valueOf(2), Int128.valueOf(6) % Int128.valueOf(4))

        Assertions.assertEquals(Int128.ZERO, Int128.MINUS_ONE % Int128.MINUS_ONE)
        Assertions.assertEquals(Int128.ZERO, Int128.ONE % Int128.MINUS_ONE)
        Assertions.assertEquals(Int128.ZERO, Int128.MINUS_ONE % Int128.ONE)

        Assertions.assertEquals(Int128.ZERO, Int128.valueOf(-4) % Int128.valueOf(-2))
        Assertions.assertEquals(Int128.ZERO, Int128.valueOf(-4) % Int128.valueOf(2))
        Assertions.assertEquals(Int128.ZERO, Int128.valueOf(4) % Int128.valueOf(-2))

        Assertions.assertEquals(Int128.MINUS_ONE, Int128.valueOf(-6) % Int128.valueOf(-5)) // Round down
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.valueOf(-6) % Int128.valueOf(5)) // Rounding to ZERO
        Assertions.assertEquals(Int128.ONE, Int128.valueOf(6) % Int128.valueOf(-5))

        Assertions.assertEquals(Int128.valueOf(8), Int128.valueOf(128) % Int128.valueOf(10))

        // 18446744073709551616 % 10 == 6
        Assertions.assertEquals(
            Int128.valueOf(1844674407370955161uL) to Int128.valueOf(6),
            Int128.valueOf(1uL, 0uL).divMod(Int128.valueOf(10))
        )
        Assertions.assertEquals(
            Int128.valueOf(1844674407370955161uL) to Int128.valueOf(6uL),
            Int128.valueOf(1uL, 0uL).divMod(Int128.valueOf(10uL))
        )
        Assertions.assertEquals(Int128.valueOf(6), Int128.valueOf(1uL, 0uL) % Int128.valueOf(10))
    }

    @Test
    fun divMod() {
        Assertions.assertThrows(ArithmeticException::class.java) { Int128.MAX_VALUE.divMod(Int128.ZERO) }

        Assertions.assertEquals(Int128.ONE to Int128.ZERO, Int128.ONE.divMod(Int128.valueOf(1)))

        Assertions.assertEquals(Int128.MAX_VALUE to Int128.ZERO, Int128.MAX_VALUE.divMod(Int128.ONE))
        Assertions.assertEquals(Int128.ONE to Int128.ZERO, Int128.MAX_VALUE.divMod(Int128.MAX_VALUE))
        Assertions.assertEquals(Int128.valueOf(2) to Int128.ZERO, Int128.valueOf(4).divMod(Int128.valueOf(2)))
        Assertions.assertEquals(Int128.ONE to Int128.ONE, Int128.valueOf(4).divMod(Int128.valueOf(3)))
    }

    @Test
    fun increment() {
        Assertions.assertEquals(Int128.ONE, Int128.ZERO.increment())
        Assertions.assertEquals(Int128.valueOf(2L), Int128.ONE.increment())
        Assertions.assertEquals(Int128.valueOf(1uL, 1uL), Int128.valueOf(1uL, 0uL).increment())
        Assertions.assertEquals(Int128.MIN_VALUE, Int128.MAX_VALUE.increment())
    }

    @Test
    fun decrement() {
        Assertions.assertEquals(Int128.ZERO, Int128.ONE.decrement())
        Assertions.assertEquals(Int128.ONE, Int128.valueOf(2L).decrement())
        Assertions.assertEquals(Int128.valueOf(1uL, 0uL), Int128.valueOf(1uL, 1uL).decrement())
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MIN_VALUE.decrement())
    }

    @Test
    fun toByte() {
        Assertions.assertEquals(0.toByte(), Int128.ZERO.toByte())
        Assertions.assertEquals(1.toByte(), Int128.ONE.toByte())
        Assertions.assertEquals(15.toByte(), Int128.valueOf(15L).toByte())

        Assertions.assertEquals(15.toByte(), Int128.valueOf(1uL, 15uL).toByte())
        Assertions.assertEquals((-15).toByte(), Int128.valueOf(1uL, (-15L).toULong()).toByte())
        Assertions.assertEquals((-1).toByte(), Int128.MAX_VALUE.toByte())
        Assertions.assertEquals(0.toByte(), Int128.MIN_VALUE.toByte())
    }

    @Test
    fun toDouble() {
        Assertions.assertEquals(0L.toDouble(), Int128.ZERO.toDouble())
        Assertions.assertEquals(1L.toDouble(), Int128.ONE.toDouble())
        Assertions.assertEquals(15L.toDouble(), Int128.valueOf(15L).toDouble())

        Assertions.assertEquals(15L.toDouble(), Int128.valueOf(1uL, 15uL).toDouble())
        Assertions.assertEquals((-15).toDouble(), Int128.valueOf(1uL, (-15L).toULong()).toDouble())
        Assertions.assertEquals(-1.0, Int128.MAX_VALUE.toDouble())
        Assertions.assertEquals(0.0, Int128.MIN_VALUE.toDouble())
    }

    @Test
    fun toFloat() {
        Assertions.assertEquals(0L.toFloat(), Int128.ZERO.toFloat())
        Assertions.assertEquals(1L.toFloat(), Int128.ONE.toFloat())
        Assertions.assertEquals(15L.toFloat(), Int128.valueOf(15L).toFloat())

        Assertions.assertEquals(15L.toFloat(), Int128.valueOf(1uL, 15uL).toFloat())
        Assertions.assertEquals((-15).toFloat(), Int128.valueOf(1uL, (-15L).toULong()).toFloat())
        Assertions.assertEquals((-1.0).toFloat(), Int128.MAX_VALUE.toFloat())
        Assertions.assertEquals(0.0.toFloat(), Int128.MIN_VALUE.toFloat())
    }

    @Test
    fun toInt() {
        Assertions.assertEquals(0, Int128.ZERO.toInt())
        Assertions.assertEquals(1, Int128.ONE.toInt())
        Assertions.assertEquals(15, Int128.valueOf(15L).toInt())

        Assertions.assertEquals(15, Int128.valueOf(1uL, 15uL).toInt())
        Assertions.assertEquals(-15, Int128.valueOf(1uL, (-15L).toULong()).toInt())
        Assertions.assertEquals(Long.MAX_VALUE.toInt(), Int128.MAX_VALUE.toInt())
        Assertions.assertEquals(0, Int128.MIN_VALUE.toInt())
    }

    @Test
    fun toLong() {
        Assertions.assertEquals(0L, Int128.ZERO.toLong())
        Assertions.assertEquals(1L, Int128.ONE.toLong())
        Assertions.assertEquals(15L, Int128.valueOf(15L).toLong())

        Assertions.assertEquals(15L, Int128.valueOf(1uL, 15uL).toLong())
        Assertions.assertEquals(-15, Int128.valueOf(1uL, (-15L).toULong()).toLong())
        Assertions.assertEquals(-1L, Int128.MAX_VALUE.toLong())
        Assertions.assertEquals(0L, Int128.MIN_VALUE.toLong())
    }

    @Test
    fun toShort() {
        Assertions.assertEquals(0.toShort(), Int128.ZERO.toShort())
        Assertions.assertEquals(1.toShort(), Int128.ONE.toShort())
        Assertions.assertEquals(15.toShort(), Int128.valueOf(15L).toShort())

        Assertions.assertEquals(15.toShort(), Int128.valueOf(1uL, 15uL).toShort())
        Assertions.assertEquals((-15).toShort(), Int128.valueOf(1uL, (-15L).toULong()).toShort())
        Assertions.assertEquals((-1).toShort(), Int128.MAX_VALUE.toShort())
        Assertions.assertEquals(0.toShort(), Int128.MIN_VALUE.toShort())
    }

    @Test
    fun unaryMinus() {
        Assertions.assertEquals(Int128.ZERO, -Int128.ZERO)
        Assertions.assertEquals(Int128.MINUS_ONE, -Int128.ONE)
        Assertions.assertEquals(Int128.ONE, -Int128.MINUS_ONE)
        Assertions.assertEquals(Int128(Long.MIN_VALUE.toULong(), 1uL), -Int128.MAX_VALUE)
        Assertions.assertEquals(Int128.MIN_VALUE, -Int128.MIN_VALUE)
    }

    @Test
    fun inv() {
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.ZERO.inv())
        Assertions.assertEquals(Int128(ULong.MAX_VALUE, ULong.MAX_VALUE - 1u), Int128.ONE.inv())
        Assertions.assertEquals(Int128.ZERO, Int128.MINUS_ONE.inv())
        Assertions.assertEquals(Int128.MIN_VALUE, -Int128.MAX_VALUE.inv())
    }

    @Test
    fun component1() {
        run {
            val (h, _) = Int128.valueOf(15L)
            Assertions.assertEquals(0uL, h)
        }
        run {
            val (h, _) = Int128.valueOf(15uL, 0uL)
            Assertions.assertEquals(15uL, h)
        }
        run {
            val (h, _) = Int128.valueOf(15uL, 45uL)
            Assertions.assertEquals(15uL, h)
        }
    }

    @Test
    fun component2() {
        run {
            val (_, l) = Int128.valueOf(15L)
            Assertions.assertEquals(15uL, l)
        }
        run {
            val (_, l) = Int128.valueOf(15uL, 0uL)
            Assertions.assertEquals(0uL, l)
        }
        run {
            val (_, l) = Int128.valueOf(15uL, 45uL)
            Assertions.assertEquals(45uL, l)
        }
    }

    @Test fun testRangeToAscending() {
        run {
            val r = (Int128.ZERO..Int128.MAX_VALUE.decrement())
            Assertions.assertEquals(r.start , Int128.ZERO)
            Assertions.assertEquals(r.endInclusive , Int128.MAX_VALUE.decrement())
            Assertions.assertEquals(r.endExclusive , Int128.MAX_VALUE)
            Assertions.assertTrue(Int128.ZERO in r)
            Assertions.assertTrue(Int128.ONE in r)
            Assertions.assertTrue(Int128.MAX_VALUE.decrement() in r)
            Assertions.assertFalse(Int128.MAX_VALUE in r)
            Assertions.assertFalse(Int128.MIN_VALUE in r)
            Assertions.assertFalse(Int128.MINUS_ONE in r)
        }
        run {
            val r = (Int128.MINUS_ONE..Int128.MAX_VALUE.decrement())
            Assertions.assertEquals(r.start , Int128.MINUS_ONE)
            Assertions.assertEquals(r.endInclusive , Int128.MAX_VALUE.decrement())
            Assertions.assertEquals(r.endExclusive , Int128.MAX_VALUE)
            Assertions.assertTrue(Int128.ZERO in r)
            Assertions.assertTrue(Int128.ONE in r)
            Assertions.assertTrue(Int128.MAX_VALUE.decrement() in r)
            Assertions.assertFalse(Int128.MAX_VALUE in r)
            Assertions.assertFalse(Int128.MIN_VALUE in r)
            Assertions.assertTrue(Int128.MINUS_ONE in r)
        }
        run {
            val r = (Int128.MIN_VALUE..Int128.MAX_VALUE.decrement())
            Assertions.assertEquals(r.start , Int128.MIN_VALUE)
            Assertions.assertEquals(r.endInclusive , Int128.MAX_VALUE.decrement())
            Assertions.assertEquals(r.endExclusive , Int128.MAX_VALUE)
            Assertions.assertTrue(Int128.ZERO in r)
            Assertions.assertTrue(Int128.ONE in r)
            Assertions.assertTrue(Int128.MAX_VALUE.decrement() in r)
            Assertions.assertFalse(Int128.MAX_VALUE in r)
            Assertions.assertTrue(Int128.MIN_VALUE in r)
            Assertions.assertTrue(Int128.MINUS_ONE in r)
        }
        run {
            val r = (Int128.ZERO..Int128.valueOf(5))
            Assertions.assertEquals(r.start , Int128.ZERO)
            Assertions.assertEquals(r.endInclusive , Int128.valueOf(5))
            Assertions.assertEquals(r.endExclusive , Int128.valueOf(6))
Assertions.assertEquals(r.size, 6)
            Assertions.assertEquals(listOf(0,1,2,3,4,5).map{Int128.valueOf(it)}, r.toList())
        }
    }

    @Test fun testEqualsInt128() {
        Assertions.assertTrue(Int128.ZERO == Int128.ZERO)
        Assertions.assertTrue(Int128.ONE == Int128.ONE)
        Assertions.assertTrue(Int128.MINUS_ONE == Int128.MINUS_ONE)
        Assertions.assertTrue(Int128.MAX_VALUE == Int128.MAX_VALUE)
        Assertions.assertTrue(Int128.MIN_VALUE == Int128.MIN_VALUE)

        Assertions.assertTrue(Int128(0uL, 0uL) == Int128(0uL, 0uL))
        Assertions.assertTrue(Int128(0uL, 1uL) == Int128(0uL, 1uL))
        Assertions.assertTrue(Int128(ULong.MAX_VALUE, ULong.MAX_VALUE) ==  Int128(ULong.MAX_VALUE, ULong.MAX_VALUE))
        Assertions.assertTrue(Int128(Long.MAX_VALUE.toULong(), ULong.MAX_VALUE) == Int128(Long.MAX_VALUE.toULong(), ULong.MAX_VALUE))
        Assertions.assertTrue(Int128(Long.MIN_VALUE.toULong(), 0uL) == Int128(Long.MIN_VALUE.toULong(), 0uL))
        Assertions.assertTrue(Int128(1uL, 0uL) == Int128(1uL, 0uL))
        Assertions.assertTrue(Int128(1uL, 1uL) == Int128(1uL, 1uL))

        Assertions.assertTrue(Int128(0uL, 1uL) != Int128(0uL, 0uL))
        Assertions.assertTrue(Int128(1uL, 0uL) != Int128(0uL, 0uL))
        Assertions.assertTrue(Int128(1uL, 1uL) != Int128(0uL, 0uL))
    }
//    @Test fun testEqualsUInt128() {
//        Assertions.assertTrue(Int128.ZERO == UInt128.ZERO as Any)
//        Assertions.assertTrue(Int128.ONE == UInt128.ONE)
//        Assertions.assertTrue(Int128.MAX_VALUE == UInt128.MAX_VALUE)
//
//        Int128(0uL, 0uL) == (UInt128(0uL, 0uL) as Any?)
//
//        Assertions.assertTrue(Int128(0uL, 0uL) == UInt128(0uL, 0uL))
//        Assertions.assertTrue(Int128(0uL, 1uL) == UInt128(0uL, 1uL))
//        Assertions.assertTrue(Int128(ULong.MAX_VALUE, ULong.MAX_VALUE) ==  Int128(ULong.MAX_VALUE, ULong.MAX_VALUE))
//        Assertions.assertTrue(Int128(Long.MAX_VALUE.toULong(), ULong.MAX_VALUE) == Int128(Long.MAX_VALUE.toULong(), ULong.MAX_VALUE))
//        Assertions.assertTrue(Int128(Long.MIN_VALUE.toULong(), 0uL) == Int128(Long.MIN_VALUE.toULong(), 0uL))
//        Assertions.assertTrue(Int128(1uL, 0uL) == Int128(1uL, 0uL))
//        Assertions.assertTrue(Int128(1uL, 1uL) == Int128(1uL, 1uL))
//
//        Assertions.assertTrue(Int128(0uL, 1uL) != Int128(0uL, 0uL))
//        Assertions.assertTrue(Int128(1uL, 0uL) != Int128(0uL, 0uL))
//        Assertions.assertTrue(Int128(1uL, 1uL) != Int128(0uL, 0uL))
//
//    }
//    @Test fun testEqualsByte() {
//        Assertions.assertTrue(Int128.ZERO == (0.toByte() as Number))
//        Assertions.assertTrue(Int128.ONE == (1.toByte() as Number))
//        Assertions.assertTrue( Int128.MINUS_ONE==((-1).toByte() as Any))
//        Assertions.assertTrue( Int128.valueOf(Byte.MAX_VALUE.toLong())==(Byte.MAX_VALUE as Any))
//        Assertions.assertTrue( Int128.valueOf(Byte.MIN_VALUE.toLong())==(Byte.MIN_VALUE as Any))
//
//        Assertions.assertTrue( Int128.valueOf(1L)!=((-1).toByte() as Any))
//        Assertions.assertTrue( Int128.valueOf(-1L)!=(1.toByte() as Any))
//    }
//    @Test fun testEqualsShort() {
//        Assertions.assertTrue( Int128.ZERO==(0.toShort() as Any))
//        Assertions.assertTrue( Int128.ONE==(1.toShort() as Any))
//        Assertions.assertTrue( Int128.MINUS_ONE==((-1).toShort() as Any))
//        Assertions.assertTrue( Int128.valueOf(Short.MAX_VALUE.toLong())==(Short.MAX_VALUE as Any))
//        Assertions.assertTrue( Int128.valueOf(Short.MIN_VALUE.toLong())==(Short.MIN_VALUE as Any))
//
//        Assertions.assertTrue( Int128.valueOf(1L)!=((-1).toShort() as Any))
//        Assertions.assertTrue( Int128.valueOf(-1L)!=(1.toShort() as Any))
//    }
//    @Test fun testEqualsInt() {
//        Assertions.assertTrue( Int128.ZERO==(0 as Any))
//        Assertions.assertTrue( Int128.ONE==(1 as Any))
//        Assertions.assertTrue( Int128.MINUS_ONE==(-1 as Any))
//        Assertions.assertTrue( Int128.valueOf(Int.MAX_VALUE.toLong())==(Int.MAX_VALUE as Any))
//        Assertions.assertTrue( Int128.valueOf(Int.MIN_VALUE.toLong())==(Int.MIN_VALUE as Any))
//
//        Assertions.assertTrue( Int128.valueOf(1)!=(-1 as Any))
//        Assertions.assertTrue( Int128.valueOf(-1)!=(1 as Any))
//    }
//    @Test fun testEqualsLong() {
//        Assertions.assertTrue( Int128.ZERO==(0L as Any))
//        Assertions.assertTrue( Int128.ONE==(1L as Any))
//        Assertions.assertTrue( Int128.MINUS_ONE==(-1L as Any))
//        Assertions.assertTrue( Int128.valueOf(Long.MAX_VALUE)==(Long.MAX_VALUE as Any))
//        Assertions.assertTrue( Int128.valueOf(Long.MIN_VALUE)==(Long.MIN_VALUE as Any))
//
//        Assertions.assertTrue( Int128.valueOf(1L)!=(-1L as Any))
//        Assertions.assertTrue( Int128.valueOf(-1L)!=(1L as Any))
//    }
//    @Test fun testEqualsUByte() {
//        Assertions.assertTrue( Int128.ZERO==(0.toUByte() as Any))
//        Assertions.assertTrue( Int128.ONE==(1.toUByte() as Any))
//        Assertions.assertTrue( Int128.valueOf(UByte.MAX_VALUE.toLong())==(UByte.MAX_VALUE as Any))
//        Assertions.assertTrue( Int128.valueOf((-1).toUByte().toULong())!=((-1L).toUByte().toULong() as Any))
//    }
//    @Test fun testEqualsUShort() {
//        Assertions.assertTrue( Int128.ZERO==(0.toUShort() as Any))
//        Assertions.assertTrue( Int128.ONE==(1.toUShort() as Any))
//        Assertions.assertTrue( Int128.valueOf(UShort.MAX_VALUE.toLong())==(UShort.MAX_VALUE as Any))
//        Assertions.assertTrue( Int128.valueOf((-1).toUShort().toULong())!=((-1L).toUShort().toULong() as Any))
//    }
//    @Test fun testEqualsUInt() {
//        Assertions.assertTrue( Int128.ZERO==(0u as Any))
//        Assertions.assertTrue( Int128.ONE==(1u as Any))
//        Assertions.assertTrue( Int128.valueOf(UInt.MAX_VALUE.toLong())==(UInt.MAX_VALUE as Any))
//        Assertions.assertTrue( Int128.valueOf((-1).toUInt())!=((-1L).toUInt() as Any))
//    }
//    @Test fun testEqualsULong() {
//        Assertions.assertTrue( Int128.ZERO==(0uL as Any))
//        Assertions.assertTrue( Int128.ONE==(1uL as Any))
//        Assertions.assertTrue( Int128.valueOf(ULong.MAX_VALUE)==(ULong.MAX_VALUE as Any))
//        Assertions.assertTrue( Int128.valueOf((-1).toULong())!=((-1L).toULong() as Any))
//    }
//    @Test fun testEqualsFloat() {
//        Assertions.assertTrue( Int128.ZERO==(0.0f as Any))
//        Assertions.assertTrue( Int128.ONE==(1.0f as Any))
//        Assertions.assertTrue( Int128.MINUS_ONE==(-1.0f as Any))
//
//        Assertions.assertTrue( Int128.ONE!=(-1.0f as Any))
//        Assertions.assertTrue( Int128.MINUS_ONE!=(1.0f as Any))
//
//        Assertions.assertTrue( Int128.valueOf(3)!=(((0.2f+0.1f)*10) as Any))
//    }
//    @Test fun testEqualsDouble() {
//        Assertions.assertTrue( Int128.ZERO==(0.0 as Any))
//        Assertions.assertTrue( Int128.ONE==(1.0 as Any))
//        Assertions.assertTrue( Int128.MINUS_ONE==(-1.0 as Any))
//
//        Assertions.assertTrue( Int128.ONE!=(-1.0 as Any))
//        Assertions.assertTrue( Int128.MINUS_ONE!=(1.0 as Any))
//
//        Assertions.assertTrue( Int128.valueOf(3)!=(((0.2+0.1)*10) as Any))
//    }
}