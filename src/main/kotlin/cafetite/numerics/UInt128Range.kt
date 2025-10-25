package cafetite.numerics

class UInt128Range(
    override val start: UInt128,
    override val endInclusive: UInt128,
    val step: Int128 = Int128.ONE
) : ClosedRange<UInt128>, OpenEndRange<UInt128>, Iterable<UInt128> {
    companion object {

        private fun differenceModulo(a: UInt128, b: UInt128, c: UInt128): UInt128 {
            val ac = a % c
            val bc = b % c
            return if (ac >= bc) ac - bc else ac - bc + c
        }
        private fun getProgressionLastElement(start: UInt128, end: UInt128, step: Int128): UInt128 = when {
            step > Int128.ZERO -> if (start >= end) end else end - differenceModulo(end, start, step.toUInt128())
            step < Int128.ZERO -> if (start <= end) end else end + differenceModulo(start, end, (-step).toUInt128())
            else -> throw kotlin.IllegalArgumentException("Step is zero.")
        }
    }

    init {
        if (step.isZero()) throw kotlin.IllegalArgumentException("Step must be non-zero.")
        if (step == Int128.MIN_VALUE) throw kotlin.IllegalArgumentException("Step must be greater than Int128.MIN_VALUE to avoid overflow on negation.")
    }

    val isAscending = step > Int128.ZERO

    /**
     * The first element in the progression.
     */
    val first: UInt128 = start

    /**
     * The last element in the progression.
     */
    val last: UInt128 = getProgressionLastElement(start, endInclusive, step)

    override val endExclusive: UInt128
        get() {
            if (last == UInt128.MAX_VALUE) error("Cannot return the exclusive upper bound of a range that includes MAX_VALUE.")
            return last.increment()
        }

    val size: Int
        get() {
            val steps = size128
            if (steps > UInt128.valueOf(Int.MAX_VALUE))
                throw ArithmeticException("Range too large to represent as Int")
            return steps.toInt()
        }

    val size128: UInt128
        get() {
            if (isEmpty()) return UInt128.ONE
            val diff = if (isAscending) endInclusive - start else start - endInclusive
            return (diff / step.toUInt128()) + UInt128.ONE
        }

    override fun contains(value: UInt128): Boolean {
        if (isAscending) {
            if (value < start || value > endInclusive) return false
        } else {
            if (value > start || value < endInclusive) return false
        }
        val diff = (value - start)
        return (diff % step.abs().toUInt128()).isZero()
    }

    override fun iterator(): Iterator<UInt128> = object : Iterator<UInt128> {
        private val finalElement: UInt128 = last
        private var hasNext: Boolean = if (step > Int128.ZERO) first <= last else first >= last
        private var next: UInt128 = if (hasNext) first else finalElement

        override fun hasNext(): Boolean = hasNext

        override fun next(): UInt128 {
            val value = next
            if (value == finalElement) {
                if (!hasNext) throw kotlin.NoSuchElementException()
                hasNext = false
            } else {
                next += step.toUInt128()
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

    fun reversed() = UInt128Range(last, first, -step)

    infix fun step(step: Int128): UInt128Range = UInt128Range(first, last, if (this.step > Int128.ZERO) step else -step)

    override fun equals(other: Any?): Boolean =
        other is UInt128Range && (isEmpty() && other.isEmpty() ||
                first == other.first && last == other.last && step == other.step)

    override fun hashCode(): Int =
        if (isEmpty()) -1 else (31 * (31 * first.hashCode() + last.hashCode()) + step.hashCode())

    override fun toString(): String =
        if (step.isPositive()) "$first..$last step $step" else "$first downTo $last step ${-step}"
}
