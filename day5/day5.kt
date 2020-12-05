val Char.isLow: Boolean get() = listOf('F', 'L').contains(this)
fun IntRange.pick(char: Char): Int = if (char.isLow) this.first else this.last

data class Seat(val row: Int, val col: Int) {
    val id: Int get() = ((row * 8) + col)

    companion object {
        fun getSeat(code: String): Seat = Seat(getRow(code.substring(0, 7)), getCol(code.substring(7, 10)))

        fun getRow(code: String): Int {
            return code.substring(0, code.count() - 1).toList()
                .fold((0..127)) { r, c  ->
                    val m = (((r.last - r.first) + if (c.isLow) -1 else 1) / 2)
                    ((r.first + if (c.isLow) 0 else m) .. (if (c.isLow) r.first + m else r.last))
                }
                .pick(code.last())
        }

        fun getCol(code: String): Int {
            return code.substring(0, code.count() - 1).toList()
                .fold((0..7)) { r, c  ->
                    val m = (((r.last - r.first) + if (c.isLow) -1 else 1) / 2)
                    ((r.first + if (c.isLow) 0 else m) .. (if (c.isLow) r.first + m else r.last))
                }
                .pick(code.last())
        }
    }
}

fun main() {
    // Part A
    val seats = input.trim().lines().map { Seat.getSeat(it) }
    val maxSeatId = seats.map { it.id }.sorted().last()
    println("Max SeatID: " + maxSeatId)

    // Part B
    val rs = seats
        .filter { !listOf(0, 127).contains(it.row) }
        .sortedWith(compareBy<Seat> { it.row }.thenBy { it.col })
        .groupBy { it.row }
        .filterValues { it.count() < 8 }
        .values
        .first()

    // lol i have 2 kids don't @ me unless you do too
    println(rs)
    println(Seat(row = 77, col = 3).id)
}
