import util.*
import java.math.BigInteger

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
    println()

    val input3 = Input(
        """
            0
            17,x,13,19
        """.trimIndent()
    )
    val input4 = Input(
        """
            0
            67,7,59,61
        """.trimIndent()
    )
    val input5 = Input(
        """
            0
            67,x,7,59,61
        """.trimIndent()
    )
    val input6 = Input(
        """
            0
            67,7,x,59,61
        """.trimIndent()
    )
    val input7 = Input(
        """
            0
            1789,37,47,1889
        """.trimIndent()
    )

    listOf(input2, input3, input4, input5, input6, input7)
        .forEachIndexed { index, input ->
            println("input${index + 2}")
            val list = parseInput3(input)
            print(list)
            print(" --- ")
            print(findEarliestTimestampMatchingOffsets(list))
            print("\n\n")

//            val pairs = parseInput2(input)
//            print(pairs)
//            print("  -----  ")
//            print(pairs.crt())
//            print("\n\n")
        }

    val answer2 = parseInput2(input).crt().let { (it.first % it.second).abs() }
    println("The answer to part 2 is ... $answer2")
}

// Part 2

// Thief! https://github.com/CodingNagger/advent-of-code-2020/blob/master/pkg/days/day13/computer.go
fun findEarliestTimestampMatchingOffsets(list: List<Int?>): BigInteger {
    var cursor = list.indexOfFirst { it != null }
    var timestamp = BigInteger.ZERO
    var increment = list[cursor]!!.toBigInteger()

    while (cursor < list.size) {
        val x = list[cursor]?.toBigInteger()
        if (x == null) {
            cursor++
            continue
        }

        timestamp += increment

        if ((timestamp + cursor.toBigInteger()) % x == BigInteger.ZERO) {
            increment = lcm(increment, x)
            cursor++
        }
    }

    return timestamp
}

private fun lcm(a: BigInteger, b: BigInteger): BigInteger {
    return (a * b).abs() / a.gcd(b)
}

private fun parseInput3(input: Input): List<Int?> =
    input.toList()
        .let { it[1].split(",") }
        .map(String::toIntOrNull)

/*
 *
 *
 *
 *
 *
 *
 *
 *
 */

// Old junk

fun List<Pair<BigInteger, BigInteger>>.crt(): Pair<BigInteger, BigInteger> = this
    .sortedByDescending { it.second }
    .reduce { (a, m), (nextA, nextM) ->
        Pair(
            m * ((((nextA - a) % nextM) * modInverse(m, nextM)) % nextM) + a,
            m * nextM
        ).alsoPrint()
    }

private fun modInverse(a: BigInteger, m: BigInteger): BigInteger =
    (BigInteger.ONE..m).firstOrNull { (a * it) % m == BigInteger.ONE }
        ?: throw NoSuchElementException("No modulo inverse for $a, $m")


/*
 *
 *
 *
 */

/*
 * (0, 7), (1, 13), (4, 59), (6, 31), (7, 19)
 *
 * (4, 59)
 * (6, 31)
 * (7, 19)
 * (1, 13)
 * (0, 7)
 *
 * x = 0 (mod 7)
 * x = 1 (mod 13)
 * x = 4 (mod 59)
 * x = 6 (mod 31)
 * x = 7 (mod 19)
 *
 * ~initial~
 *
 * x = 4 (mod 59)
 * x = 59j + 4                  // x = mj + a
 *
 * ~loop~
 *
 * 59j+4 = 6 (mod 31)
 * 59j = 2 (mod 31)             // MOD 31
 * 59j*10 = 2*10 (mod 31)
 * 590j = 20 (mod 31)           // MOD 31
 * j = 20 (mod 31)
 *
 * j = 31k + 20
 *
 * x = 59(31k + 20) + 4
 * x = 1829k + 1180 + 4
 * x = 1829k + 1184             // x = mk + a
 *
 * ~repeat~
 *
 * x = 7 (mod 19)
 * 1829k + 1184 = 7 (mod 19)
 * 1829k = 7 - 1184 (mod 19)
 * 1829k = -18 (mod 19)
 * 1829k * 4 = -18 * 4 (mod 19)
 * k = -15 (mod 19)
 *
 * k = 19l + -15
 *
 * x = 1829k + 1184
 * x = 1829(19l + -15) + 1184
 * x = 34751l + -26251
 *
 * ~repeat~
 *
 * x = 1 (mod 13)
 * 34751l + -26251 = 1 (mod 13)
 * 34751l = 26252 (mod 13)
 * 34751l = 5 (mod 13)
 * 34751l * 7 = 5 * 7 (mod 13)
 * l = 9 (mod 13)
 *
 * l = 13m + 9
 *
 * x = 34751(13m + 9) + -26251
 * x = 451763m + 312759 + -26251
 * x = 451763m + 286508
 *
 * ~repeat~
 *
 * x = 0 (mod 7)
 * 451763m + 286509 = 0 (mod 7)
 * 451763m = -286509 (mod 7)
 * 451763m = -6 (mod 7)
 * 451763m * 2 = -6 * 2 (mod 7)
 * m = -12 (mod 7)
 * m = -5 (mod 7)
 *
 * m = 7n + -5
 *
 * x = 451763(7n + -5) + 286508
 * x = 451763(7n + -5) + 286508
 * x = 3162341n + -1972307
 *
 * WRONG NUMBER
 * x = -1972307 (mod 3162341)
 */

/*
 * 7j + 6 = 4 (mod 5)
 * 7j = -2 (mod 5)
 *
 * 7 * 1 (mod 5) == 2
 * 7 * 2 (mod 5) == 4
 * 7 * 3 (mod 5) == 1
 *
 * 7j*3 = -2*3 (mod 5)
 * j = -6 (mod 5)
 * j = 4 (mod 5)
 *
 * j = 5k + 4
 *
 *
 * x = 7(5k + 4) + 6
 * x = 35k + 28 + 6
 * x = 35k + 34
 *
 *
 */

/*
 * Simplifying linear congruences:
 *
 * 5x + 2 = 4 (mod 7)
 * 5x = 2 (mod 7)
 *
 * 5 * 1 mod 7 == 5
 * 5 * 2 mod 7 == 3
 * 5 * 3 mod 7 == 1
 *
 * 5x*3 = 2*3 (mod 7)
 * x = 6 (mod 7)
 */

private fun parseInput2(input: Input): List<Pair<BigInteger, BigInteger>> =
    input.toList()
        .let {
            it[1].split(",")
                .mapIndexedNotNull { index, s ->
                    if (s == "x") null
                    else {
                        index.toBigInteger() to s.toBigInteger()
                    }
                }
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