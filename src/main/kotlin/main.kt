fun main(args: Array<String>) {
    println("Hello Advent Of Code!")
    val arg1 = args.toList().first()
    val day = arg1.toIntOrNull()

    if (day != null) {
        println("You chose day $day")
    } else {
        println("Uh oh, \"$arg1\" is not a day >:(")
    }
}