package day05

import java.nio.file.Files
import java.nio.file.Path

typealias OrderingRules = MutableMap<Int, MutableSet<Int>>

fun main() {

    println("*** Advent of Code ***")
    println("### Day 05: Print Queue ###")

    println("--- Part 1 ---")
    val input = Files.readAllLines(Path.of("2024", "day05", "input.txt"))


    //Parsing
    val (orderingRules, pageLists) = parse(input)
//    println(orderingMap)
//    println(pageLists)

    //Analyzing
    var validMidSum = 0
    val invalidPageLists: MutableList<List<Int>> = mutableListOf();

    for (list in pageLists) {
        if (validatePageList(list, orderingRules)) {
            validMidSum += list[list.size / 2]
        }
        else {
            invalidPageLists.add(list)
        }
    }

    println("The sum of middle page numbers in correctly ordered updates is $validMidSum")

    println("--- Part 2 ---")

    val comparator: Comparator<Int> = Comparator { i, i2 ->
        if (i == i2 || orderingRules[i] == null || orderingRules[i2] == null) {
            return@Comparator 0
        }
        else if (orderingRules[i]!!.contains(i2)) {
            return@Comparator -1
        }
        else if (orderingRules[i2]!!.contains(i)) {
            return@Comparator 1
        }
        else {
            return@Comparator 0
        }
    }

    var correctedMidSum = 0

    for (list in invalidPageLists) {
        val sorted = list.sortedWith(comparator)
        //inefficient but explicit alternative
        //val sorted = correctPageList(list, orderingRules)

        correctedMidSum += sorted[sorted.size / 2]
    }

    println("The sum of middle page numbers in corrected updates is $correctedMidSum")
    //correct: 5466
}

fun correctPageList(original: List<Int>, orderingRules: OrderingRules): List<Int> {
    val list = original.toMutableList()
    repeat(list.size) {
        for (i in list.indices) {
            var page = list[i]
            val nextSet = orderingRules[page] ?: continue

            for (next in nextSet) {
                //update page value
                page = list[i]

                val nextI = list.indexOf(next)
                //no occurrence
                if (nextI == -1) {
                    continue
                }

                //swap
                if (nextI < i) {
                    list[i] = next
                    list[nextI] = page
                }
            }
        }
    }
    return list
}

fun validatePageList(list: List<Int>, orderingRules: OrderingRules): Boolean {
    var valid = true

    for (i in list.indices) {
        val page = list[i]
        val nextSet = orderingRules[page] ?: continue

        for (next in nextSet) {
            val nextI = list.indexOf(next)
            if (nextI == -1) {
                continue
            }

            valid = valid && nextI > i
        }
    }

    return valid
}

fun parse(input: List<String>): Pair<OrderingRules, MutableList<List<Int>>> {

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
