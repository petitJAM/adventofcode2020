import java.io.File

fun main(args: Array<String>) {
    println("Hello Advent Of Code!")
    val arg1 = args.toList().firstOrNull()
    val day = arg1?.toIntOrNull() ?: 6

    if (day != null) {
        println()
        println("Running Day $day")

        when (day) {
            1 -> day1()
            2 -> day2()
            3 -> day3()
            4 -> day4()
            5 -> day5()
            6 -> day6()
            else -> println("That day isn't implemented yet! :(")
        }

    } else {
        println("Uh oh, \"$arg1\" is not a day >:(")
    }
}
