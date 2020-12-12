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
    println(seatingArea.toDisplayString())

    val answer = countOfOccupiedOnceStable(seatingArea)
    println("The answer to part 1 is ... $answer")

    val answer2 = -1
    println("The answer to part 2 is ... $answer2")
}

// Part 1

private fun countOfOccupiedOnceStable(seatingArea: SeatingArea): Int =
    seatingArea.runUntilStable().occupiedCount()

private fun SeatingArea.runUntilStable(): SeatingArea {
    var current = this
    var next = current.next()

    while (current != next) {
        current = next
        next = current.next()
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

private fun SeatingArea.occupiedNeighborCount(point: Point): Int {
    return point.neighbors()
        .filter { it.y in this.indices && it.x in this[it.y].indices }
        .map { this[it] }
        .count { it == Seat.OCCUPIED }
}

@OptIn(ExperimentalStdlibApi::class)
private fun SeatingArea.points(): List<Point> {
    return buildList {
        this@points.indices.forEach { y ->
            this@points[y].indices.forEach { x ->
                this.add(Point(x, y))
            }
        }
    }
}

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