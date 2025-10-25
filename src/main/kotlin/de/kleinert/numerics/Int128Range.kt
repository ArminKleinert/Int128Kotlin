package de.kleinert.numerics


data class Int128Range (
    override val start: Int128,
    override val endInclusive: Int128,
    val step: Int128 = Int128.ONE
) :  ClosedRange<Int128>, OpenEndRange<Int128>, Iterable<Int128> {
    companion object {
        private inline fun  mod(a: Int128, b: Int128): Int128 {
            val mod = a % b
            return if (mod.isPositive()) mod else mod + b
        }
        private inline fun differenceModulo(a: Int128, b: Int128, c: Int128): Int128 {
            return mod(mod(a, c) - mod(b, c), c)
        }
        private fun getProgressionLastElement(start: Int128, end: Int128, step: Int128): Int128 = when {
            step.isZero() -> throw kotlin.IllegalArgumentException("Step is zero.")
            step .isPositive() -> if (start >= end) end else end - differenceModulo(end, start, step)
            else -> if (start <= end) end else end + differenceModulo(start, end, -step)
        }
    }
    init {
        if (step.isZero()) throw kotlin.IllegalArgumentException("Step must be non-zero.")
        if (step == Int128.MIN_VALUE) throw kotlin.IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation.")
    }

    private val isAscending = step > Int128.ZERO

    /**
     * The first element in the progression.
     */
    val first: Int128 = start

    /**
     * The last element in the progression.
     */
    val last: Int128 = getProgressionLastElement(start, endInclusive, step)

    override val endExclusive: Int128 get() {
        if (last == Int128.MAX_VALUE) error("Cannot return the exclusive upper bound of a range that includes MAX_VALUE.")
        return last.increment()
    }

    val size: Int
        get() {
            val steps = size128
            if (steps > Int128.valueOf(Int.MAX_VALUE))
                throw ArithmeticException("Range too large to represent as Int")
            return steps.toInt()
        }

    val size128: Int128
        get() {
            if (isEmpty()) return Int128.ONE
            val diff = if (isAscending) endInclusive - start else start - endInclusive
            return (diff / step.abs()) + Int128.ONE
        }

    override fun contains(value: Int128): Boolean {
        if (isAscending) {
            if (value < start || value > endInclusive) return false
        } else {
            if (value > start || value < endInclusive) return false
        }
        val diff = (value - start)
        return (diff % step).isZero()
    }

    override fun iterator(): Iterator<Int128> = object : Iterator<Int128> {
        private val finalElement: Int128 = last
        private var hasNext: Boolean = if (step > Int128.ZERO) first <= last else first >= last
        private var next: Int128 = if (hasNext) first else finalElement

        override fun hasNext(): Boolean = hasNext

        override fun next(): Int128 {
            val value = next
            if (value == finalElement) {
                if (!hasNext) throw kotlin.NoSuchElementException()
                hasNext = false
            }
            else {
                next += step
            }
            return value
        }
    }

    /**
     * Checks if the progression is empty.
     *
     * Progression with a positive step is empty if its first element is greater than the last element.
     * Progression with a negative step is empty if its first element is less than the last element.
     */
    override fun isEmpty(): Boolean = if (step.isGtZero()) first > last else first < last

    override fun equals(other: Any?): Boolean =
        other is Int128Range && (isEmpty() && other.isEmpty() ||
                first == other.first && last == other.last && step == other.step)

    override fun hashCode(): Int =
        if (isEmpty()) -1 else (31 * (31 * first.hashCode() + last.hashCode()) + step.hashCode())

    override fun toString(): String = if (step.isPositive()) "$first..$last step $step" else "$first downTo $last step ${-step}"
}