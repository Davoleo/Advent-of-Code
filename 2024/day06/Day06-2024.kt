package day06

import java.nio.file.Files
import java.nio.file.Path

class Guard(var x: Int, var y: Int, var direction: Direction) {
    fun move(coords: Pair<Int, Int> = direction.vec) {
        y += coords.first
        x += coords.second
    }

    val pos get(): Pair<Int, Int> = y to x

    fun rotate() {
        direction = direction.rotate90Dg()
    }
}

enum class Direction(val vec: Pair<Int, Int>) {
    UP(Pair(-1, 0)),
    RIGHT(Pair(0, 1)),
    DOWN(Pair(1, 0)),
    LEFT(Pair(0, -1));


    fun opposite(): Direction {
        return when (this) {
            UP -> DOWN
            RIGHT -> LEFT
            DOWN -> UP
            LEFT -> RIGHT
        }
    }

    fun rotate90Dg(): Direction {
        return when(this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
    }
}

fun main() {
    println("*** Advent of Code ***")
    println("### Day 06: Guard Gallivant ###")

    println("--- Part 1 ---")

    val input = Files.readAllLines(Path.of("2024", "day06", "column.txt")).map(String::toCharArray)

    println("${input.size} x ${input.first().size}")


    val guard = findGuard(input)
    val ip = guard.pos
    println("Initial guard position: (${guard.y}, ${guard.x})")
    val tiles = simulateGuard(input, guard)

    println("The number of steps taken by the guard before going outbound is: $tiles")
    //1699

    println("--- Part 2 ---")


    val mInput = input.toMutableList()
    val guardCheck = Guard(
        x = ip.second,
        y = ip.first,
        direction = Direction.UP
    )
    var loops = 0
    while (true) {

        guardCheck.move()
        println("guardCheck: ${guardCheck.y} ${guardCheck.x}, ${guardCheck.direction}")

        if (!isGuardInbound(guardCheck, mInput))
            break

        val (newY, newX) = guardCheck.pos

        if (mInput[newY][newX] == '.') {
            mInput[newY][newX] = '#'
            guardCheck.move(guardCheck.direction.opposite().vec)

            val innerGuard = Guard(
                x = guardCheck.x,
                y = guardCheck.y,
                direction = guardCheck.direction
            )
            if (loopCheckGuard(mInput, innerGuard)) {
                println("obstacle in $newY $newX creates loop")
                loops++
            }

            mInput[newY][newX] = '.'
            guardCheck.move()
        }
        else if (mInput[newY][newX] == '#') {
            guardCheck.move(guardCheck.direction.opposite().vec)
            guardCheck.rotate()
        }
    }

    println("The number of loops that can be created by adding 1 obstacle is: $loops")
    //5142 too high

}

fun findGuard(map: List<CharArray>): Guard {
    for (line in map.indices) {
        val guardPos = map[line].indexOf('^')
        if (guardPos != -1) {
            return Guard(
                y = line,
                x = guardPos,
                direction = Direction.UP
            )
        }

    }
    throw IllegalStateException("Guard not found")
}

fun simulateGuard(map: List<CharArray>, guard: Guard): Int {
    val tilesSteppedOn: MutableSet<Pair<Int, Int>> = mutableSetOf(guard.pos)

    while (true) {
        guard.move()
        if (!isGuardInbound(guard, map))
            break

        val char = map[guard.y][guard.x]


        println("guard pos: (${guard.y}, ${guard.x}) -> char: $char, tiles: ${tilesSteppedOn.size}")

        //Obstacle
        if (char == '#') {
            guard.move(guard.direction.opposite().vec)
            guard.rotate()
            continue
        }
        else if (char == '.' || char == '^') {
            tilesSteppedOn.add(guard.pos)
        }
    }

    return tilesSteppedOn.size
}

fun loopCheckGuard(map: List<CharArray>, guard: Guard): Boolean {

    println("-----")
    map.forEach { line -> println(line.joinToString("")) }
    println("-----")

    val history: MutableSet<Triple<Int, Int, Direction>> = mutableSetOf(Triple(guard.y, guard.x, guard.direction))

    while (true) {
        guard.move()
        if (!isGuardInbound(guard, map))
            break

        val char = map[guard.y][guard.x]

        //Obstacle
        if (char == '#') {
            guard.move(guard.direction.opposite().vec)
            guard.rotate()
            continue
        }
        else if (char == '.' || char == '^') {
            val existed = !history.add(Triple(guard.y, guard.x, guard.direction))
            if (existed) {
                return true
            }
        }
    }

    return false

}

fun isGuardInbound(guard: Guard, map: List<CharArray>): Boolean {
    return guard.x < map.first().size && guard.x >= 0
            && guard.y < map.size && guard.y >= 0
}