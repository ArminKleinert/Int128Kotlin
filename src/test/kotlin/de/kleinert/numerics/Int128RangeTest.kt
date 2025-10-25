package de.kleinert.numerics

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions

class Int128RangeTest {
    private val zero = Int128.ZERO
    private val five = Int128.valueOf(5uL)
    private val six = UInt128.valueOf(6uL)

    @Test
    fun testEquals() {
        Assertions.assertEquals(zero..five, zero..five)
        Assertions.assertEquals(five..zero, five..zero)
        Assertions.assertEquals(zero..five, Int128Range(zero, five, Int128.ONE))
        Assertions.assertEquals(five..zero, Int128Range(five, zero, Int128.MINUS_ONE))
    }

    @Test
    fun testBasicAscendingRange() {
        val r = zero..five
        Assertions.assertEquals(6, r.size)
        Assertions.assertEquals(six, r.size128)
        Assertions.assertTrue(zero in r)
        Assertions.assertTrue(five in r)
        Assertions.assertTrue(five - 1 in r)
        Assertions.assertFalse(five + 1 in r)
        Assertions.assertFalse(Int128.MINUS_ONE in r)
        Assertions.assertFalse(Int128.MAX_VALUE in r)
        Assertions.assertFalse(Int128.MIN_VALUE in r)
    }



























    @Test
    fun testForwardRangeIteration() {
        val range = Int128Range(Int128.ZERO, five)
        val list = range.toList()
        Assertions.assertEquals(listOf(0uL, 1uL, 2uL, 3uL, 4uL, 5uL).map { Int128.valueOf(it) }, list)
    }

    @Test
    fun testBackwardRangeIteration() {run {
        val range = Int128Range(five, Int128.ZERO, Int128.MINUS_ONE)
        val list = range.toList()
        Assertions.assertEquals(listOf(5uL, 4uL, 3uL, 2uL, 1uL, 0uL).map { Int128.valueOf(it) }, list)
    }
        run{
        val range = five downTo  Int128.ZERO
        val list = range.toList()
        Assertions.assertEquals(listOf(5uL, 4uL, 3uL, 2uL, 1uL, 0uL).map { Int128.valueOf(it) }, list)}
    }

    @Test
    fun testStepRange() {
        val range = Int128Range(Int128.ZERO, Int128.valueOf(10uL), Int128.valueOf(2))
        val list = range.toList()
        Assertions.assertEquals(listOf(0uL, 2uL, 4uL, 6uL, 8uL, 10uL).map { Int128.valueOf(it) }, list)
    }

    @Test
    fun testNegativeStepRange() {
        val range = Int128Range(Int128.valueOf(10uL), Int128.ZERO, Int128.valueOf(-2))
        val list = range.toList()
        Assertions.assertEquals(listOf(10uL, 8uL, 6uL, 4uL, 2uL, 0uL).map { Int128.valueOf(it) }, list)
    }

    @Test
    fun testContains() {
        val range = Int128Range(Int128.ZERO, Int128.valueOf(10))
        Assertions.assertTrue(five in range)
        Assertions.assertTrue(Int128.valueOf(0uL) in range)
        Assertions.assertTrue(Int128.valueOf(10uL) in range)
        Assertions.assertTrue(Int128.valueOf(11uL) !in range)
    }

    @Test
    fun testRangeAscending() {
        val range = five downTo Int128.ZERO
        Assertions.assertFalse(range.isEmpty())
        Assertions.assertEquals(6, range.size)
    }

    @Test
    fun testEmptyRangeAscending() {
        val range = Int128Range(five, Int128.valueOf(0), Int128.ONE)
        Assertions.assertTrue(range.isEmpty())
        Assertions.assertEquals(0, range.size)
    }

    @Test
    fun testEmptyRangeDescending() {
        val range = Int128Range(Int128.valueOf(0), five, Int128.valueOf(-1))
        Assertions.assertTrue(range.isEmpty())
        Assertions.assertEquals(0, range.size)
    }

    @Test
    fun testSizeSmallRange() {
        val range = Int128Range(Int128.ZERO, Int128.valueOf(4))
        Assertions.assertEquals(5, range.size)
    }

    @Test
    fun testSizeWithStep() {
        val range = Int128Range(Int128.ZERO, Int128.valueOf(9uL), Int128.valueOf(2uL))
        Assertions.assertEquals(5, range.size)
    }

    @Test
    fun testSizeThrowsWhenTooLarge() {
        // Simulate very large range (just using difference exceeding Int.MAX_VALUE)
        val largeStart = Int128.ZERO
        val largeEnd = Int128.valueOf(Int.MAX_VALUE.toLong()) + Int128.valueOf(10uL)
        val largeRange = Int128Range(largeStart, largeEnd)

        Assertions.assertThrows(ArithmeticException::class.java) { largeRange.size }
    }

    @Test
    fun testSize128AlwaysWorks() {
        val start = Int128.ZERO
        val end = Int128.valueOf(Long.MAX_VALUE)
        val range = start..<end

        // Should not throw
        val size128 = range.size128
        Assertions.assertEquals(UInt128.valueOf(Long.MAX_VALUE.toULong()+1uL), size128)
    }

    @Test
    fun testStepNegative() {
        val range = Int128Range(five, Int128.ZERO, Int128.valueOf(-2L))
        Assertions.assertEquals(3, range.size)
        Assertions.assertEquals(listOf(5uL, 3uL, 1uL).map { Int128.valueOf(it) }, range.toList())
    }

    @Test
    fun testZeroStepThrows() {
        Assertions.assertThrows(IllegalArgumentException::class.java) { Int128Range(Int128.ZERO, Int128.valueOf(10uL), Int128.ZERO) }
    }
}