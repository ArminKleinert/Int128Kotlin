package de.kleinert.numerics

class UInt128Range(
    override val start: UInt128,
    override val endInclusive: UInt128,
    val step: UInt128 = UInt128.ONE
) : ClosedRange<UInt128>, OpenEndRange<UInt128>, Iterable<UInt128> {

    init {
        require(step != UInt128.ZERO) { "step must be non-zero" }
    }

    private val forward: Boolean = start <= endInclusive

    override val endExclusive: UInt128
        get() = if (forward) endInclusive + UInt128.ONE else endInclusive - UInt128.ONE

    override fun isEmpty(): Boolean {
        return if (forward) start > endInclusive else start < endInclusive
    }

    override fun contains(value: UInt128): Boolean {
        if (isEmpty()) return false
        return if (forward) {
            value >= start && value <= endInclusive &&
                    ((value - start) % step == UInt128.ZERO)
        } else {
            value <= start && value >= endInclusive &&
                    ((start - value) % step == UInt128.ZERO)
        }
    }

    override fun iterator(): Iterator<UInt128> = object : Iterator<UInt128> {
        private var current = start
        private var hasNext = !isEmpty()

        override fun hasNext(): Boolean = hasNext

        override fun next(): UInt128 {
            if (!hasNext) throw NoSuchElementException()

            val value = current
            current = if (forward) current + step else current - step

            if (forward) {
                if (current > endInclusive) hasNext = false
            } else {
                if (current < endInclusive) hasNext = false
            }

            return value
        }
    }

    /**
     * The number of elements in this range as an Int.
     * Throws [ArithmeticException] if the size exceeds [Int.MAX_VALUE].
     */
    val size: Int
        get() {
            if (isEmpty()) return 0
            val diff = if (forward) endInclusive - start else start - endInclusive
            val steps = diff / step + UInt128.ONE
            if (steps > UInt128.valueOf(Int.MAX_VALUE.toULong()))
                throw ArithmeticException("Range size too large for Int")
            return steps.toInt()
        }

    /**
     * Same as [size], but returned as [UInt128] and never throws.
     */
    val size128: UInt128
        get() {
            if (isEmpty()) return UInt128.ZERO
            val diff = if (forward) endInclusive - start else start - endInclusive
            return diff / step + UInt128.ONE
        }

    override fun toString(): String {
        val stepStr = if (step == UInt128.ONE) "" else " step $step"
        return if (forward) "$start..$endInclusive$stepStr" else "$start downTo $endInclusive$stepStr"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UInt128Range) return false
        return start == other.start && endInclusive == other.endInclusive && step == other.step
    }

    override fun hashCode(): Int {
        var result = start.hashCode()
        result = 31 * result + endInclusive.hashCode()
        result = 31 * result + step.hashCode()
        return result
    }
}
