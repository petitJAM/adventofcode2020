/*
 * Notes
 */

fun day1() {
//    val input = readInputFile(1).toIntList()
    val input = """
        979
        366
        299
        675
        1456
        1721
    """.trimIndent().split("\n").map(String::toInt)

    val numsThatSum = find2NumbersThatSumTo2020(input)
    val answer = numsThatSum.first * numsThatSum.second
    println("The answer to part 1 is ... $answer")
}

private tailrec fun find2NumbersThatSumTo2020(list: List<Int>): Pair<Int, Int> =
    list.tail
        .find { it + list.head == 2020 }
        ?.let { Pair(list.head, it) }
        ?: find2NumbersThatSumTo2020(list.tail)

private val <T> List<T>.head: T
    get() = first()

private val <T> List<T>.tail: List<T>
    get() = drop(1)
