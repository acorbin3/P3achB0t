package com.p3achb0t.api.wrappers.walking

import com.p3achb0t.Main
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.client.cache.util.Util.ungzip
import java.io.IOException
import java.io.UncheckedIOException
import java.util.function.Predicate

object PathfinderTest {
    private var map: CollisionMap? = null
    val START: Tile = Tile(3225, 3218, 0)
    val END: Tile = Tile(3166, 3482, 0)
    @JvmStatic
    fun main(args: Array<String>) {
        while (true) {
            val s = System.nanoTime()
            val path = Pathfinder(map!!, HashMap(), arrayListOf(START), END).find()
            println(((System.nanoTime() - s) / 1000000.0).toString() + "ms")
            path?.forEach { print("$it ") }

        }
    }

    init {
        try {
            map = CollisionMap(ungzip(Main.javaClass.getResourceAsStream("/collision-map").readAllBytes()))
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }
}