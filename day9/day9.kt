fun main() {
    val numbers: List<Long> = input.lines().map { it.toLong() }
    var candidate: Long = 0

    // A
    for (i in 25..numbers.indices.last()) {
        val preceding = numbers.subList((i - 25), i)
        if (!numbers[i].isSumOfTwoIn(preceding)) {
            println("[A] Number: ${numbers[i]}") // [A] Number: 167829540
            candidate = numbers[i]
            break
        }
    }

    // B
    println("[B] Weakness: ${candidate.rangeOfSum(numbers)}")
}

fun Long.isSumOfTwoIn(preceding: List<Long>): Boolean {
    for (a in preceding.indices) {
        for (b in preceding.indices) {
            if (b == a) continue
            if (preceding[a] + preceding[b] == this) return true
        }
    }
    return false
}

fun Long.rangeOfSum(numbers: List<Long>): Long {
    for (a in numbers.indices) {
        var candidate = numbers[a]
        for (b in (a + 1)..numbers.indices.last()) {
            candidate += numbers[b]
            if (candidate > this) continue
            if (candidate == this) {
                val sub = numbers.subList(a, b).sorted()
                return sub.first() + sub.last()
            }
        }
    }
    return 0
}
