import util.Input
import util.readInputFile

@Suppress("unused")
fun day6() {
    val input = readInputFile(6)
//    val input2 = Input(
//        """
//            abc
//
//            a
//            b
//            c
//
//            ab
//            ac
//
//            a
//            a
//            a
//            a
//
//            b
//    """.trimIndent()
//    )

    val groups = parseInput(input)

    val yesAnswersPerGroup = groups
        .map { group ->
            group.flatMap { it.toCharArray().toList() }
        }
        .map { it.toSet() }
        .map { it.size }

    val answer = yesAnswersPerGroup.sum()
    println("The answer to part 1 is ... $answer")

    val numberOfEveryoneYesAnswerPerGroup = groups.map(Group::countEveryoneYes)

    val answer2 = numberOfEveryoneYesAnswerPerGroup.sum()
    println("The answer to part 2 is ... $answer2")
}

// Data

private typealias Group = List<String>

private fun Group.countEveryoneYes(): Int = this
    .map { answers ->
        answers.toCharArray().toList()
    }
    .reduce { acc, list ->
        acc.intersect(list).toList()
    }
    .size

private fun parseInput(input: Input): List<Group> =
    input.toGroupList()
        .map {
            it.split("\n")
        }