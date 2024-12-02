package day02

import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.abs

fun main() {

    println("--- Part 1 ---")

    val lines = Files.readAllLines(Path.of("2024", "day02", "input.txt"))

    var safeReports = 0

    for (line in lines) {
        val report = line.split(" ").map(String::toInt)
        if (analyzeReport(report))
            safeReports++
    }

    println("The number of safe reports is: $safeReports")
    println("--- Part 2 ---")

    var semiSafeReports = 0
    for (line in lines) {
        val report = line.split(" ").map(String::toInt)
        if (analyzeReportWProblemDampener(report)) {
            semiSafeReports++
        }
        else {
            println("Report $line is unsafe")
        }
    }

    println("The number of safe reports with Problem Dampener is: $semiSafeReports")

}

fun analyzeReport(report: List<Int>): Boolean {
    var crescent: Boolean? = null
    for (i in 1 until report.size) {
        //Set direction
        if (crescent == null) {
            crescent = report[i-1] < report[i]
        } //Test direction
        else if (crescent != (report[i-1] < report[i])) {
            return false
        }

        val diff = abs(report[i] - report[i-1])

        if (diff < 1 || diff > 3) {
            return false
        }
    }
    return true
}

@Suppress("t")
fun analyzeReportWProblemDampener(pReport: List<Int>, dampenedLevel: Int = -1): Boolean {
    var crescent: Boolean? = null
    val report: List<Int> = if (dampenedLevel > -1)
        pReport.slice(pReport.indices.minus(dampenedLevel))
    else
        pReport

    for (i in 1 until report.size) {
        //Set direction
        if (crescent == null) {
            crescent = report[i-1] < report[i]
        } //Test direction
        else if (crescent != (report[i-1] < report[i])) {
            //first unsafe level -> retry without it
            return if (dampenedLevel == -1) {
                //Always test dampening first index, to see if the assumed order was wrong
                analyzeReportWProblemDampener(report, 0) ||
                analyzeReportWProblemDampener(report, i-1) ||
                        analyzeReportWProblemDampener(report, i)
            } else false
        }

        val diff = abs(report[i] - report[i-1])

        if (diff < 1 || diff > 3) {
            //first unsafe level -> save it
            return if (dampenedLevel == -1) {
                //Always test dampening first index, to see if the assumed order was wrong
                analyzeReportWProblemDampener(report, 0) ||
                analyzeReportWProblemDampener(report, i-1) ||
                        analyzeReportWProblemDampener(report, i)
            } else false
        }
    }

    if (dampenedLevel > -1) {
        println("report [${pReport.joinToString(" ")}] was dampened and fixed via omitting ${pReport[dampenedLevel]}")
    }
    else {
        println("Report [${pReport.joinToString(" ")}] is safe as is")
    }

    return true
}