import util.Input
import util.readInputFile
import kotlin.math.abs
import kotlin.math.pow

fun day5() {
    val input = readInputFile(5).toList()
//    val input2 = Input(
//        """
//            BFFFBBFRRR
//            FFFBBBFRRR
//            BBFFBBFRLL
//    """.trimIndent()
//    ).toList()

    val seats = input.map(::parseSeat)

    val answer = seats.maxByOrNull { it.id }?.id
    println("The answer to part 1 is ... $answer")

    val answer2 = seats.map(Seat::id).sorted()
        .zipWithNext()
        .first { abs(it.first - it.second) > 1 }
        .let { it.first + 1 }
    println("The answer to part 2 is ... $answer2")
}

// Data

private typealias Seat = Pair<Int, Int>

private val Seat.row: Int
    get() = first

private val Seat.column: Int
    get() = second

private val Seat.id: Int
    get() = row * 8 + column

private fun parseSeat(input: String): Seat {
    val rowChars = input.substring(0, 7).toCharArray()
    val colChars = input.substring(7).toCharArray()

    val row = rowChars.mapIndexed { index, c ->
        if (c == 'F') 0 else 2f.pow(6 - index).toInt()
    }.sum()

    val col = colChars.mapIndexed { index, c ->
        if (c == 'L') 0 else 2f.pow(2 - index).toInt()
    }.sum()

    return Seat(row, col)
}
