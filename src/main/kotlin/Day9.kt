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

    val exampleAnswer2 = findContiguousSetThatSums(input2.toBigIntegerList(), exampleAnswer)
        .sumOfSmallestAndLargest()
    println("The example answer to part 2 is ... $exampleAnswer2")

    val answer2 = findContiguousSetThatSums(input.toBigIntegerList(), answer)
        .sumOfSmallestAndLargest()
    println("The answer to part 2 is ... $answer2")
}

// Part 2

private fun findContiguousSetThatSums(numbers: List<BigInteger>, sumTo: BigInteger): List<BigInteger> {
    var start = 0
    var end = 1
    var contiguousSet = numbers.subList(start, end)
    var currentSum = contiguousSet.sum()

    while (currentSum != sumTo) {
        if (currentSum > sumTo) {
            start += 1
        } else if (currentSum < sumTo) {
            end += 1
        } else {
            break
        }
        contiguousSet = numbers.subList(start, end)
        currentSum = contiguousSet.sum()
    }

    return contiguousSet
}

private fun List<BigInteger>.sum(): BigInteger =
    this.reduce { acc, bigInteger -> acc + bigInteger }

private fun List<BigInteger>.sumOfSmallestAndLargest(): BigInteger =
    (minOrNull() ?: BigInteger.ZERO) + (maxOrNull() ?: BigInteger.ZERO)

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