package day08

import java.io.File

const val MATRIX_SIZE = 99

private fun BooleanArray.set(indices: IntRange, value: Boolean) {
    for (i in indices)
        this[i] = value
}

fun main() {

    println("** Advent of Code 2022 **")
    println("### Day 08: Treetop Tree House ###")

    val lines = File("2022\\day08\\input.txt").readLines()
    //All the trees at the sides of the matrix are always visible

    val leftHighest = IntArray(MATRIX_SIZE) { _ -> 0}
    val rightHighest = IntArray(MATRIX_SIZE) { _ -> 0}

    val topHighest = IntArray(MATRIX_SIZE) { _ -> 0}
    val bottomVisible = Array(MATRIX_SIZE) { _ -> BooleanArray(10) }

    var visibleTrees = MATRIX_SIZE*4 - 4

    println("--- Part 1 ---")

    lines.forEachIndexed { row, line ->

        if (row in 1 until MATRIX_SIZE-1) {
            for (col in 1 until line.length-1) {

                //Test Left Visibility
                val numLeft = line[col].code - '0'.code
                if (leftHighest[row] < numLeft) {
                    leftHighest[row] = numLeft
                    ++visibleTrees
                    continue
                }

                //Test Top Visibility
                if (topHighest[col] < numLeft) {
                    topHighest[col] = numLeft
                    ++visibleTrees
                    continue
                }

                //Test Right Visibility
                val numRight = line[line.length - col].code - '0'.code
                if (rightHighest[row] < numRight) {
                    rightHighest[row] = numRight
                    ++visibleTrees
                    continue
                }

                val flags = bottomVisible[col]
                if (!flags[numLeft]) {
                    flags[numLeft] = true
                    flags.set(0 until numLeft, false)
                }

            }
        }

    }

    for (treeCol in bottomVisible)
        visibleTrees += treeCol.count { it }

    println("Number of visible trees: $visibleTrees")
}
