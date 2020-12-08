import util.Input
import util.readInputFile
import util.splitLast

@Suppress("unused")
fun day8() {
    val input = readInputFile(8)
    val input2 = Input(
        """
            nop +0
            acc +1
            jmp +4
            acc +3
            jmp -3
            acc -99
            acc +1
            jmp -4
            acc +6
    """.trimIndent()
    )

    val instructions = parseInput(input)

    val answer = runUntilRepeatedInstruction(instructions)
    println("The answer to part 1 is ... $answer")

    val answer2 = -1
    println("The answer to part 2 is ... $answer2")
}

// Part 1

/**
 * Run the [instructions] until an instruction is repeated and return the accumulator value
 */
private fun runUntilRepeatedInstruction(instructions: List<Instruction>): Int {
    var accumulator = 0
    val visitedInstructionIndices = mutableSetOf<Int>()
    var currentPosition = 0

    while (currentPosition !in visitedInstructionIndices) {
        visitedInstructionIndices += currentPosition
        val instruction = instructions[currentPosition]
        when (instruction.operation) {
            Operation.ACC -> {
                accumulator += instruction.argument
                currentPosition += 1
            }
            Operation.JMP -> {
                currentPosition += instruction.argument
            }
            Operation.NOP -> currentPosition += 1
        }
    }

    return accumulator
}

// Data

private data class Instruction(
    val operation: Operation,
    val argument: Int,
)

private enum class Operation {
    ACC,
    JMP,
    NOP,
    ;
}

private fun parseInput(input: Input): List<Instruction> = input.toList()
    .map { it.splitLast(" ") }
    .map { (op, arg) ->
        Instruction(
            Operation.valueOf(op.toUpperCase()),
            arg.toInt()
        )
    }