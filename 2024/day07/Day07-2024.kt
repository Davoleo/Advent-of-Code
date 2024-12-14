package day07

import java.nio.file.Files
import java.nio.file.Path
import java.util.function.BinaryOperator

enum class Operator(val symbol: String, private val func: BinaryOperator<Long>) {
    ADD("+", Long::plus),
    MULTIPLY("*", Long::times),
    CONCATENATE("||", { a, b -> (a.toString() + b.toString()).toLong() })
    ;

    operator fun invoke(a: Long, b: Long): Long {
        return this.func.apply(a, b)
    }

    companion object {
        fun legalOps(): Array<Operator> {
            return arrayOf(ADD, MULTIPLY)
        }
    }
}

fun main() {
    println("*** Advent of Code ***")
    println("### Day 07: Bridge Repair ###")

    println("--- Part 1 ---")

    val input = Files.readAllLines(Path.of("2024", "day07", "input.txt"))

    var validResultsPart1: Long = 0
    var validResultsPart2: Long = 0
    for (equation in input) {
        val (result, inputs) = equation.split(':')
        val operands = inputs.trim().split(' ').map(String::toInt)

        val resultLong = result.toLong()

        val valid1 = isEqValid(resultLong, operands.first().toLong(), 1, operands, Operator.legalOps())
        if (valid1)
            validResultsPart1 += resultLong

        val valid2 = isEqValid(resultLong, operands.first().toLong(), 1, operands, Operator.entries.toTypedArray())
        if (valid2)
            validResultsPart2 += resultLong
    }

    println("The total calibration result is $validResultsPart1")

    println("--- Part 2 ---")

    println("The total calibration result is $validResultsPart2")
}

fun isEqValid(testValue: Long, accumulator: Long, i: Int, operands: List<Int>, operators: Array<Operator>): Boolean {
    if (i < operands.size) {
        var isValid = false
        for (op in operators) {
            val newVal = op(accumulator, operands[i].toLong())
            isValid = isValid || isEqValid(testValue, newVal, i+1, operands, operators)
        }
        return isValid
    }
    else {
        return testValue == accumulator
    }
}