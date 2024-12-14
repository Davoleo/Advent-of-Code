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

class MapCell(val char: Char, val y: Int, val x: Int);
typealias GuardAction = (MapCell) -> Boolean;

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

    val input = Files.readAllLines(Path.of("2024", "day06", "input.txt")).map(String::toCharArray)

    println("${input.size} x ${input.first().size}")


    val guard = findGuard(input)
    val ip = guard.pos
    println("Initial guard position: (${guard.y}, ${guard.x})")
    val tiles = simulateGuard(input, guard)

    println("The number of steps taken by the guard before going outbound is: $tiles")

    println("--- Part 2 ---")


    val mInput = input.toMutableList()
    val guardCheck = Guard(
        x = ip.second,
        y = ip.first,
        direction = Direction.UP
    )
    var loops = 0

    val alreadyPlaced: MutableSet<Pair<Int, Int>> = mutableSetOf()

    //this simulation is to follow the normal path and place obstacles each time
    loopCheckGuard(mInput, guardCheck) { nextMapCell ->

        //skip possible obstacle simulation if there is a wall, starting position
        //fuck this shit we need to avoid already placed obstacles.
        if (nextMapCell.char == '#' || nextMapCell.char == '^' || !alreadyPlaced.add(Pair(nextMapCell.y, nextMapCell.x)))
            return@loopCheckGuard false

        val history: MutableSet<Triple<Int, Int, Direction>> = mutableSetOf()

        //place fake wall
        mInput[nextMapCell.y][nextMapCell.x] = '#'
        //create guard
        val simulatedGuard = Guard(guardCheck.x, guardCheck.y, guardCheck.direction)
        //start simulation to find loop
        val withoutLoop = loopCheckGuard(mInput, simulatedGuard) simLoop@{ _ ->

            val added = history.add(Triple(simulatedGuard.y, simulatedGuard.x, simulatedGuard.direction))

            //this ends the loop simulation, when the guard visits a past cell
            if (!added) {
                println("-----")
                mInput.forEach { line -> println(line.joinToString("")) }
                println("-----")
                return@simLoop true;
            }
            return@simLoop false;
        }

        if (!withoutLoop)
            loops++

        //this is for checking for loops
        mInput[nextMapCell.y][nextMapCell.x] = '.'

        return@loopCheckGuard false;
    }

    println("The number of loops that can be created by adding 1 obstacle is: $loops")
    //5142 too high
    //1699

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


        //println("guard pos: (${guard.y}, ${guard.x}) -> char: $char, tiles: ${tilesSteppedOn.size}")

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



fun loopCheckGuard(map: List<CharArray>, guard: Guard, onTurn: GuardAction): Boolean {

    while (true) {

        val nextMapCell = peekNextCell(guard, map) ?: break;

        if(onTurn.invoke(nextMapCell))
            return false
        //Is a WALL
        if (nextMapCell.char == '#')
            guard.rotate()
        else
            guard.move()

    }

    return true

}

fun peekNextCell(guard: Guard, map: List<CharArray>): MapCell? {

    val nextY = guard.y + guard.direction.vec.first
    val nextX = guard.x + guard.direction.vec.second

    val inbound = nextX < map.first().size && nextX >= 0
            && nextY < map.size && nextY >= 0
    if (inbound)
        return MapCell(map[nextY][nextX], nextY, nextX)
    return null

}
fun isGuardInbound(guard: Guard, map: List<CharArray>): Boolean {
    return guard.x < map.first().size && guard.x >= 0
            && guard.y < map.size && guard.y >= 0
}