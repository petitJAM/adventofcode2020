import util.*
import kotlin.TODO
import kotlin.math.abs

@Suppress("unused")
fun day12() {
    val input = readInputFile(12)
    val input2 = Input(
        """
            F10
            N3
            F7
            R90
            F11
        """.trimIndent()
    )

    val input3 = Input(
        """
            E1
            S1
            W1
            N1
            F1
            R90
            F1
            R90
            F1
            R90
            F1
            R90
        """.trimIndent()
    )

    val directions = parseInput(input)

    val answer = directions.calculateFinalPosition().first.let { abs(it.x) + abs(it.y) }
    println("The answer to part 1 is ... $answer")

    val answer2 = -1
    println("The answer to part 2 is ... $answer2")
}

// Part 1

private fun List<Direction>.calculateFinalPosition(
    startingPoint: Point = Point(0, 0),
    startingFacing: Facing = Facing.E
): Pair<Point, Facing> = this
    .fold(Pair(startingPoint, startingFacing)) { acc, direction ->
        val point = acc.first
        val facing = acc.second
        when (direction.action) {
            Action.N -> Point(point.x, point.y - direction.value) to facing
            Action.S -> Point(point.x, point.y + direction.value) to facing
            Action.E -> Point(point.x + direction.value, point.y) to facing
            Action.W -> Point(point.x - direction.value, point.y) to facing
            Action.L -> point to facing.turn(-direction.value)
            Action.R -> point to facing.turn(direction.value)
            Action.F -> when (facing) {
                Facing.N -> Point(point.x, point.y - direction.value) to facing
                Facing.S -> Point(point.x, point.y + direction.value) to facing
                Facing.E -> Point(point.x + direction.value, point.y) to facing
                Facing.W -> Point(point.x - direction.value, point.y) to facing
            }
        }
            .also { (point, facing) ->
                println("From $acc, \t\tmove by $direction \t\tto $point facing $facing")
            }
    }

// Data

private typealias Direction = Pair<Action, Int>

private val Direction.action: Action
    get() = first

private val Direction.value: Int
    get() = second

private enum class Action {
    N, S, E, W, L, R, F
}

private enum class Facing(val degrees: Int) {
    N(0), E(90), S(180), W(270);

    fun turn(degrees: Int): Facing =
        when ((this.degrees + degrees) % 360) {
            0 -> N
            90, -270 -> E
            180, -180 -> S
            270, -90 -> W
            else -> throw IllegalArgumentException("Can't turn by $degrees degrees")
        }
}

private fun parseInput(input: Input): List<Direction> {
    return input.toList()
        .map {
            Direction(
                Action.valueOf(it[0].toString()),
                it.substring(1).toInt()
            )
        }
}