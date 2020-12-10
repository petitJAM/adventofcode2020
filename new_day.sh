#!/bin/bash

DAY_NUM=$1

echo "Creating day $DAY_NUM"

touch "src/main/resources/inputs/day$DAY_NUM.txt"

KT_FILE="src/main/kotlin/Day$DAY_NUM.kt"

if [[ ! -f "$KT_FILE" ]]; then
  cat <<KT >> $KT_FILE
import util.Input
import util.readInputFile

@Suppress("unused")
fun day$DAY_NUM() {
    val input = readInputFile($DAY_NUM)
    val input2 = Input(
        """
        """.trimIndent()
    )

    val answer = -1
    println("The answer to part 1 is ... \$answer")

    val answer2 = -1
    println("The answer to part 2 is ... \$answer2")
}
KT
fi
