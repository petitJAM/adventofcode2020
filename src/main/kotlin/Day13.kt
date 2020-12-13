import util.Input
import util.TODO
import util.alsoPrint
import util.readInputFile

@Suppress("unused")
fun day13() {
    val input = readInputFile(13)
    val input2 = Input(
        """
            939
            7,13,x,x,59,x,31,19
        """.trimIndent()
    )

    val (earliestTimestamp, busIds) = parseInput(input)

    println(earliestTimestamp)
    println(busIds)

    val answer = busIds.earliestTimestampAfter(earliestTimestamp).alsoPrint()
        .let { (busId, departureTime) ->
            busId * (departureTime - earliestTimestamp)
        }
    println("The answer to part 1 is ... $answer")

    val answer2 = -1
    println("The answer to part 2 is ... $answer2")
}

// Part 1

private fun List<BusId>.earliestTimestampAfter(after: Int): Pair<BusId, Int> = this
    .map { busId ->
        busId to busId.departureTimesSequence().first { it >= after }
    }
    .minByOrNull { it.second }!!

// Data

private fun BusId.departureTimesSequence(): Sequence<Int> =
    generateSequence(0) { it + this }

private typealias BusId = Int

private fun parseInput(input: Input): Pair<Int, List<BusId>> =
    input.toList()
        .let {
            Pair(
                it[0].toInt(),
                it[1].split(",").filterNot { it == "x" }.map(String::toInt)
            )
        }