import util.readInputFile

fun day2() {
    val input = readInputFile(2).toList()
    val input2 = """
        1-3 a: abcde
        1-3 b: cdefg
        2-9 c: ccccccccc
    """.trimIndent()
        .split("\n")

    val parsedInput = parseInput(input)

    val answer = countInvalidPasswords(parsedInput)
    println("The answer to part 1 is ... $answer")

    /**/

    println("The answer to part 2 is ... TODO")
}

// Part 1

private fun countInvalidPasswords(list: List<Pair<PasswordPolicy, Password>>): Int =
    list.count { valid(it.second, it.first) }

private fun valid(password: Password, passwordPolicy: PasswordPolicy): Boolean =
    password.count { it == passwordPolicy.letter }.let {
        passwordPolicy.minimum <= it && it <= passwordPolicy.maximum
    }

// Data parsing

typealias Password = String

data class PasswordPolicy(
    val letter: Char,
    val minimum: Int,
    val maximum: Int,
) {
    companion object {
        fun fromInputString(inputStr: String): PasswordPolicy {
            val (minMax, char) = inputStr.splitLast(" ")
            val (min, max) = minMax.splitLast("-").let {
                Pair(it.first.toInt(), it.second.toInt())
            }
            return PasswordPolicy(
                char.toCharArray().first(),
                min,
                max
            )
        }
    }
}

private fun parseInput(input: List<String>): List<Pair<PasswordPolicy, Password>> =
    input
        .map { line ->
            line.splitLast(":")
        }
        .map {
            Pair(it.first, it.second.trim())
        }
        .map { it: Pair<String, Password> ->
            Pair(PasswordPolicy.fromInputString(it.first), it.second)
        }

private fun String.splitLast(delimiter: String): Pair<String, String> =
    lastIndexOf(delimiter).let { index ->
        substring(0, index) to substring(index + 1)
    }