import util.Input
import util.readInputFile
import util.splitLast

@Suppress("unused")
fun day7() {
    val input = readInputFile(7)
//    val input2 = Input(
//        """
//            light red bags contain 1 bright white bag, 2 muted yellow bags.
//            dark orange bags contain 3 bright white bags, 4 muted yellow bags.
//            bright white bags contain 1 shiny gold bag.
//            muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
//            shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
//            dark olive bags contain 3 faded blue bags, 4 dotted black bags.
//            vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
//            faded blue bags contain no other bags.
//            dotted black bags contain no other bags.
//    """.trimIndent()
//    )
//    val input3 = Input(
//        """
//            shiny gold bags contain 2 dark red bags.
//            dark red bags contain 2 dark orange bags.
//            dark orange bags contain 2 dark yellow bags.
//            dark yellow bags contain 2 dark green bags.
//            dark green bags contain 2 dark blue bags.
//            dark blue bags contain 2 dark violet bags.
//            dark violet bags contain no other bags.
//        """.trimIndent()
//    )

    val rules = parseInput(input)

    val shinyGoldBag: Bag = "shiny gold"

    val answer = countBagsThatEventuallyContain(rules, shinyGoldBag)
    println("The answer to part 1 is ... $answer")

    // Subtract 1 because we don't count the top level bag for this answer
    val answer2 = countTotalBags(rules, shinyGoldBag) - 1
    println("The answer to part 2 is ... $answer2")
}

// Part 2

@Suppress("SameParameterValue")
private fun countTotalBags(rules: List<Rule>, bag: Bag): Int {
    return if (bag == NO_OTHER_BAGS) {
        0
    } else {
        rules.first { it.bag == bag }
            .contains
            .map { (count, bag) ->
                count * countTotalBags(rules, bag)
            }
            .sum()
            .let {
                it + 1
            }
    }
}

// Part 1

@Suppress("SameParameterValue")
private fun countBagsThatEventuallyContain(rules: List<Rule>, bag: Bag): Int {
    return bagsThatCanContain(rules, bag).toSet().size
}

private fun bagsThatCanContain(rules: List<Rule>, bag: Bag): List<Bag> {
    val bagsThatDirectlyContainIt = rules
        .filter { rule ->
            rule.contains.map { it.bag }.contains(bag)
        }
        .map(Rule::bag)

    val bagsThatIndirectlyContainIt = if (bagsThatDirectlyContainIt.isNotEmpty()) {
        bagsThatDirectlyContainIt
            .flatMap { bagsThatCanContain(rules, it) }
    } else {
        emptyList()
    }

    return bagsThatDirectlyContainIt + bagsThatIndirectlyContainIt
}

// Data

private typealias Bag = String

private const val NO_OTHER_BAGS: Bag = "no other bags"

private typealias BagCount = Pair<Int, Bag>

private val BagCount.bag: Bag
    get() = second

private val BagCount.count: Int
    get() = first

private data class Rule(
    val bag: Bag,
    val contains: List<BagCount>,
) {

    val containsNoOtherBags: Boolean
        get() = contains.size == 1 && contains.first().bag == NO_OTHER_BAGS

    companion object {
        private val bagRegex = Regex("^(\\d+) (\\w+ \\w+) bags?\$")

        fun parse(str: String): Rule {
            val (bagStr, otherBagsCountStr) = str
                .removeSuffix(".")
                .splitLast(" contain ")

            val bag = bagStr.removeSuffix(" bags")

            val contains = otherBagsCountStr.split(", ")
                .map { bagCountStr ->
                    if (bagCountStr == NO_OTHER_BAGS) {
                        Pair(0, bagCountStr)
                    } else {
                        bagRegex.find(bagCountStr)?.groupValues?.let {
                            Pair(it[1].toInt(), it[2])
                        } ?: throw IllegalArgumentException("I don't know how to parse this: $bagCountStr")
                    }
                }

            return Rule(bag, contains)
        }
    }
}

private fun parseInput(input: Input): List<Rule> {
    return input.toList().map(Rule.Companion::parse)
}