package day04

import java.io.File

fun main() {

    println("### Day 04: Camp Cleanup ###")
    println("--- Part 1 ---")

    var subsetSections = 0
    var overlappingSections = 0

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

        if (pair.first.intersect(pair.second).isNotEmpty()) {
            overlappingSections++
        }
    }

    println("Number of section ranges fully contained by other section range of the pair: $subsetSections")

    println("--- Part 2 ---")

    println("Ranges overlapping overall: $overlappingSections")


}