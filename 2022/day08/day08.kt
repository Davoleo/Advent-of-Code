package day08

import java.io.File

const val MATRIX_SIZE = 99

val visibilityCache = BooleanArray(MATRIX_SIZE* MATRIX_SIZE)
val matrix = Array(MATRIX_SIZE) {_ -> IntArray(MATRIX_SIZE) }

fun testTreeVisibility(row: Int, col: Int, num: Int, horizontalMaxCache: IntArray, verticalMaxCache: IntArray) {
    var overMax = false

    //Test Horizontal Visibility
    if (horizontalMaxCache[row] < num) {
        horizontalMaxCache[row] = num
        overMax = true
    }

    //Test Top Visibility
    if (verticalMaxCache[col] < num) {
        verticalMaxCache[col] = num
        overMax = true
    }
    if (overMax)
        visibilityCache[row*MATRIX_SIZE + col] = true
}

fun treeScenicScore(row: Int, col: Int, tree: Int): Int {

    var scenicScore = 1

    //Left Scenic Score
    var x = col
    var treeCount = 0
    while (x > 0) {
        x--
        treeCount++
        if (matrix[row][x] >= tree)
            break
    }
    scenicScore *= treeCount

    //Right Scenic Score
    x = col
    treeCount = 0
    while (x < MATRIX_SIZE-1) {
        x++
        treeCount++
        if (matrix[row][x] >= tree)
            break
    }
    scenicScore *= treeCount


    var y = row
    treeCount = 0
    while (y > 0) {
        y--
        treeCount++
        if (matrix[y][col] >= tree)
            break
    }
    scenicScore *= treeCount

    y = row
    treeCount = 0
    while (y < MATRIX_SIZE-1) {
        y++
        treeCount++
        if (matrix[y][col] >= tree)
            break
    }
    scenicScore *= treeCount

    return scenicScore
}

fun main() {

    println("** Advent of Code 2022 **")
    println("### Day 08: Treetop Tree House ###")

    val lines = File("2022\\day08\\input.txt").readLines()
    //All the trees at the sides of the matrix are always visible

    lines.forEachIndexed { row, line -> line.forEachIndexed { col, char -> matrix[row][col] = char.code - '0'.code } }

    val leftHighest = IntArray(MATRIX_SIZE) { i -> lines[i][0].code - '0'.code }
    val topHighest = IntArray(MATRIX_SIZE) { i -> lines[0][i].code - '0'.code }

    val rightHighest = IntArray(MATRIX_SIZE) { i ->
        val line = lines[i]
        line[line.lastIndex].code - '0'.code
    }
    val bottomHighest = IntArray(MATRIX_SIZE) { i -> lines[lines.lastIndex][i].code - '0'.code }

    var visibleTrees = MATRIX_SIZE*4 - 4

    println("--- Part 1 ---")

    for (row in 1 until MATRIX_SIZE-1) {
        for (col in 1 until MATRIX_SIZE-1) {
            val num = lines[row][col].code - '0'.code
            testTreeVisibility(row, col, num, leftHighest, topHighest)

            val rrow = MATRIX_SIZE-1-row
            val rcol = MATRIX_SIZE-1-col
            val rnum = lines[rrow][rcol].code - '0'.code
            testTreeVisibility(rrow, rcol, rnum, rightHighest, bottomHighest)
        }
    }

    visibleTrees += visibilityCache.count { it }
    println("Number of visible trees: $visibleTrees")

    println("--- Part 2 ---")

    var maxScenicScore = 0
    for (row in 0 until MATRIX_SIZE) {
        for (col in 0 until MATRIX_SIZE) {

            val tree = matrix[row][col]
            val scenicScore = treeScenicScore(row, col, tree)
            if (scenicScore > maxScenicScore)
                maxScenicScore = scenicScore
        }
    }


    println("Highest Scenic Score Tree: $maxScenicScore")
}
