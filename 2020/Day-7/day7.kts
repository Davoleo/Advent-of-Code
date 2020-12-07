import Day7.BagBundle
import java.io.File
import kotlin.collections.ArrayList

typealias BagBundle = Pair<String, Int>

val file = File("input.txt")
val lines = file.readLines()

//Yes, a map would have done the work WAY better, but it's too late now
val bagMap: MutableMap<String, List<BagBundle>> = mutableMapOf()

lines.forEach { line ->
    val parts = line.split("bags contain")
    val color = parts[0].trimEnd()

    val bagChildren: ArrayList<BagBundle> = ArrayList()

    if (!parts[1].contains("no other bags")) {
        val children = parts[1].split(',')
        for (child in children) {
            val splitChild = child.trimStart().split(' ')
            val childCount = splitChild[0].toInt()
            val childColor = splitChild[1] + ' ' + splitChild[2]
            bagChildren.add(Pair(childColor, childCount))
        }
    }

    bagMap[color] = bagChildren
}

var checked: MutableSet<String> = mutableSetOf()
//do {
//    val hasFinished = computeLevelOfBags(bagMap, checked)
//} while (!hasFinished)
println("The total number of bags that contain shiny gold bags is ${checked.size}")
println(checked)
println("------------------------------------------------------------------------")
val innerBags = countInnerBags("shiny gold", bagMap)
println("The number of bags contained by one shiny gold bag is $innerBags")


fun computeLevelOfBags(bagList: Map<String, List<BagBundle>>, checked: MutableSet<String>): Boolean {

    if (checked.isEmpty()) {
        bagList.forEach { (color, bagChildren): Map.Entry<String, List<BagBundle>> ->
            for (child in bagChildren) {
                if (child.first == "shiny gold") {
                    checked.add(color)
                }
            }
        }
        return false
    }
    else {
        var hasNewItems = false

        val newBags = mutableSetOf<String>()
        bagList.forEach { (color, bagChildren): Map.Entry<String, List<BagBundle>> ->
            for (checkedBag in checked) {
                for (child in bagChildren) {
                    if (child.first == checkedBag) {
                        newBags.add(color)
                    }
                }
            }
        }

        hasNewItems = hasNewItems || checked.addAll(newBags)
        return !hasNewItems
    }
}

fun countInnerBags(bagName: String, bagMap: Map<String, List<BagBundle>>): Int {
    var result = 0

    val children = bagMap[bagName]
    if (children!!.isNotEmpty()) {
        println(children)
        for (child in children) {
            val innerResult = countInnerBags(child.first, bagMap)
            result +=
                    if (innerResult != 0) child.second + child.second * innerResult
                    else child.second

        }
    }

    return result
}
