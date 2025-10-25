package de.kleinert.numerics

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UInt128Test {
    @Test
    fun sizeBytes(){
        Assertions.assertEquals(16, UInt128.SIZE_BYTES)
    }
    @Test
    fun sizeBits(){
        Assertions.assertEquals(128, UInt128.SIZE_BITS)
    }

    @Test
    fun testToString() {
        var v = UInt128.ZERO
        Assertions.assertEquals("0", v.toString())
        Assertions.assertEquals("0", v.toString(2))
        Assertions.assertEquals("0", v.toString(8))
        Assertions.assertEquals("0", v.toString(10))
        Assertions.assertEquals("0", v.toString(16))

        v = UInt128.ONE
        Assertions.assertEquals("1", v.toString())
        Assertions.assertEquals("1", v.toString(2))
        Assertions.assertEquals("1", v.toString(8))
        Assertions.assertEquals("1", v.toString(10))
        Assertions.assertEquals("1", v.toString(16))

        v = UInt128.MAX_VALUE
        Assertions.assertEquals("340282366920938463463374607431768211455", v.toString())
        Assertions.assertEquals(
            "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111",
            v.toString(2)
        )
        Assertions.assertEquals("3777777777777777777777777777777777777777777", v.toString(8))
        Assertions.assertEquals("340282366920938463463374607431768211455", v.toString(10))
        Assertions.assertEquals("ffffffffffffffffffffffffffffffff", v.toString(16))

        v = UInt128(0x100uL, 0x000000000000000uL)
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
        Assertions.assertEquals(UInt128.ZERO, UInt128.valueOf(0uL))
        Assertions.assertEquals(UInt128.ONE, UInt128.valueOf(1uL))
        Assertions.assertEquals(UInt128(0uL, ULong.MAX_VALUE), UInt128.valueOf(ULong.MAX_VALUE))
    }

    @Test
    fun valueOfUInt() {
        Assertions.assertEquals(UInt128.ZERO, UInt128.valueOf(0u))
        Assertions.assertEquals(UInt128.ONE, UInt128.valueOf(1u))
        Assertions.assertEquals(UInt128(0uL, UInt.MAX_VALUE.toULong()), UInt128.valueOf(UInt.MAX_VALUE))
    }

    @Test
    fun valueOfInt() {
        //Assertions.assertThrows(IllegalArgumentException::class.java) { UInt128.valueOf(-1) }
        //Assertions.assertThrows(IllegalArgumentException::class.java) { UInt128.valueOf(Int.MIN_VALUE) }
        Assertions.assertEquals(UInt128.ZERO, UInt128.valueOf(0))
        Assertions.assertEquals(UInt128.ONE, UInt128.valueOf(1))
        Assertions.assertEquals(UInt128(0uL, Int.MAX_VALUE.toULong()), UInt128.valueOf(Int.MAX_VALUE))
    }

    @Test
    fun valueOfLong() {
        //Assertions.assertThrows(IllegalArgumentException::class.java) { UInt128.valueOf(-1L) }
        //Assertions.assertThrows(IllegalArgumentException::class.java) { UInt128.valueOf(Long.MIN_VALUE) }
        Assertions.assertEquals(UInt128.ZERO, UInt128.valueOf(0uL))
        Assertions.assertEquals(UInt128.ONE, UInt128.valueOf(1uL))
        Assertions.assertEquals(UInt128(0uL, ULong.MAX_VALUE), UInt128.valueOf(ULong.MAX_VALUE))
    }

    @Test
    fun valueOfString() {
        Assertions.assertThrows(IllegalArgumentException::class.java) { UInt128.valueOf("") }
        Assertions.assertThrows(NumberFormatException::class.java) { UInt128.valueOf("-") }
        Assertions.assertEquals(UInt128.ZERO, UInt128.valueOf("0"))
        Assertions.assertEquals(UInt128.ONE, UInt128.valueOf("1"))
        //Assertions.assertEquals(UInt128(0xFFFFFFFFFFFFFF00uL, 0x000000000000000uL), UInt128.valueOf("-4722366482869645213696"))
        Assertions.assertEquals(UInt128(0x100uL, 0x000000000000000uL), UInt128.valueOf("4722366482869645213696"))
        Assertions.assertEquals(UInt128(0x100uL, 0x000000000000000uL), UInt128.valueOf("1000000000000000000", 16))
    }

    @Test
    fun valueOfStringOrNull() {
        Assertions.assertNull(UInt128.valueOfOrNull(""))
        Assertions.assertNull(UInt128.valueOfOrNull("-"))
        Assertions.assertEquals(UInt128.ZERO, UInt128.valueOfOrNull("0"))
        Assertions.assertEquals(UInt128.ONE, UInt128.valueOfOrNull("1"))
        Assertions.assertEquals(UInt128(0x100uL, 0x000000000000000uL), UInt128.valueOfOrNull("4722366482869645213696"))
        Assertions.assertEquals(UInt128(0x100uL, 0x000000000000000uL), UInt128.valueOfOrNull("1000000000000000000", 16))
    }

    @Test
    fun compareTo() {
        Assertions.assertTrue(UInt128.ONE > UInt128.ZERO)
        Assertions.assertTrue(UInt128.MAX_VALUE > UInt128.ZERO)
        Assertions.assertTrue(UInt128.MAX_VALUE > UInt128.MIN_VALUE)

        Assertions.assertTrue(UInt128.ZERO < UInt128.ONE)
        Assertions.assertTrue(UInt128.ZERO < UInt128.MAX_VALUE)
    }

    @Test
    fun equals() {
        Assertions.assertTrue(UInt128.ZERO == UInt128.valueOf(0uL, 0uL))
        Assertions.assertTrue(UInt128.ONE == UInt128.valueOf(0uL, 1uL))
        Assertions.assertTrue(UInt128.ONE != UInt128.valueOf(0uL, 0uL))
        Assertions.assertTrue(UInt128.MAX_VALUE == UInt128(ULong.MAX_VALUE, ULong.MAX_VALUE))
        Assertions.assertTrue(UInt128.MIN_VALUE == UInt128(ULong.MIN_VALUE, 0uL))
    }

    @Test
    fun isZero() {
        Assertions.assertTrue(UInt128.ZERO.isZero())
        Assertions.assertFalse(UInt128.ONE.isZero())
        Assertions.assertFalse(UInt128.MAX_VALUE.isZero())
        Assertions.assertTrue(UInt128.MIN_VALUE.isZero())
    }

    @Test
    fun and() {
        Assertions.assertEquals(UInt128.ZERO, UInt128.ZERO and UInt128.ONE)
        Assertions.assertEquals(UInt128.ZERO, UInt128.ZERO and UInt128.ZERO)
        Assertions.assertEquals(UInt128.ZERO, UInt128.MAX_VALUE and UInt128.ZERO)
        Assertions.assertEquals(UInt128.ONE, UInt128.ONE and UInt128.ONE)
        Assertions.assertEquals(UInt128.ONE, UInt128.valueOf(3uL) and UInt128.ONE)
        Assertions.assertEquals(UInt128.MAX_VALUE, UInt128.MAX_VALUE and UInt128.MAX_VALUE)
    }

    @Test
    fun or() {
        Assertions.assertEquals(UInt128.ONE, UInt128.ZERO or UInt128.ONE)
        Assertions.assertEquals(UInt128.ZERO, UInt128.ZERO or UInt128.ZERO)
        Assertions.assertEquals(UInt128.MAX_VALUE, UInt128.MAX_VALUE or UInt128.ZERO)
        Assertions.assertEquals(UInt128.ONE, UInt128.ONE or UInt128.ONE)
        Assertions.assertEquals(UInt128.valueOf(3uL), UInt128.valueOf(3uL) or UInt128.ONE)
        Assertions.assertEquals(UInt128.MAX_VALUE, UInt128.MAX_VALUE or UInt128.MAX_VALUE)
    }

    @Test
    fun xor() {
        Assertions.assertEquals(UInt128.ONE, UInt128.ZERO xor UInt128.ONE)
        Assertions.assertEquals(UInt128.ZERO, UInt128.ZERO xor UInt128.ZERO)
        Assertions.assertEquals(UInt128.MAX_VALUE, UInt128.MAX_VALUE xor UInt128.ZERO)
        Assertions.assertEquals(UInt128.ZERO, UInt128.ONE xor UInt128.ONE)
        Assertions.assertEquals(UInt128.valueOf(2uL), UInt128.valueOf(3uL) xor UInt128.ONE)
        Assertions.assertEquals(UInt128.ZERO, UInt128.MAX_VALUE xor UInt128.MAX_VALUE)
    }

    @Test
    fun shiftLeft() {
        Assertions.assertEquals(UInt128.ZERO, UInt128.ZERO shl 1)
        Assertions.assertEquals(UInt128.valueOf(0uL, 0uL), UInt128.ONE shl 256)
        Assertions.assertEquals(UInt128.ONE, UInt128.ONE shl 0)
        Assertions.assertEquals(UInt128.valueOf(2), UInt128.ONE shl 1)
        Assertions.assertEquals(UInt128.valueOf(1uL, 0uL), UInt128.ONE shl 64)
        Assertions.assertEquals(UInt128.valueOf(ULong.MAX_VALUE, 0uL), UInt128.MAX_VALUE shl 64)
        Assertions.assertEquals(UInt128.valueOf(ULong.MAX_VALUE shl 1, 0uL), UInt128.MAX_VALUE shl 65)
    }

    @Test
    fun shiftRight() {
        Assertions.assertEquals(UInt128.ZERO, UInt128.ZERO shr 1)
        Assertions.assertEquals(UInt128.valueOf(0uL, 0uL), UInt128.MAX_VALUE shr 256)
        Assertions.assertEquals(UInt128.ONE, UInt128.ONE shr 0)
        Assertions.assertEquals(UInt128.valueOf(2), UInt128.valueOf(4) shr 1)
        Assertions.assertEquals(UInt128.valueOf(2), UInt128.valueOf(5) shr 1)
        Assertions.assertEquals(UInt128.valueOf(3), UInt128.valueOf(6) shr 1)
        Assertions.assertEquals(UInt128.valueOf(3), UInt128.valueOf(7) shr 1)
        Assertions.assertEquals(UInt128.valueOf(0uL, ULong.MAX_VALUE), UInt128.MAX_VALUE shr 64)
        Assertions.assertEquals(UInt128.valueOf(0uL, ULong.MAX_VALUE shr 1), UInt128.MAX_VALUE shr 65)
        Assertions.assertEquals(UInt128.valueOf(0uL, 0uL), UInt128.MIN_VALUE shr 65)
    }

    @Test
    fun numberOfLeadingZeros() {
        Assertions.assertEquals(128, UInt128.ZERO.numberOfLeadingZeros())
        Assertions.assertEquals(127, UInt128.ONE.numberOfLeadingZeros())
        Assertions.assertEquals(0, UInt128.MAX_VALUE.numberOfLeadingZeros())
        Assertions.assertEquals(128, UInt128.MIN_VALUE.numberOfLeadingZeros())

        Assertions.assertEquals(126, UInt128.valueOf(2uL).numberOfLeadingZeros())
    }

    @Test
    fun numberOfTrailingZeros() {
        Assertions.assertEquals(128, UInt128.ZERO.numberOfTrailingZeros())
        Assertions.assertEquals(0, UInt128.ONE.numberOfTrailingZeros())
        Assertions.assertEquals(0, UInt128.MAX_VALUE.numberOfTrailingZeros())
        Assertions.assertEquals(128, UInt128.MIN_VALUE.numberOfTrailingZeros())

        Assertions.assertEquals(1, UInt128.valueOf(2uL).numberOfTrailingZeros())
    }

    @Test
    fun bitCount() {
        Assertions.assertEquals(0, UInt128.ZERO.countOneBits())
        Assertions.assertEquals(1, UInt128.ONE.countOneBits())
        Assertions.assertEquals(128, UInt128.MAX_VALUE.countOneBits())
        Assertions.assertEquals(0, UInt128.MIN_VALUE.countOneBits())

        Assertions.assertEquals(1, UInt128.valueOf(2uL).countOneBits())

        Assertions.assertEquals(2, UInt128.valueOf(3uL).countOneBits())
        Assertions.assertEquals(2, UInt128.valueOf(1uL, 1uL).countOneBits())
    }

    @Test
    fun plusUInt128() {
        Assertions.assertEquals(UInt128.ZERO, UInt128.ZERO + UInt128.ZERO)
        Assertions.assertEquals(UInt128.ONE, UInt128.ZERO + UInt128.ONE)
        Assertions.assertEquals(UInt128.ONE, UInt128.ONE + UInt128.ZERO)
        Assertions.assertEquals(UInt128(1uL, 0uL), UInt128(0uL, ULong.MAX_VALUE) + UInt128.ONE)
        Assertions.assertEquals(UInt128(1uL, 0uL), UInt128.ONE + UInt128(0uL, ULong.MAX_VALUE))
        Assertions.assertEquals(UInt128(0uL, 0uL), UInt128.MAX_VALUE + UInt128.ONE)
    }

    @Test
    fun testPlusLong() {
        Assertions.assertEquals(UInt128.ZERO, UInt128.ZERO + 0uL)
        Assertions.assertEquals(UInt128.ONE, UInt128.ZERO + 1uL)
        Assertions.assertEquals(UInt128.ONE, UInt128.ONE + 0uL)
        Assertions.assertEquals(UInt128(1uL, 0uL), UInt128(0uL, ULong.MAX_VALUE) + 1uL)
        Assertions.assertEquals(UInt128(0uL, 0uL), UInt128.MAX_VALUE + 1uL)
    }

    @Test
    fun testPlusInt() {
        Assertions.assertEquals(UInt128.ZERO, UInt128.ZERO + 0u)
        Assertions.assertEquals(UInt128.ONE, UInt128.ZERO + 1u)
        Assertions.assertEquals(UInt128.ONE, UInt128.ONE + 0u)
        Assertions.assertEquals(UInt128(1uL, 0uL), UInt128(0uL, ULong.MAX_VALUE) + 1u)
        Assertions.assertEquals(UInt128(0uL, 0uL), UInt128.MAX_VALUE + 1u)
    }

    @Test
    fun testPlusUInt() {
        Assertions.assertEquals(UInt128.ZERO, UInt128.ZERO + 0u)
        Assertions.assertEquals(UInt128.ONE, UInt128.ZERO + 1u)
        Assertions.assertEquals(UInt128.ONE, UInt128.ONE + 0u)
        Assertions.assertEquals(UInt128(1uL, 0uL), UInt128(0uL, ULong.MAX_VALUE) + 1uL)
        Assertions.assertEquals(UInt128(0uL, 0uL), UInt128.MAX_VALUE + 1u)
    }

    @Test
    fun testPlusULong() {
        Assertions.assertEquals(UInt128.ZERO, UInt128.ZERO + 0uL)
        Assertions.assertEquals(UInt128.ONE, UInt128.ZERO + 1uL)
        Assertions.assertEquals(UInt128.ONE, UInt128.ONE + 0uL)
        Assertions.assertEquals(UInt128(1uL, 0uL), UInt128(0uL, ULong.MAX_VALUE) + 1uL)
        Assertions.assertEquals(UInt128(1uL, 0uL), UInt128.ONE + ULong.MAX_VALUE)
        Assertions.assertEquals(UInt128(0uL, 0uL), UInt128.MAX_VALUE + 1uL)
    }

    @Test
    fun minusUInt128() {
        Assertions.assertEquals(UInt128.ZERO, UInt128.ONE - UInt128.ONE)
        Assertions.assertEquals(UInt128.ONE, UInt128.ONE - UInt128.ZERO)
        Assertions.assertEquals(UInt128(ULong.MAX_VALUE, ULong.MAX_VALUE - 1u), UInt128.MAX_VALUE - UInt128.ONE)
        Assertions.assertEquals(UInt128.MAX_VALUE, UInt128.ZERO - UInt128.ONE)
    }

    @Test
    fun minusLong() {
        Assertions.assertEquals(UInt128.ZERO, UInt128.ONE - 1uL)
        Assertions.assertEquals(UInt128.ONE, UInt128.ONE - 0uL)
        Assertions.assertEquals(UInt128(ULong.MAX_VALUE, ULong.MAX_VALUE - 1u), UInt128.MAX_VALUE - 1uL)
    }

    @Test
    fun minusInt() {
        Assertions.assertEquals(UInt128.ZERO, UInt128.ONE - 1u)
        Assertions.assertEquals(UInt128.ONE, UInt128.ONE - 0u)
        Assertions.assertEquals(UInt128(ULong.MAX_VALUE, ULong.MAX_VALUE - 1u), UInt128.MAX_VALUE - 1u)
    }

    @Test
    fun timesUInt128() {
        Assertions.assertEquals(UInt128.ZERO, UInt128.ZERO * UInt128.MAX_VALUE)
        Assertions.assertEquals(UInt128.ZERO, UInt128.MAX_VALUE * UInt128.ZERO)
        Assertions.assertEquals(UInt128.MAX_VALUE, UInt128.ONE * UInt128.MAX_VALUE)
        Assertions.assertEquals(UInt128.MAX_VALUE, UInt128.MAX_VALUE * UInt128.ONE)
    }

    @Test
    fun timesLong() {
        Assertions.assertEquals(UInt128.ZERO, UInt128.ZERO * 55uL)
        Assertions.assertEquals(UInt128.ZERO, UInt128.MAX_VALUE * 0uL)
        Assertions.assertEquals(UInt128.valueOf(55uL), UInt128.ONE * 55uL)
        Assertions.assertEquals(UInt128.MAX_VALUE, UInt128.MAX_VALUE * 1uL)
    }

    @Test
    fun timesInt() {
        Assertions.assertEquals(UInt128.ZERO, UInt128.ZERO * 55u)
        Assertions.assertEquals(UInt128.ZERO, UInt128.MAX_VALUE * 0u)
        Assertions.assertEquals(UInt128.valueOf(55), UInt128.ONE * 55u)
        Assertions.assertEquals(UInt128.MAX_VALUE, UInt128.MAX_VALUE * 1u)
    }

    @Test
    fun divUInt128() {
        Assertions.assertThrows(ArithmeticException::class.java) { UInt128.MAX_VALUE / UInt128.ZERO }
        Assertions.assertEquals(UInt128.ZERO, UInt128.ONE / UInt128.valueOf(2))
        Assertions.assertEquals(UInt128.MAX_VALUE, UInt128.MAX_VALUE / UInt128.ONE)
        Assertions.assertEquals(UInt128.ONE, UInt128.MAX_VALUE / UInt128.MAX_VALUE)
        Assertions.assertEquals(UInt128.valueOf(2), UInt128.valueOf(4) / UInt128.valueOf(2))

        Assertions.assertEquals(UInt128.valueOf(2), UInt128.valueOf(4) / UInt128.valueOf(2))

        Assertions.assertEquals(UInt128.ONE, UInt128.valueOf(6) / UInt128.valueOf(5)) // Round down

        Assertions.assertEquals(UInt128.valueOf(5), (UInt128.valueOf(500) / UInt128.valueOf(10)) / UInt128.valueOf(10))
    }

    @Test
    fun remUInt128() {
        Assertions.assertThrows(ArithmeticException::class.java) { UInt128.MAX_VALUE % UInt128.ZERO }
        Assertions.assertEquals(UInt128.ONE, UInt128.ONE % UInt128.valueOf(2))
        Assertions.assertEquals(UInt128.ONE, UInt128.valueOf(3) % UInt128.valueOf(2))
        Assertions.assertEquals(UInt128.ZERO, UInt128.MAX_VALUE % UInt128.ONE)
        Assertions.assertEquals(UInt128.ZERO, UInt128.MAX_VALUE % UInt128.MAX_VALUE)
        Assertions.assertEquals(UInt128.ZERO, UInt128.valueOf(4) % UInt128.valueOf(2))
        Assertions.assertEquals(UInt128.valueOf(4), UInt128.valueOf(4) % UInt128.valueOf(6))
        Assertions.assertEquals(UInt128.valueOf(2), UInt128.valueOf(6) % UInt128.valueOf(4))

        Assertions.assertEquals(UInt128.ZERO, UInt128.valueOf(4) % UInt128.valueOf(2))

        Assertions.assertEquals(UInt128.ONE, UInt128.valueOf(6) % UInt128.valueOf(5))

        Assertions.assertEquals(UInt128.valueOf(8), UInt128.valueOf(128) % UInt128.valueOf(10))
        Assertions.assertEquals(UInt128.valueOf(6), UInt128.valueOf(1uL, 0uL) % UInt128.valueOf(10))
    }

    @Test
    fun divMod() {
        Assertions.assertThrows(ArithmeticException::class.java) { UInt128.MAX_VALUE.divMod(UInt128.ZERO) }

        Assertions.assertEquals(UInt128.ONE to UInt128.ZERO, UInt128.ONE.divMod(UInt128.valueOf(1)))

        // 18446744073709551616 % 10 == 6
        Assertions.assertEquals(
            UInt128.valueOf(1844674407370955161uL) to UInt128.valueOf(6),
            UInt128.valueOf(1uL, 0uL).divMod(UInt128.valueOf(10))
        )
        Assertions.assertEquals(
            UInt128.valueOf(1844674407370955161uL) to 6uL,
            UInt128.valueOf(1uL, 0uL).divMod(10uL)
        )

        Assertions.assertEquals(UInt128.MAX_VALUE to UInt128.ZERO, UInt128.MAX_VALUE.divMod(UInt128.ONE))
        Assertions.assertEquals(UInt128.ONE to UInt128.ZERO, UInt128.MAX_VALUE.divMod(UInt128.MAX_VALUE))
        Assertions.assertEquals(UInt128.valueOf(2) to UInt128.ZERO, UInt128.valueOf(4).divMod(UInt128.valueOf(2)))
        Assertions.assertEquals(UInt128.ONE to UInt128.ONE, UInt128.valueOf(4).divMod(UInt128.valueOf(3)))
    }

    @Test
    fun divModUInt() {
        Assertions.assertThrows(ArithmeticException::class.java) { UInt128.MAX_VALUE.divMod(0u) }

        Assertions.assertEquals(UInt128.ONE to UInt128.ZERO, UInt128.ONE.divMod(1u))

        // 18446744073709551616 % 10 == 6
        Assertions.assertEquals(
            UInt128.valueOf(1844674407370955161uL) to UInt128.valueOf(6),
            UInt128.valueOf(1uL, 0uL).divMod(10u)
        )

        Assertions.assertEquals(UInt128.MAX_VALUE to UInt128.ZERO, UInt128.MAX_VALUE.divMod(1u))
        Assertions.assertEquals(UInt128.valueOf(2) to UInt128.ZERO, UInt128.valueOf(4).divMod(2u))
        Assertions.assertEquals(UInt128.ONE to UInt128.ONE, UInt128.valueOf(4).divMod(3u))
    }

    @Test
    fun divModULong() {
        Assertions.assertThrows(ArithmeticException::class.java) { UInt128.MAX_VALUE.divMod(0uL) }

        Assertions.assertEquals(UInt128.ONE to 0uL, UInt128.ONE.divMod(1uL))

        // 18446744073709551616 % 10 == 6
        Assertions.assertEquals(
            UInt128.valueOf(1844674407370955161uL) to 6uL,
            UInt128.valueOf(1uL, 0uL).divMod(10uL)
        )

        Assertions.assertEquals(UInt128.MAX_VALUE to 0uL, UInt128.MAX_VALUE.divMod(1uL))
        Assertions.assertEquals(UInt128.valueOf(2) to 0uL, UInt128.valueOf(4).divMod(2uL))
        Assertions.assertEquals(UInt128.ONE to 1uL, UInt128.valueOf(4).divMod(3uL))
    }

    @Test
    fun increment() {
        Assertions.assertEquals(UInt128.ONE, UInt128.ZERO.increment())
        Assertions.assertEquals(UInt128.valueOf(2uL), UInt128.ONE.increment())
        Assertions.assertEquals(UInt128.valueOf(1uL, 1uL), UInt128.valueOf(1uL, 0uL).increment())
        Assertions.assertEquals(UInt128.MIN_VALUE, UInt128.MAX_VALUE.increment())
    }

    @Test
    fun decrement() {
        Assertions.assertEquals(UInt128.ZERO, UInt128.ONE.decrement())
        Assertions.assertEquals(UInt128.ONE, UInt128.valueOf(2uL).decrement())
        Assertions.assertEquals(UInt128.valueOf(1uL, 0uL), UInt128.valueOf(1uL, 1uL).decrement())
        Assertions.assertEquals(UInt128.MAX_VALUE, UInt128.MIN_VALUE.decrement())
    }

    @Test
    fun toByte() {
        Assertions.assertEquals(0.toByte(), UInt128.ZERO.toByte())
        Assertions.assertEquals(1.toByte(), UInt128.ONE.toByte())
        Assertions.assertEquals(15.toByte(), UInt128.valueOf(15uL).toByte())

        Assertions.assertEquals(15.toByte(), UInt128.valueOf(1uL, 15uL).toByte())
        Assertions.assertEquals(0.toByte(), UInt128.MIN_VALUE.toByte())
    }

    @Test
    fun toDouble() {
        Assertions.assertEquals(0L.toDouble(), UInt128.ZERO.toDouble())
        Assertions.assertEquals(1L.toDouble(), UInt128.ONE.toDouble())
        Assertions.assertEquals(15uL.toDouble(), UInt128.valueOf(15uL).toDouble())

        Assertions.assertEquals(15L.toDouble(), UInt128.valueOf(1uL, 15uL).toDouble())
        Assertions.assertEquals(0.0, UInt128.MIN_VALUE.toDouble())
    }

    @Test
    fun toFloat() {
        Assertions.assertEquals(0L.toFloat(), UInt128.ZERO.toFloat())
        Assertions.assertEquals(1L.toFloat(), UInt128.ONE.toFloat())
        Assertions.assertEquals(15uL.toFloat(), UInt128.valueOf(15uL).toFloat())

        Assertions.assertEquals(15L.toFloat(), UInt128.valueOf(1uL, 15uL).toFloat())
        Assertions.assertEquals(0.0.toFloat(), UInt128.MIN_VALUE.toFloat())
    }

    @Test
    fun toInt() {
        Assertions.assertEquals(0, UInt128.ZERO.toInt())
        Assertions.assertEquals(1, UInt128.ONE.toInt())

        Assertions.assertEquals(15, UInt128.valueOf(1uL, 15uL).toInt())
        Assertions.assertEquals(Long.MAX_VALUE.toInt(), UInt128.MAX_VALUE.toInt())
        Assertions.assertEquals(0, UInt128.MIN_VALUE.toInt())
    }

    @Test
    fun toLong() {
        Assertions.assertEquals(0L, UInt128.ZERO.toLong())
        Assertions.assertEquals(1L, UInt128.ONE.toLong())

        Assertions.assertEquals(15L, UInt128.valueOf(1uL, 15uL).toLong())
        Assertions.assertEquals(-1L, UInt128.MAX_VALUE.toLong())
        Assertions.assertEquals(0L, UInt128.MIN_VALUE.toLong())
    }

    @Test
    fun toShort() {
        Assertions.assertEquals(0.toShort(), UInt128.ZERO.toShort())
        Assertions.assertEquals(1.toShort(), UInt128.ONE.toShort())
        Assertions.assertEquals(15.toShort(), UInt128.valueOf(15uL).toShort())

        Assertions.assertEquals(15.toShort(), UInt128.valueOf(1uL, 15uL).toShort())
        Assertions.assertEquals((-15).toShort(), UInt128.valueOf(1uL, (-15L).toULong()).toShort())
        Assertions.assertEquals((-1).toShort(), UInt128.MAX_VALUE.toShort())
        Assertions.assertEquals(0.toShort(), UInt128.MIN_VALUE.toShort())
    }

    @Test
    fun inv() {
        Assertions.assertEquals(UInt128(ULong.MAX_VALUE, ULong.MAX_VALUE - 1u), UInt128.ONE.inv())
        Assertions.assertEquals(UInt128.MIN_VALUE, UInt128.MAX_VALUE.inv())
    }

    @Test
    fun component1() {
        run {
            val (h, _) = UInt128.valueOf(15uL)
            Assertions.assertEquals(0uL, h)
        }
        run {
            val (h, _) = UInt128.valueOf(15uL, 0uL)
            Assertions.assertEquals(15uL, h)
        }
        run {
            val (h, _) = UInt128.valueOf(15uL, 45uL)
            Assertions.assertEquals(15uL, h)
        }
    }

    @Test
    fun component2() {
        run {
            val (_, l) = UInt128.valueOf(15uL)
            Assertions.assertEquals(15uL, l)
        }
        run {
            val (_, l) = UInt128.valueOf(15uL, 0uL)
            Assertions.assertEquals(0uL, l)
        }
        run {
            val (_, l) = UInt128.valueOf(15uL, 45uL)
            Assertions.assertEquals(45uL, l)
        }
    }

    @Test fun testEqualsInt128() {
        Assertions.assertTrue(UInt128.ZERO == UInt128.ZERO)
        Assertions.assertTrue(UInt128.ONE == UInt128.ONE)
        Assertions.assertTrue(UInt128.MAX_VALUE == UInt128.MAX_VALUE)
        Assertions.assertTrue(UInt128.MIN_VALUE == UInt128.MIN_VALUE)

        Assertions.assertTrue(UInt128(0uL, 0uL) == UInt128(0uL, 0uL))
        Assertions.assertTrue(UInt128(0uL, 1uL) == UInt128(0uL, 1uL))
        Assertions.assertTrue(UInt128(ULong.MAX_VALUE, ULong.MAX_VALUE) ==  UInt128(ULong.MAX_VALUE, ULong.MAX_VALUE))
        Assertions.assertTrue(UInt128(Long.MAX_VALUE.toULong(), ULong.MAX_VALUE) == UInt128(Long.MAX_VALUE.toULong(), ULong.MAX_VALUE))
        Assertions.assertTrue(UInt128(Long.MIN_VALUE.toULong(), 0uL) == UInt128(Long.MIN_VALUE.toULong(), 0uL))
        Assertions.assertTrue(UInt128(1uL, 0uL) == UInt128(1uL, 0uL))
        Assertions.assertTrue(UInt128(1uL, 1uL) == UInt128(1uL, 1uL))

        Assertions.assertTrue(UInt128(0uL, 1uL) != UInt128(0uL, 0uL))
        Assertions.assertTrue(UInt128(1uL, 0uL) != UInt128(0uL, 0uL))
        Assertions.assertTrue(UInt128(1uL, 1uL) != UInt128(0uL, 0uL))
    }
}