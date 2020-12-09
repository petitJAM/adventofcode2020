import util.Input
import util.TODO
import util.readInputFile
import java.math.BigInteger

@Suppress("unused")
fun day9() {
    val input = readInputFile(9)
    val input2 = Input(
        """
            35
            20
            15
            25
            47
            40
            62
            55
            65
            95
            102
            117
            150
            182
            127
            219
            299
            277
            309
            576
    """.trimIndent()
    )

    val exampleAnswer = firstInvalidNumber(input2.toBigIntegerList(), 5)
    println("The example answer to part 1 is ... $exampleAnswer")

    val answer = firstInvalidNumber(input.toBigIntegerList(), 25)
    println("The answer to part 1 is ... $answer")

    val answer2 = -1
    println("The answer to part 2 is ... $answer2")
}

// Part 1

private fun firstInvalidNumber(numbers: List<BigInteger>, preambleLength: Int): BigInteger =
    numbers.drop(preambleLength)
        .filterIndexed { index, bigInteger ->
            !bigInteger.isSumOfAny(numbers.subList(index, index + preambleLength))
        }
        .first()

private fun BigInteger.isSumOfAny(list: List<BigInteger>): Boolean =
    list.any {
        (this - it) in list
    }