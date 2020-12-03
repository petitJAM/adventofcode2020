import util.readInputFile

fun day3() {
    val input = readInputFile(3).toList()
    val input2 = """
        ..##.......
        #...#...#..
        .#....#..#.
        ..#.#...#.#
        .#...##..#.
        ..#.##.....
        .#.#.#....#
        .#........#
        #.##...#...
        #...##....#
        .#..#...#.#
    """.trimIndent()
        .split("\n")

    val treeMap = parseInput(input)

    val answer = countTreesOnPath(treeMap, Slope(3, 1))
    println("The answer to part 1 is ... $answer")

    val slopes = listOf(
        Slope(1, 1),
        Slope(3, 1),
        Slope(5, 1),
        Slope(7, 1),
        Slope(1, 2),
    )

    val counts = slopes
        .map { slope ->
            countTreesOnPath(treeMap, slope)
        }
        .map(Int::toBigInteger)

    println(counts)

    println(Int.MAX_VALUE)
    val answer2 = counts.reduce { acc, i -> println("acc $acc * i $i = ${acc * i}"); acc * i }
    println("The answer to part 2 is ... $answer2")
}

// Part 1+2

private fun countTreesOnPath(treeMap: TreeMap, slope: Slope): Int {
    val width = treeMap.first().size
    val height = treeMap.size

    println("Slope ${slope.first}, ${slope.second}")
    println("Width $width, Height $height")
    println()

    var treeCount = 0
    var currentX = 0
    var currentY = 0

    while (currentY < height) {
//        println("Checking $currentX, $currentY ... it's ${treeMap[currentY][currentX]}")
        println(treeMap[currentY].toString(currentX))

        if (slope.second > 1) {
            var yBetween = currentY + 1
            while (yBetween < currentY + slope.second && yBetween < height) {
                println(treeMap[yBetween].toString(-1))
                yBetween += 1
            }
        }

        if (treeMap[currentY][currentX] == Square.TREE) treeCount += 1
        currentX = (currentX + slope.first) % width
        currentY += slope.second
    }

    println()

    return treeCount
}

// Data

/**
 * Slope(x, y)
 *
 * Top left is 0,0.
 * Positive X is right.
 * Positive Y is down.
 */
private typealias Slope = Pair<Int, Int>

private typealias TreeMap = List<TreeMapRow>

private typealias TreeMapRow = List<Square>

private fun TreeMapRow.toString(tobboganAt: Int): String =
    this
        .mapIndexed { index, square ->
            if (index == tobboganAt) {
                when (square) {
                    Square.OPEN -> 'O'
                    Square.TREE -> 'X'
                }
            } else {
                when (square) {
                    Square.OPEN -> '.'
                    Square.TREE -> '#'
                }
            }
        }
        .joinToString("")

private enum class Square {
    OPEN,
    TREE,
    ;

    companion object {
        fun fromChar(c: Char): Square {
            return when (c) {
                '.' -> OPEN
                '#' -> TREE
                else -> throw IllegalArgumentException("Invalid character $c")
            }
        }
    }
}

private fun parseInput(input: List<String>): TreeMap {
    return input
        .map {
            it.toCharArray()
        }
        .map {
            it.map { c -> Square.fromChar(c) }
        }
}