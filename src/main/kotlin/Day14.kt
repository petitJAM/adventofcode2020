import util.Input
import util.TODO
import util.readInputFile
import util.sum
import java.lang.Integer.parseInt
import java.math.BigInteger
import kotlin.math.pow

@Suppress("unused")
fun day14() {
    val input = readInputFile(14)
    val input2 = Input(
        """
            mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
            mem[8] = 11
            mem[7] = 101
            mem[8] = 0
        """.trimIndent()
    )

    val program = parseInput(input2)
    println(program.run())

    val answer = parseInput(input).run().values.sum()
    println("The answer to part 1 is ... $answer")

    val answer2 = parseInput(input).runV2().values.sum()
    println("The answer to part 2 is ... $answer2")
}

// Part 2

private fun Program.runV2(): Map<BigInteger, BigInteger> {
    val memory = mutableMapOf<BigInteger, BigInteger>()
    var currentMask = Bitmask.DEFAULT

    forEach { command ->
        when (command) {
            is Command.WriteBitmask -> currentMask = command.bitmask
            is Command.WriteMemory -> {
                val address = command.address.toBigInteger()
                val floatingAddress = address.toBitString().zip(currentMask.str)
                    .map { (real, mask) ->
                        when (mask) {
                            '0' -> real
                            '1' -> '1'
                            'X' -> 'X'
                            else -> throw IllegalStateException("Bitmask contains unexpected char $mask")
                        }
                    }
                    .joinToString("")
                val floatingCount = floatingAddress.count { it == 'X' }
                generateFloatingPermutations(floatingCount)
                    .map { floatingValues ->
                        // TODO: for each floating value, replace the first X with the floating value
                    }

                TODO()
            }
        }
    }

    return memory
}

private fun generateFloatingPermutations(n: Int): List<String> =
    (0..2f.pow(n).toInt())
        .map { it.toString(2) }
        .let { list ->
            val max = list.map { it.length }.maxOrNull() ?: 0
            list.map { it.padStart(max, '0') }
        }

// Part 1

private fun Program.run(): Map<Int, BigInteger> {
    val memory = mutableMapOf<Int, BigInteger>()
    var currentMask = Bitmask.DEFAULT

    forEach { command ->
        when (command) {
            is Command.WriteBitmask -> {
                currentMask = command.bitmask
            }
            is Command.WriteMemory -> {
                memory[command.address] = currentMask.apply(command.value)
            }
        }

        // println("Program state:")
        // println("Mask: $currentMask")
        // println("Memory: $memory")
        // println()
    }

    return memory
}

// Data

private sealed class Command {

    data class WriteBitmask(
        val bitmask: Bitmask,
    ) : Command()

    data class WriteMemory(
        val address: Int,
        val value: BigInteger,
    ) : Command()

    companion object {
        private val bitmaskRegex = Regex("^mask = ([X01]{36})$")
        private val memRegex = Regex("^mem\\[(\\d+)] = (\\d+)$")

        fun fromString(str: String): Command = when {
            str.matches(bitmaskRegex) -> {
                parseWriteBitmask(str)
            }
            str.matches(memRegex) -> {
                parseWriteMemory(str)
            }
            else -> {
                throw IllegalArgumentException("String does not match either regex $str")
            }
        }

        private fun parseWriteBitmask(str: String): WriteBitmask =
            bitmaskRegex.find(str)?.let { matchResult ->
                WriteBitmask(
                    Bitmask(matchResult.groupValues[1])
                )
            } ?: throw IllegalArgumentException("No WriteBitmask matches found in $str")

        private fun parseWriteMemory(str: String): WriteMemory =
            memRegex.find(str)?.let { matchResult ->
                WriteMemory(
                    matchResult.groupValues[1].toInt(),
                    matchResult.groupValues[2].toBigInteger(),
                )
            } ?: throw IllegalArgumentException("No WriteMemory matches found in $str")
    }
}

inline class Bitmask(val str: String) {

    companion object {
        val DEFAULT = Bitmask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    }

    fun apply(int: BigInteger): BigInteger =
        int.toBitString().zip(str)
            .map { (input, mask) ->
                if (mask == 'X') {
                    input
                } else {
                    mask
                }
            }
            .joinToString("")
            .let {
                BigInteger(it, 2)
            }
}

private fun BigInteger.toBitString(): String = toString(2).padStart(36, '0')

private typealias Program = List<Command>

private fun parseInput(input: Input): Program =
    input.toList()
        .map {
            Command.fromString(it)
        }