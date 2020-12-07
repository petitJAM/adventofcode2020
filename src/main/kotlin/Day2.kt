import util.readInputFile
import util.splitLast

@Suppress("unused")
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

    val answer2 = countValidPasswords(parsedInput)
    println("The answer to part 2 is ... $answer2")
}

// Part 2

private fun countValidPasswords(list: List<Pair<PasswordPolicy, Password>>): Int =
    list.count { valid2(it.second, it.first) }

private fun valid2(password: Password, passwordPolicy: PasswordPolicy): Boolean =
    (password[passwordPolicy.firstNum - 1] == passwordPolicy.letter)
        .xor(password[passwordPolicy.secondNum - 1] == passwordPolicy.letter)

// Part 1

private fun countInvalidPasswords(list: List<Pair<PasswordPolicy, Password>>): Int =
    list.count { valid(it.second, it.first) }

private fun valid(password: Password, passwordPolicy: PasswordPolicy): Boolean =
    password.count { it == passwordPolicy.letter }.let {
        passwordPolicy.firstNum <= it && it <= passwordPolicy.secondNum
    }

// Data parsing

typealias Password = String

data class PasswordPolicy(
    val letter: Char,
    val firstNum: Int,
    val secondNum: Int,
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
