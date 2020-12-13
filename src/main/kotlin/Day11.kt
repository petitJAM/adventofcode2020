import util.Input
import util.readInputFile

@Suppress("unused")
fun day11() {
    val input = readInputFile(11)
    val input2 = Input(
        """
            L.LL.LL.LL
            LLLLLLL.LL
            L.L.L..L..
            LLLL.LL.LL
            L.LL.LL.LL
            L.LLLLL.LL
            ..L.L.....
            LLLLLLLLLL
            L.LLLLLL.L
            L.LLLLL.LL
        """.trimIndent()
    )

    val seatingArea = parseInput(input)

    val answer = countOfOccupiedOnceStable(seatingArea, SeatingArea::next)
    println("The answer to part 1 is ... $answer")

    val answer2 = countOfOccupiedOnceStable(seatingArea, SeatingArea::next2)
    println("The answer to part 2 is ... $answer2")
}

// Part 2

private fun SeatingArea.next2(): SeatingArea = this
    .mapIndexed { y, row ->
        row.mapIndexed { x, seat ->
            val point = Point(x, y)
            when (seat) {
                Seat.NO_SEAT -> Seat.NO_SEAT
                Seat.EMPTY -> if (occupiedVisibleNeighborCount(point) == 0) {
                    Seat.OCCUPIED
                } else {
                    Seat.EMPTY
                }
                Seat.OCCUPIED -> if (occupiedVisibleNeighborCount(point) >= 5) {
                    Seat.EMPTY
                } else {
                    Seat.OCCUPIED
                }
            }
        }
    }

private fun SeatingArea.occupiedVisibleNeighborCount(point: Point): Int =
    this.visibleNeighbors(point).count { it == Seat.OCCUPIED }

@OptIn(ExperimentalStdlibApi::class)
private fun SeatingArea.visibleNeighbors(point: Point): List<Seat> = buildList {
    val validY = this@visibleNeighbors.indices
    val validX = this@visibleNeighbors[0].indices

    val directions = listOf(
        -1 to -1, 0 to -1, 1 to -1,
        -1 to 0, 1 to 0,
        -1 to 1, 0 to 1, 1 to 1,
    )
    directions.forEach { direction ->
        var next = Point(point.x + direction.x, point.y + direction.y)
        while (next.x in validX && next.y in validY) {
            val seat = this@visibleNeighbors[next]
            if (seat != Seat.NO_SEAT) {
                add(seat)
                break
            }
            next = Point(next.x + direction.x, next.y + direction.y)
        }
    }
}

// Part 1

private fun countOfOccupiedOnceStable(seatingArea: SeatingArea, method: SeatingArea.() -> SeatingArea): Int =
    seatingArea.runUntilStable(method).occupiedCount()

private fun SeatingArea.runUntilStable(method: SeatingArea.() -> SeatingArea): SeatingArea {
    var current = this
    var next = current.method()

    while (current != next) {
        current = next
        next = current.method()
    }

    return current
}

private fun SeatingArea.occupiedCount(): Int =
    sumBy { row -> row.count { it == Seat.OCCUPIED } }

// Calculation

private fun SeatingArea.next(): SeatingArea = this
    .mapIndexed { y, row ->
        row.mapIndexed { x, seat ->
            val point = Point(x, y)
            when (seat) {
                Seat.NO_SEAT -> Seat.NO_SEAT
                Seat.EMPTY -> {
                    if (this.occupiedNeighborCount(point) == 0) {
                        Seat.OCCUPIED
                    } else {
                        Seat.EMPTY
                    }
                }
                Seat.OCCUPIED -> {
                    if (this.occupiedNeighborCount(point) >= 4) {
                        Seat.EMPTY
                    } else {
                        Seat.OCCUPIED
                    }
                }
            }
        }
    }

private fun SeatingArea.occupiedNeighborCount(point: Point): Int =
    point.neighbors()
        .filter { it.y in this.indices && it.x in this[it.y].indices }
        .map { this[it] }
        .count { it == Seat.OCCUPIED }

private operator fun SeatingArea.get(point: Point): Seat =
    this[point.y][point.x]

private typealias Point = Pair<Int, Int>

private val Point.x: Int
    get() = first

private val Point.y: Int
    get() = second

@OptIn(ExperimentalStdlibApi::class)
private fun Point.neighbors(): List<Point> {
    return buildList {
        add(Point(x - 1, y - 1))
        add(Point(x - 1, y))
        add(Point(x - 1, y + 1))
        add(Point(x, y - 1))
        add(Point(x + 1, y - 1))
        add(Point(x + 1, y))
        add(Point(x, y + 1))
        add(Point(x + 1, y + 1))
    }
}

// Data

private enum class Seat {
    NO_SEAT,
    EMPTY,
    OCCUPIED,
    ;

    fun toChar(): Char = when (this) {
        NO_SEAT -> '.'
        EMPTY -> 'L'
        OCCUPIED -> '#'
    }

    companion object {
        fun fromChar(char: Char): Seat = when (char) {
            '.' -> NO_SEAT
            'L' -> EMPTY
            '#' -> OCCUPIED
            else -> throw IllegalArgumentException("Invalid seat char '$char'")
        }
    }
}

private typealias SeatingArea = List<List<Seat>>

private fun SeatingArea.toDisplayString(): String =
    this.joinToString("\n") { it.map(Seat::toChar).joinToString("") }

private fun parseInput(input: Input): SeatingArea =
    input.toList()
        .map(String::toCharArray)
        .map {
            it.map { char -> Seat.fromChar(char) }
        }