package day04

import java.io.File

fun doRangesOverlap(range1: IntRange, range2: IntRange): Boolean {
    return range1.first <= range2.last && range2.first >= range1.last
}

fun main() {

    println("### Day 04: Camp Cleanup ###")
    println("--- Part 1 ---")

    var subsetSections = 0

    File("2022\\day04\\input.txt").forEachLine { line ->

        val pair = line.split(",").let {
            var rangeLimits = it[0].split("-")
            val firstRange = IntRange(rangeLimits[0].toInt(), rangeLimits[1].toInt())
            rangeLimits = it[1].split("-")
            val secondRange = IntRange(rangeLimits[0].toInt(), rangeLimits[1].toInt())

            firstRange to secondRange
        }

        //first contains second
        val fcs = pair.first.all { section -> pair.second.contains(section) }
        //second contains first
        val scf = pair.second.all { section -> pair.first.contains(section) }

        if (fcs || scf) {
            subsetSections++
        }
    }

    println("Number of section ranges fully contained by other section range of the pair: $subsetSections")

    println("--- Part 2 ---")

    val lines = File("2022\\day04\\input.txt").readLines()

    val decoupledRanges: List<IntRange> = List(lines.size * 2) { index ->
        val rangeString = lines[index/2].split(",")[index%2]
        val rangeLimits = rangeString.split("-")
        IntRange(rangeLimits[0].toInt(), rangeLimits[1].toInt())
    }

    var overlapsCount = 0
    decoupledRanges.forEach { range1 ->
        if (decoupledRanges.any { doRangesOverlap(range1, it) })
            overlapsCount++
    }

    println("Ranges overlapping overall: $overlapsCount")


}