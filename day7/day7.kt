data class Bag(val type: String, val color: String)
data class Rule(val bag: Bag, val contents: List<Bag>)

fun main() {
    val txt = input.lines()
    val target = Bag("shiny", "gold")

    // A
    val rules: List<Rule> = txt.map { rule ->
        val comps = rule.split(" bags contain ")
        val contents = comps.last().replace(".", "").split(", ").fold(mutableListOf<String>()) { list, str ->
            if (str == "no other bags") return@fold list
            val count = str.split(" ").first().toInt()
            val type = str.replace(count.toString() + " ", "").replace("bags", "").replace("bag", "").trim()
            MutableList(count) { type }.forEach { el -> list.add(el) }
            list
        }.map {
            val ic = it.split(" ")
            Bag(ic.first(), ic.last())
        }
        val oc = comps.first().split(" ")
        val outer = Bag(oc.first(), oc.last())
        Rule(outer, contents)
    }.sortedWith(compareBy<Rule> { it.bag.type }.thenBy { it.bag.color })

    val a = rules.toMutableList()
    var targets: MutableList<Bag> = mutableListOf(target)
    var valids: MutableList<Bag> = a.filter { it.contents.intersect(targets.asIterable()).isNotEmpty() }.map { it.bag }.toMutableList()
    a.removeAll { valids.contains(it.bag) }

    var countA = valids.count()

    do {
        targets = valids
        valids = a.filter { it.contents.intersect(targets.asIterable()).isNotEmpty() }.map { it.bag }.toMutableList()
        countA = countA + valids.count()
        a.removeAll { valids.contains(it.bag) }
    } while (targets.isNotEmpty())

    println("[A] Count: " + countA)

    // B
    var inners = rules.filter { it.bag == target }.map { it.contents }.toMutableList()
    var countB = 0

    do {
        inners.removeFirst().forEach { bag ->
            countB = countB + 1
            rules.filter { it.bag == bag }.map { it.contents }.forEach { inners.add(it) }
        }
    } while (inners.isNotEmpty())

    println("[B] Count: " + countB)
}
