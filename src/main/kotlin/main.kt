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
