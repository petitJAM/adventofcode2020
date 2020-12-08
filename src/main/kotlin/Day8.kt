import util.Input
import util.readInputFile
import util.splitLast

@Suppress("unused")
fun day8() {
    val input = readInputFile(8)
//    val input2 = Input(
//        """
//            nop +0
//            acc +1
//            jmp +4
//            acc +3
//            jmp -3
//            acc -99
//            acc +1
//            jmp -4
//            acc +6
//    """.trimIndent()
//    )

    val instructions = parseInput(input)

    val answer = runUntilRepeatedInstruction(instructions)
    println("The answer to part 1 is ... $answer")

    val jmpNopIndices = instructions.mapIndexedNotNull { index, instruction ->
        if (instruction.operation == Operation.JMP || instruction.operation == Operation.NOP) {
            index
        } else {
            null
        }
    }

    val instructionVariants = jmpNopIndices.map { index ->
        instructions
            .toMutableList()
            .apply {
                val instruction = removeAt(index)
                val newOp = when (instruction.operation) {
                    Operation.JMP -> Operation.NOP
                    Operation.NOP -> Operation.JMP
                    Operation.ACC -> throw IllegalStateException("Should not be trying to replace an ACC operation")
                }
                add(index, instruction.copy(operation = newOp))
            }
            .toList()
    }

    val results = instructionVariants.mapNotNull { instructions ->
        runUntilRepeatedOrCompleted(instructions)
    }

    val answer2 = results.first()
    println("The answer to part 2 is ... $answer2")
}

// Part 2

/**
 * @return null if the program hit a repeated instruction or accumulator if the program completed successfully
 */
private fun runUntilRepeatedOrCompleted(instructions: List<Instruction>): Int? {
    var accumulator = 0
    val visitedInstructionIndices = mutableSetOf<Int>()
    var currentPosition = 0

    while (currentPosition !in visitedInstructionIndices && currentPosition < instructions.size) {
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

    return if (currentPosition > instructions.lastIndex) {
        accumulator
    } else {
        null
    }
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