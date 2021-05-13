package com.p3achb0t.api.wrappers.walking

import com.p3achb0t.api.wrappers.Tile
import java.util.*
import java.util.function.Function
import java.util.function.Predicate
import java.util.stream.Collectors
import kotlin.collections.ArrayDeque

class Pathfinder(
    private val map: CollisionMap,
    var transports: Map<Tile, List<Tile>>,
    var starts: List<Tile>,
    var target: Tile
) {
    private var boundary: ArrayDeque<Tile> = ArrayDeque<Tile>()
    private var visited: MutableSet<Tile> = HashSet<Tile>()
    private val predecessors: PositionMap = PositionMap()
    fun find(): List<Tile>? {
        println("Start: ${starts.first()}. -> $target")
        boundary.addAll(starts)
        var lowestDist = Int.MAX_VALUE
        var count = 0
        while (!boundary.isEmpty() && count < 200_000) {
            count++
            var node: Tile = boundary.removeFirst()
            val curDist = target.distanceTo(node)
            if(lowestDist >curDist) {
                println("DistToEnd: $curDist")
                lowestDist = curDist
            }
//            println(count)
            if (target.isSameTile(node) || target.distanceTo(node) < 5) {
                val result: LinkedList<Tile> = LinkedList<Tile>()
                while (node != null && !node.isSameTile(starts.first()) ) {

                    result.add(0, node)
//                    println("$node distToEnd: ${node.distanceTo(target)}")
                    node = predecessors.get(node) as Tile
//                    println(node)
                }
                return result
            }
            addNeighbors(node)
        }
        println("ERROR: failed to get path. Loop count: $count")
        return null
    }

    private fun addNeighbors(position: Tile) {
        // Prefer taking transports as early as possible, to avoid causing problems with ladders which can be taken
        // from several source positions.
        transports.filter { it.key.isSameTile(position) }.forEach { t, u ->
            if(!predecessors.containsKey(u.first())){
                predecessors.put(u.first(), position)
                boundary.addLast(u.first())
            }
        }

        if (map.w(position.x, position.y, position.z)) {
            val neighbor = Tile(position.x - 1, position.y, position.z)
            if (!predecessors.containsKey(neighbor)) {
                predecessors.putE(neighbor)
                boundary.addLast(neighbor)
            }
        }
        if (map.e(position.x, position.y, position.z)) {
            val neighbor = Tile(position.x + 1, position.y, position.z)
            if (!predecessors.containsKey(neighbor)) {
                predecessors.putW(neighbor)
                boundary.addLast(neighbor)
            }
        }
        if (map.s(position.x, position.y, position.z)) {
            val neighbor = Tile(position.x, position.y - 1, position.z)
            if (!predecessors.containsKey(neighbor)) {
                predecessors.putN(neighbor)
                boundary.addLast(neighbor)
            }
        }
        if (map.n(position.x, position.y, position.z)) {
            val neighbor = Tile(position.x, position.y + 1, position.z)
            if (!predecessors.containsKey(neighbor)) {
                predecessors.putS(neighbor)
                boundary.addLast(neighbor)
            }
        }
        if (map.sw(position.x, position.y, position.z)) {
            val neighbor = Tile(position.x - 1, position.y - 1, position.z)
            if (!predecessors.containsKey(neighbor)) {
                predecessors.putNE(neighbor)
                boundary.addLast(neighbor)
            }
        }
        if (map.se(position.x, position.y, position.z)) {
            val neighbor = Tile(position.x + 1, position.y - 1, position.z)
            if (!predecessors.containsKey(neighbor)) {
                predecessors.putNW(neighbor)
                boundary.addLast(neighbor)
            }
        }
        if (map.nw(position.x, position.y, position.z)) {
            val neighbor = Tile(position.x - 1, position.y + 1, position.z)
            if (!predecessors.containsKey(neighbor)) {
                predecessors.putSE(neighbor)
                boundary.addLast(neighbor)
            }
        }
        if (map.ne(position.x, position.y, position.z)) {
            val neighbor = Tile(position.x + 1, position.y + 1, position.z)
            if (!predecessors.containsKey(neighbor)) {
                predecessors.putSW(neighbor)
                boundary.addLast(neighbor)
            }
        }
    }
}