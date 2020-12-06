fun main() {
    val txt = input.split("\n\n")

    // A
    val anyone = txt
        .map {
            it.lines()
                .map { line -> line.toList() }
                .flatMap { it }
                .toSet()
                .toList()
                .sorted()
        }
        .map { it.count() }
        .fold(0) { total, count -> total + count }

    println("[A] Anyone Count: " + anyone)

    // B
    val everyone = txt
        .map { raw ->
            slideBy(
                raw.lines()
                .map { line -> line.toList() }
                .flatMap { item -> item }
                .toList()
                .sorted()
            ) { it }.filter { it.count() == raw.lines().count() }
        }
        .map { it.count() }
        .sum()

    println("[B] Everyone Count: " + everyone)
}

fun <T, R> slideBy(list: List<T>, classifier: (T) -> R): List<List<T>> {
    tailrec fun slideBy_(list: List<T>, acc: MutableList<List<T>>): MutableList<List<T>> =
        if (list.isEmpty()) acc
        else slideBy_(list.dropWhile { classifier(it) == classifier(list.first()) },  acc.apply { add(list.takeWhile { classifier(it) == classifier(list.first()) } )} )
    return slideBy_(list, mutableListOf())
}
