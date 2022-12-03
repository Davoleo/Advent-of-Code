package day03

import java.io.File
import java.util.BitSet

//Builds priority [0-indexed]
fun priorityOf(type: Char): Int {
    return if (type.isUpperCase())
        type - 'A' + 26
    else
        type - 'a'
}

fun main() {

    println("### AoC 2022 | Day 03 ###")

    println("--- Part 1 ---")

    File("2022\\day03\\input.txt").useLines { rucksacks ->

        val priorities = rucksacks.map { rucksack ->
            val charOccurred = BitSet(52)

            val compartment1 = rucksack.substring(0, rucksack.length/2)
            val compartment2 = rucksack.substring(rucksack.length/2, rucksack.length)

            repeat(rucksack.length/2) { index ->
                val type = compartment1[index]
                charOccurred[priorityOf(type)] = true
            }

            repeat(rucksack.length/2) { index ->
                val type = compartment2[index]
                val priority = priorityOf(type)
                if (charOccurred[priority]) {
                    return@map priority+1
                }
            }

            assert(false)
            return@map 0
        }.sum()

        println("Sum of double item type Priorities: $priorities")
    }

    println("--- Part 2 ---")
    File("2022\\day03\\input.txt").useLines { rucksacks ->

        var badgesPrioritySum = 0

        rucksacks.chunked(3).forEach { group ->
            //Very lazy, I know, although the Part 1 solution doesn't scale as fine in readability
            val badge = group[0].toSet().intersect(group[1].toSet()).intersect(group[2].toSet()).first()
            badgesPrioritySum += (priorityOf(badge) + 1)
        }

        println("Sum of all badges priorities: $badgesPrioritySum")
    }

}