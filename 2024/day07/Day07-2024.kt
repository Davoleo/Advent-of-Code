package day07

import java.nio.file.Files
import java.nio.file.Path
import java.util.function.BinaryOperator

enum class Operator(val symbol: Char, private val func: BinaryOperator<Long>) {
    ADD('+', Long::plus),
    MULTIPLY('*', Long::times),
    ;

    operator fun invoke(a: Long, b: Long): Long {
        return this.func.apply(a, b)
    }
}

fun main() {
    println("*** Advent of Code ***")
    println("### Day 07: Bridge Repair ###")

    println("--- Part 1 ---")

    val input = Files.readAllLines(Path.of("2024", "day07", "input.txt"))

    var trueAcc: Long = 0
    for (equation in input) {
        val (result, inputs) = equation.split(':')
        val operands = inputs.trim().split(' ').map(String::toInt)

        val resultLong = result.toLong()

        val valid = isEqValid(resultLong, operands.first().toLong(), 1, operands)
        if (valid) {
            trueAcc += resultLong
        }
    }

    println("The total calibration result is $trueAcc")
}

fun isEqValid(testValue: Long, accumulator: Long, i: Int, operands: List<Int>): Boolean {
    if (i < operands.size) {
        var isValid = false
        for (op in Operator.entries) {
            val newVal = op(accumulator, operands[i].toLong())
            isValid = isValid || isEqValid(testValue, newVal, i+1, operands)
        }
        return isValid
    }
    else {
        return testValue == accumulator
    }
}