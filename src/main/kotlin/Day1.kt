import util.head
import util.readInputFile
import util.tail

fun day1() {
    val input = readInputFile(1).toIntList()
//    val input = """
//        1721
//        979
//        366
//        299
//        675
//        1456
//    """.trimIndent().split("\n").map(String::toInt)

    val numsThatSum = find2NumbersThatSumTo2020(input)
    val answer = numsThatSum.first * numsThatSum.second
    println("The answer to part 1 is ... $answer")

    val numsThatSum2 = find3NumbersThatSumTo2020(input)
    val answer2 = numsThatSum2.first * numsThatSum2.second * numsThatSum2.third
    println("The answer to part 2 is ... $answer2")
}

// Part 2

private tailrec fun find3NumbersThatSumTo2020(list: List<Int>): Triple<Int, Int, Int> =
    findSumOrNot(list.head, list.tail) ?: find3NumbersThatSumTo2020(list.tail)

private tailrec fun findSumOrNot(x: Int, list: List<Int>): Triple<Int, Int, Int>? =
    if (list.isEmpty()) {
        null
    } else {
        list.tail.find { x + list.head + it == 2020 }
            ?.let { Triple(x, list.head, it) }
            ?: findSumOrNot(x, list.tail)
    }

// Part 1

private tailrec fun find2NumbersThatSumTo2020(list: List<Int>): Pair<Int, Int> =
    list.tail
        .find { it + list.head == 2020 }
        ?.let { Pair(list.head, it) }
        ?: find2NumbersThatSumTo2020(list.tail)
