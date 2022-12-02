package day02

import java.io.File
import java.util.Scanner

fun main() {

    val scanner = Scanner(File("2022\\day02\\input.txt"))

    val matchResMatrix = arrayOf(
        intArrayOf(3, 6, 0),
        intArrayOf(0, 3, 6),
        intArrayOf(6, 0, 3)
    )

    var accumulator = 0

    while (scanner.hasNext()) {
        val game = scanner.nextLine()

        var score = 0

        //Game results -> First and Third characters of line
        //Convert directly to indices by subtracting the relative offset letter
        val gameResults = Pair(game[0] - 'A', game[2] - 'X')

        //Match result via matrix
        val matchRes = matchResMatrix[gameResults.first][gameResults.second]
        //total score: match score + my shape score [directly based on the index]
        score += matchRes + (gameResults.second + 1)
        accumulator += score
    }

    println("--- Part 1 ---")
    println("Total Score: $accumulator")

    accumulator = 0

    File("2022\\day02\\input.txt").forEachLine {

        //0=Rock | 1=Paper | 2=Scissor
        val shape = it[0] - 'A'
        //0=Lost | 1=Draw | 2=Win
        val outcome = (it[2] - 'X')

        //outcome+2 = offset that takes you to the correct counter to achieve the desired outcome (thanks @PierKnight)
        val counterShape = (shape + (outcome+2)) % 3

        // outcome*3 = amount of points per game
        val score = (counterShape + 1) + (outcome*3)
        accumulator += score
    }

    println("--- Part 2 ---")
    println("Total Score: $accumulator")

}
