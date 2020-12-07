import java.lang.reflect.Method
import java.lang.reflect.Modifier

fun main(args: Array<String>) {
    println("Hello Advent Of Code!")
    val arg1 = args.toList().firstOrNull()
    val day = arg1?.toIntOrNull()

    val dayFunctions = (1..25).map { functionForDay(it) }

    if (day != null) {
        val func = dayFunctions[day - 1]
        if (func != null) {
            println("Running Day $day")
            func.invoke(null)
        } else {
            println("That day isn't implemented yet! :(")
        }
    } else {
        val lastImplementedIndex = dayFunctions.filterNotNull().lastIndex
        val currentDayFunc = dayFunctions[lastImplementedIndex]
        if (currentDayFunc != null) {
            println("Running Day ${lastImplementedIndex + 1}")
            currentDayFunc.invoke(null)
        } else {
            println("Uh oh, there aren't any implemented days yet :(")
        }
    }
}

private fun functionForDay(day: Int): Method? {
    val fileName = "Day$day"
    val funcName = "day$day"
    val selfRef = ::functionForDay
    val currentClass = selfRef.javaClass
    val classDefiningFunctions = try {
        currentClass.classLoader.loadClass("${fileName}Kt")
    } catch (ex: ClassNotFoundException) {
        null
    }
    return classDefiningFunctions?.methods?.find { it.name == funcName && Modifier.isStatic(it.modifiers) }
}
