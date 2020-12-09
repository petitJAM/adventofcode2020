package util

import java.io.File
import java.math.BigInteger

inline class Input(val inputStr: String) {

    fun toList(): List<String> {
        return inputStr.split("\n")
    }

    fun toGroupList(): List<String> {
        return inputStr.split("\n\n")
    }

    fun toIntList(): List<Int> {
        return toList().map(String::toInt)
    }

    fun toBigIntegerList(): List<BigInteger> {
        return toList().map(String::toBigInteger)
    }
}

fun readInputFile(day: Int): Input = readInputFile("inputs/day$day.txt")

private fun readInputFile(fileName: String): Input {
    val resourceFile = File(ClassLoader.getSystemResource(fileName).file)
    return Input(resourceFile.readText().trim())
}