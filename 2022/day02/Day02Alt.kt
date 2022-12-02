package day02

import java.io.File
import java.util.*


fun getHandNumber(x: String): Int
{
    when (x) {
        "X", "A" -> return 0
        "Y", "B" -> return 1
        "Z", "C" -> return 2
    }
    return 0
}

fun main()
{

    val shouldPlayMatrix = arrayOf(intArrayOf(2, 0, 1), intArrayOf(0, 1, 2), intArrayOf(1, 2, 0))

    val matchMatrix = arrayOf(intArrayOf(3, 6, 0), intArrayOf(0, 3, 6), intArrayOf(6, 0, 3))
    val arrayHand = arrayOf(1, 2, 3)
    val scanner = Scanner(File("2022\\day02\\input_alt.txt"))

    var part1Points = 0
    var part2Points = 0

    while (scanner.hasNext()) {
        val round = scanner.nextLine()

        val lineArray = round.split(" ")

        val enemyHand = getHandNumber(lineArray[0])
        val part1Hand = getHandNumber(lineArray[1])
        val part2Hand = shouldPlayMatrix[enemyHand][getHandNumber(lineArray[1])]


        //other way to determine my hand:
        //this is possible since the number next the enemy makes the player win
        //example if the enemy has rock which is 0, to win you play 0 + 1 (paper), to lose (0 - 1) % 3
        //val myPlayHand = ((round[2] - 'X' + 2) + (round[0] - 'A')) % 3

        part1Points += matchMatrix[enemyHand][part1Hand] + arrayHand[part1Hand]
        part2Points += matchMatrix[enemyHand][part2Hand] + arrayHand[part2Hand]
    }

    println("Part 1: $part1Points")
    println("Part 2: $part2Points")
}



