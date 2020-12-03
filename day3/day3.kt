const val TREE = "#"

data class Coordinate(val x: Int, val y: Int) {
    fun next(slope: Coordinate): Coordinate = Coordinate(x = this.x + slope.x, y = this.y + slope.y)

    companion object {
        val slopeA: Coordinate = Coordinate(3, 1)

        val slopesB: List<Coordinate> = listOf(
            Coordinate(1, 1),
            Coordinate(3, 1),
            Coordinate(5, 1),
            Coordinate(7, 1),
            Coordinate(1, 2)
        )
    }
}

data class Graph(val rows: List<String>) {
    private fun charAt(coord: Coordinate): String? {
        if (coord.y > (rows.count() - 1)) return null
        val x = (coord.x % rows[coord.y].count())
        return rows[coord.y][x].toString()
    }

    fun treesAlongSlope(slope: Coordinate): Int {
        var position = Coordinate(x = 0, y = 0)
        var treeCount: Int = 0

        // This should be a fold blah blah I know Chris
        while (position.y <= rows.count()) {
            position = position.next(slope)
            if (this.charAt(position) == TREE) { treeCount ++ }
        }

        return treeCount
    }
}

fun main(args: Array<String>) {
    val graph = Graph(pattern.trim().lines())
    val treesPartA = graph.treesAlongSlope(Coordinate.slopeA)
    println("Trees Hit (Part A): " + treesPartA)

    val treesPartB: Long = Coordinate.slopesB
        .map { graph.treesAlongSlope(it).toLong() }
        .reduce { acc, i -> acc * i }
    println("Trees Hit (Part B): " + treesPartB)
}
