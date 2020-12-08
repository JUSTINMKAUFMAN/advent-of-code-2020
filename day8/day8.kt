data class Instruction(val operation: String, val argument: Int)

fun run(instructions: List<Instruction>): Pair<Int, Boolean> {
    var ran: MutableList<Int> = mutableListOf<Int>()
    var accumulator = 0
    var cursor = 0
    var didExit = false

    while (!ran.contains(cursor)) {
        ran.add(cursor)
        when (instructions[cursor].operation) {
            "acc" -> { accumulator += instructions[cursor].argument; cursor += 1 }
            "jmp" -> { cursor += instructions[cursor].argument }
            else -> { cursor += 1 }
        }

        if (cursor == instructions.count()) {
            didExit = true
            break
        }
    }

    return Pair(accumulator, didExit)
}

fun main() {
    val instructions: List<Instruction> = input.lines().map {
        val comps = it.split(" ")
        val arg = if (comps[1].startsWith("+")) comps[1].replace("+", "").toInt() else comps[1].toInt()
        Instruction(comps[0], arg)
    }

    // A
    println("[A] Accumulator: ${run(instructions).first}")

    // B
    for (i in 0..(instructions.count() - 1)) {
        val (acc, didExit) = when (instructions[i].operation) {
            "jmp" -> run(instructions.updated(i, Instruction("nop", instructions[i].argument)))
            "nop" -> run(instructions.updated(i, Instruction("jmp", instructions[i].argument)))
            else -> continue
        }

        if (didExit) { println("[B] Accumulator: ${acc}"); break }
    }
}

fun <E> Iterable<E>.updated(index: Int, elem: E) = mapIndexed { i, existing ->  if (i == index) elem else existing }
