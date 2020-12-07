package util

/**
 * The first element of the list.
 */
val <T> List<T>.head: T
    get() = first()

/**
 * Everything except the first element of the list.
 */
val <T> List<T>.tail: List<T>
    get() = drop(1)

/**
 * Split this [String] on the last occurrence of [delimiter].
 */
fun String.splitLast(delimiter: String): Pair<String, String> =
    lastIndexOf(delimiter).let { index ->
        substring(0, index) to substring(index + delimiter.length)
    }

fun <T> printList(list: List<T>) {
    println("[")
    list.forEach {
        println("  $it")
    }
    println("]")
}