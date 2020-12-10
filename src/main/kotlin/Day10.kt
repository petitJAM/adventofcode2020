import util.Input
import util.readInputFile

@Suppress("unused")
fun day10() {
    val input = readInputFile(10)
    val input2 = Input(
        """
            16
            10
            15
            5
            1
            11
            7
            19
            6
            12
            4
        """.trimIndent()
    )
    val input3 = Input(
        """
            28
            33
            18
            42
            31
            14
            46
            20
            48
            47
            24
            23
            49
            45
            19
            38
            39
            11
            1
            32
            25
            35
            8
            17
            7
            9
            4
            2
            34
            10
            3
        """.trimIndent()
    )

    val exAnswer1 = puzzleAnswer(
        findJoltageDifferences(input2.toIntList())
    )
    println("Example answer 1 is ... $exAnswer1")

    val exAnswer2 = puzzleAnswer(
        findJoltageDifferences(input3.toIntList())
    )
    println("Example answer 1 is ... $exAnswer2")

    val answer = puzzleAnswer(
        findJoltageDifferences(input.toIntList())
    )
    println("The answer to part 1 is ... $answer")

    val answer2 = -1
    println("The answer to part 2 is ... $answer2")
}

// Part 1

private typealias AdapterRating = Int

private fun findJoltageDifferences(
    ratings: List<AdapterRating>,
    chargingOutletJoltage: Int = 0,
): List<Int> =
    ratings
        .sorted()
        .let {
            listOf(chargingOutletJoltage) + it
        }
        .let {
            it + (it.last() + 3)
        }
        .zipWithNext()
        .map { it.second - it.first }

private fun puzzleAnswer(differences: List<Int>): Int {
    val oneCount = differences.count { it == 1 }
    val threeCount = differences.count { it == 3 }
    return oneCount * threeCount
}