fun main() {
    var r = (0..5)
    println(r.first)
    println(r.last)
    println(r.endInclusive)
    println(r.endExclusive)
    println(r.step)
    println(r.isEmpty())
    println()

    val d = (5 downTo 0)
    println(d.first)
    println(d.last)
    println(d.step)
    println(d.isEmpty())
    println()

    val p = IntProgression.fromClosedRange(0, 5, -1)
    println(p.first)
    println(p.last)
    println(p.step)
    println(r.isEmpty())
    println()
}