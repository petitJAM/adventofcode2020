import java.io.File

fun main(args: Array<String>) {
    println("Hello Advent Of Code!")
    val arg1 = args.toList().firstOrNull()
    val day = arg1?.toIntOrNull() ?: 1

    if (day != null) {
        println()
        println("Running Day $day")

        when (day) {
            1 -> day1()
            else -> println("That day isn't implemented yet! :(")
        }

    } else {
        println("Uh oh, \"$arg1\" is not a day >:(")
    }
}

inline class Input(val inputStr: String) {

    fun toList(): List<String> {
        return inputStr.split("\n")
    }

    fun toIntList(): List<Int> {
        return toList().map(String::toInt)
    }
}

fun readInputFile(day: Int) = readInputFile("inputs/day$day.txt")

private fun readInputFile(fileName: String): Input {
    val resourceFile = File(ClassLoader.getSystemResource(fileName).file)
    return Input(resourceFile.readText().trim())
}