package day05

import java.nio.file.Files
import java.nio.file.Path

fun main() {

    println("*** Advent of Code ***")
    println("### Day 05: Print Queue ###")

    println("--- Part 1 ---")
    val input = Files.readAllLines(Path.of("2024", "day05", "input.txt"))


    //Parsing
    val (orderingMap, pageLists) = parse(input)
    println(orderingMap)
//    println(pageLists)

    //Analyzing
    var midSum = 0

    for (list in pageLists) {

        var valid = true

        for (i in list.indices) {
            val page = list[i]
            val nextSet = orderingMap[page] ?: continue

            for (next in nextSet) {
                val nextI = list.indexOf(next)
                if (nextI == -1) {
                    continue
                }

                valid = valid && nextI > i
            }
        }

        if (valid) {
            midSum += list[list.size / 2]
        }
    }

    println("The sum of middle page numbers in correctly ordered updates is $midSum")
    //5727 too high
}

fun analyze(list: List<Int>, index: Int, orderingMap: MutableMap<Int, MutableSet<Int>>): Boolean {
    val page = list[index]
    val nextSet = orderingMap[page] ?: return true

    for (next in nextSet) {
        if (list.indexOf(next) == -1) {
            continue
        }

        if (list.indexOf(next) < index) {
            return false
        }

        analyze(list, list.indexOf(next), orderingMap)
    }
    return true
}

fun parse(input: List<String>): Pair<MutableMap<Int, MutableSet<Int>>, MutableList<List<Int>>> {

    val orderingMap: MutableMap<Int, MutableSet<Int>> = mutableMapOf()
    val pageLists: MutableList<List<Int>> = mutableListOf()

    var halfInput = -1

    input.forEachIndexed{ i, line ->
        if (line.isEmpty()) {
            halfInput = i
            return@forEachIndexed
        }

        if (halfInput == -1) {
            val split = line.split('|')
            orderingMap.getOrPut(split[0].toInt()) { mutableSetOf() }.add(split[1].toInt())
        }

        if (halfInput > -1 && i > halfInput) {
            val prodList = line.split(',').map(String::toInt)
            pageLists.add(prodList)
        }
    }

    return orderingMap to pageLists
}
