package day01

import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.abs

fun main() {

    println("--- Part 1 ---")

    val lines = Files.readAllLines(Path.of("2024", "day01", "input.txt"))
    val leftArr = Array(size = lines.size, init = { _ -> -1 })
    val rightArr = Array(size = lines.size, init = { _ -> -1 })

    //Parsing and initializing
    lines.forEachIndexed { i, line ->
        val pair = line.split(Regex("\\s+"))
        assert(pair.size == 2)

        leftArr[i] = pair.first().toInt();
        rightArr[i] = pair.last().toInt();
    }

    //Sorting
    leftArr.sort();
    rightArr.sort();

    assert(leftArr.first() > -1 && rightArr.first() > -1)

    var accumulator = 0;
    for (i in leftArr.indices) {
        val dist = abs(leftArr[i] - rightArr[i])
        accumulator += dist;
    }

    println("Accumulated distance between lists: $accumulator")
    println("--- Part 2 ---")

    var listSimilarity = 0
    leftArr.forEach { num ->
        val occurrences = rightArr.count { item -> item == num }
        val similarity = num * occurrences;
        listSimilarity += similarity;
    }

    println("The total list similarity is: $listSimilarity")
}