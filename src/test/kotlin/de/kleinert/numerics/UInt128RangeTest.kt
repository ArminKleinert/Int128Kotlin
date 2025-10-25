//package de.kleinert.numerics
//
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.Test
//
//class UInt128RangeTest {
//    private val zero = UInt128.ZERO
//    private val five = UInt128.valueOf(5uL)
//
//    @Test
//    fun testClosedEquals() {
//        Assertions.assertEquals(zero..five, zero..five)
//        Assertions.assertEquals(five..zero, five..zero)
//        Assertions.assertEquals(zero..five, UInt128Range(zero, five, Int128.ONE))
//        Assertions.assertEquals(five..zero, UInt128Range(five, zero, Int128.ONE))
//    }
//
//    @Test
//    fun testOpenEquals() {
//        Assertions.assertEquals(zero..<five, zero..<five)
//        Assertions.assertEquals(five..<zero, five..<zero)
//        Assertions.assertEquals(zero..<five, UInt128Range(zero, five.decrement(), Int128.ONE))
//        Assertions.assertEquals(five..<zero, UInt128Range(five, zero.decrement(), Int128.ONE))
//    }
//
//    @Test
//    fun testClosedBasicAscendingRange() {
//        val r = zero..five
//        Assertions.assertEquals(6, r.size)
//        Assertions.assertEquals(UInt128.valueOf(6uL), r.size128)
//        Assertions.assertTrue(zero in r)
//        Assertions.assertTrue(five in r)
//        Assertions.assertTrue(five - 1u in r)
//        Assertions.assertFalse(five + 1u in r)
//        Assertions.assertFalse(UInt128.MAX_VALUE in r)
//        Assertions.assertFalse(UInt128.MIN_VALUE in r)
//        Assertions.assertFalse(r.isEmpty())
//    }
//
//    @Test
//    fun testOpenBasicAscendingRange() {
//        val r = zero..<five
//        println(r)
//        Assertions.assertEquals(5, r.size)
//        Assertions.assertEquals(UInt128.valueOf(5uL), r.size128)
//        Assertions.assertTrue(zero in r)
//        Assertions.assertTrue(five !in r)
//        Assertions.assertTrue(five - 1u in r)
//        Assertions.assertFalse(five + 1u in r)
//        Assertions.assertFalse(UInt128.MAX_VALUE in r)
//        Assertions.assertFalse(UInt128.MIN_VALUE in r)
//        Assertions.assertFalse(r.isEmpty())
//    }
//
//    @Test
//    fun testClosedSteppedAscendingRange() {
//        val r = UInt128Range(zero, five, Int128.valueOf(2uL))
//        Assertions.assertEquals(3, r.size)
//        Assertions.assertEquals(UInt128.valueOf(3uL), r.size128)
//        Assertions.assertTrue(zero in r)
//        Assertions.assertTrue(five !in r)
//        Assertions.assertTrue(five - 1u in r)
//        Assertions.assertFalse(five + 1u in r)
//        Assertions.assertFalse(UInt128.MAX_VALUE in r)
//        Assertions.assertFalse(UInt128.MIN_VALUE in r)
//        Assertions.assertFalse(r.isEmpty())
//    }
//
//    @Test
//    fun testClosedSteppedFromRangeAscendingRange() {
//        val r = zero..five step Int128.valueOf(2uL)
//        Assertions.assertEquals(3, r.size)
//        Assertions.assertEquals(UInt128.valueOf(3uL), r.size128)
//        Assertions.assertTrue(zero in r)
//        Assertions.assertTrue(five !in r)
//        Assertions.assertTrue(five - 1u in r)
//        Assertions.assertFalse(five + 1u in r)
//        Assertions.assertFalse(UInt128.MAX_VALUE in r)
//        Assertions.assertFalse(UInt128.MIN_VALUE in r)
//        Assertions.assertFalse(r.isEmpty())
//    }
//
//    @Test
//    fun testOpenSteppedAscendingRange() {
//        val r = UInt128Range(zero, five.decrement(), Int128.valueOf(2uL))
//        Assertions.assertEquals(3, r.size)
//        Assertions.assertEquals(UInt128.valueOf(3uL), r.size128)
//        Assertions.assertTrue(zero in r)
//        Assertions.assertTrue(five !in r)
//        Assertions.assertTrue(five - 1u in r)
//        Assertions.assertFalse(five + 1u in r)
//        Assertions.assertFalse(UInt128.MAX_VALUE in r)
//        Assertions.assertFalse(UInt128.MIN_VALUE in r)
//        Assertions.assertFalse(r.isEmpty())
//    }
//
//    @Test
//    fun testOpenSteppedFromRangeAscendingRange() {
//        val r = zero..<five step Int128.valueOf(2uL)
//        Assertions.assertEquals(3, r.size)
//        Assertions.assertEquals(UInt128.valueOf(3uL), r.size128)
//        Assertions.assertTrue(zero in r)
//        Assertions.assertTrue(five !in r)
//        Assertions.assertTrue(five - 1u in r)
//        Assertions.assertFalse(five + 1u in r)
//        Assertions.assertFalse(UInt128.MAX_VALUE in r)
//        Assertions.assertFalse(UInt128.MIN_VALUE in r)
//        Assertions.assertFalse(r.isEmpty())
//    }
//
//    @Test
//    fun testClosedVeryBigRange() {
//        val r = zero..UInt128.MAX_VALUE
//        Assertions.assertThrows(ArithmeticException::class.java) {r.size}
//        Assertions.assertEquals(UInt128.MAX_VALUE.increment(), r.size128)
//        Assertions.assertTrue(zero in r)
//        Assertions.assertTrue(five in r)
//        Assertions.assertTrue(five - 1u in r)
//        Assertions.assertTrue(five + 1u in r)
//        Assertions.assertTrue(UInt128.MAX_VALUE in r)
//        Assertions.assertFalse(UInt128.MIN_VALUE in r)
//        Assertions.assertFalse(r.isEmpty())
//    }
//
//    @Test
//    fun testOpenVeryBigRange() {
//        val r = zero..<UInt128.MAX_VALUE.decrement()
//        println(r)
//        println(r.size)
//        println(r.size128)
//        Assertions.assertThrows(ArithmeticException::class.java) {r.size}
//        Assertions.assertEquals(UInt128.MAX_VALUE, r.size128)
//        Assertions.assertTrue(zero in r)
//        Assertions.assertTrue(five in r)
//        Assertions.assertTrue(five - 1u in r)
//        Assertions.assertTrue(five + 1u in r)
//        Assertions.assertFalse(UInt128.MAX_VALUE in r)
//        Assertions.assertTrue(UInt128.MAX_VALUE.decrement() in r)
//        Assertions.assertFalse(UInt128.MIN_VALUE in r)
//        Assertions.assertFalse(r.isEmpty())
//    }
//
//    @Test
//    fun testClosedRangeReversed() {
//        val r = zero..five
//        val rr = r.reversed()
//        Assertions.assertEquals(r.first, rr.last)
//        Assertions.assertEquals(r.last, rr.first)
//        Assertions.assertEquals(r.step, -rr.step)
//        Assertions.assertEquals(r, rr.reversed())
//        Assertions.assertEquals(rr, rr.reversed().reversed())
//        Assertions.assertTrue(zero in r)
//        Assertions.assertTrue(five in r)
//        Assertions.assertTrue(zero in rr)
//        Assertions.assertTrue(five in rr)
//        Assertions.assertTrue(five - 1u in r)
//        Assertions.assertTrue(five - 1u in rr)
//        Assertions.assertFalse(r.isEmpty())
//        Assertions.assertFalse(rr.isEmpty())
//    }
//
//    @Test
//    fun testOpenRangeReversed() {
//        val r = zero..<five
//        val rr = r.reversed()
//        Assertions.assertEquals(r.first, rr.last)
//        Assertions.assertEquals(r.last, rr.first)
//        Assertions.assertEquals(r.step, -rr.step)
//        Assertions.assertEquals(r, rr.reversed())
//        Assertions.assertEquals(rr, rr.reversed().reversed())
//        Assertions.assertTrue(zero in r)
//        Assertions.assertTrue(five.decrement() in r)
//        Assertions.assertTrue(zero in rr)
//        Assertions.assertTrue(five.decrement() in rr)
//        Assertions.assertTrue(five - 2u in r)
//        Assertions.assertTrue(five - 2u in rr)
//        Assertions.assertFalse(r.isEmpty())
//        Assertions.assertFalse(rr.isEmpty())
//    }
//
//    @Test
//    fun testInvalidStep() {
//        Assertions.assertThrows(IllegalArgumentException::class.java) {UInt128Range(zero, five, Int128.ZERO)}
//        Assertions.assertThrows(IllegalArgumentException::class.java) {UInt128Range(zero, five, Int128.MIN_VALUE)}
//    }
//
//    @Test fun testClosedSingletonRange() {
//        val r =zero..zero
//        Assertions.assertEquals(1, r.size)
//        Assertions.assertEquals(UInt128.ONE, r.size128)
//        Assertions.assertTrue(UInt128.ZERO in r)
//        Assertions.assertFalse(r.isEmpty())
//    }
//
//    @Test fun testOpenSingletonRange() {
//        val r =zero..<UInt128.ONE
//        Assertions.assertEquals(1, r.size)
//        Assertions.assertEquals(UInt128.ONE, r.size128)
//        Assertions.assertTrue(UInt128.ZERO in r)
//        Assertions.assertFalse(r.isEmpty())
//    }
//
//    @Test fun testClosedEmptyRange() {
//        Assertions.assertTrue((Int128.ONE..Int128.ZERO).isEmpty())
//        Assertions.assertTrue(UInt128Range(UInt128.ONE, UInt128.ZERO).isEmpty())
//        Assertions.assertTrue(UInt128Range(UInt128.ONE, UInt128.MIN_VALUE).isEmpty())
//        Assertions.assertTrue(UInt128Range(UInt128.MAX_VALUE, UInt128.ZERO).isEmpty())
//        Assertions.assertTrue(UInt128Range(UInt128.MAX_VALUE, UInt128.MIN_VALUE).isEmpty())
//    }
//
//    @Test fun testOpenEmptyRange() {
//        Assertions.assertTrue((Int128.ZERO..<Int128.ZERO).isEmpty())
//        Assertions.assertTrue(UInt128Range(UInt128.ONE, UInt128.ZERO).isEmpty())
//        Assertions.assertTrue(UInt128Range(UInt128.ONE, UInt128.MIN_VALUE).isEmpty())
//        Assertions.assertTrue(UInt128Range(UInt128.MAX_VALUE, UInt128.ZERO).isEmpty())
//        Assertions.assertTrue(UInt128Range(UInt128.MAX_VALUE, UInt128.MIN_VALUE).isEmpty())
//    }
//
//    @Test fun testClosedRangeIterable() {
//        val r =zero..five
//        Assertions.assertEquals(listOf(0,1,2,3,4,5).map{UInt128.valueOf(it)}, r.toList())
//        Assertions.assertEquals(listOf(0,1,2,3,4,5).filter{it%2==0}.map{UInt128.valueOf(it)},
//            r.filter{(it%UInt128.valueOf(2uL)).isZero()}.toList())
//
//        var sum = UInt128.ZERO
//        for (i in r) sum += i
//        Assertions.assertEquals(Int128.valueOf(15uL), sum)
//    }
//
//    @Test fun testOpenRangeIterable() {
//        val r =zero..<five
//        Assertions.assertEquals(listOf(0,1,2,3,4).map{UInt128.valueOf(it)}, r.toList())
//        Assertions.assertEquals(listOf(0,1,2,3,4).filter{it%2==0}.map{UInt128.valueOf(it)},
//            r.filter{(it%UInt128.valueOf(2uL)).isZero()}.toList())
//
//        var sum = UInt128.ZERO
//        for (i in r) sum += i
//        Assertions.assertEquals(UInt128.valueOf(10uL), sum)
//    }
//}