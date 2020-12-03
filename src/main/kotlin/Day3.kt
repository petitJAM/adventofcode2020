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

    println(input2.joinToString("\n"))

    val treeMap = parseInput(input)

    val answer = countTreesOnPath(treeMap, Slope(3, 1))
    println("The answer to part 1 is ... $answer")

    val answer2 = -1
    println("The answer to part 2 is ... $answer2")
}

// Part 1

private fun countTreesOnPath(treeMap: TreeMap, slope: Slope): Int {
    val width = treeMap.first().size

    var treeCount = 0
    var currentX = 0
    var currentY = 0

    treeMap.forEach { row ->
        if (row[currentX] == Square.TREE) treeCount += 1
        currentX = (currentX + slope.first) % width
        currentY += 1
    }

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