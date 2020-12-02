package util

val <T> List<T>.head: T
    get() = first()

val <T> List<T>.tail: List<T>
    get() = drop(1)

fun String.splitLast(delimiter: String): Pair<String, String> =
    lastIndexOf(delimiter).let { index ->
        substring(0, index) to substring(index + 1)
    }