package day06

import java.nio.file.Files
import java.nio.file.Path

class Guard(var x: Int, var y: Int, var direction: Direction) {
    fun move(coords: Pair<Int, Int> = direction.vec) {
        y += coords.first
        x += coords.second
    }

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

    val input = Files.readAllLines(Path.of("2024", "day06", "input.txt"))


    val guard = findGuard(input)
    println("Initial guard position: (${guard.y}, ${guard.x})")
    val tiles = simulateGuard(input, guard)

    println("The number of steps taken by the guard before going outbound is: $tiles")
    //4662 too low

}

fun findGuard(map: List<String>): Guard {
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

fun simulateGuard(map: List<String>, guard: Guard): Int {
    var char: Char
    val tilesSteppedOn: MutableSet<Pair<Int, Int>> = mutableSetOf()

    while (true) {
        guard.move()
        if (!isGuardInbound(guard, map))
            break

        char = map[guard.y][guard.x]


        println("guard pos: (${guard.y}, ${guard.x}) -> char: $char, tiles: ${tilesSteppedOn.size}")

        //Obstacle
        if (char == '#') {
            guard.move(guard.direction.opposite().vec)
            guard.rotate()
            continue
        }
        else if (char == '.' || char == '^') {
            tilesSteppedOn.add(guard.y to guard.x)
        }
    }

    return tilesSteppedOn.size
}

fun isGuardInbound(guard: Guard, map: List<String>): Boolean {
    return guard.x < map.first().length && guard.x >= 0
            && guard.y < map.size && guard.y >= 0
}