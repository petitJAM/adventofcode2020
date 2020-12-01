package util

import java.io.File

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