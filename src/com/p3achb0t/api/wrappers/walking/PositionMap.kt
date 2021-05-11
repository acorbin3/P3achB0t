package com.p3achb0t.api.wrappers.walking

import com.p3achb0t.api.wrappers.Tile
import java.util.HashMap
import com.p3achb0t.api.wrappers.walking.PositionMap

class PositionMap {
    private val regions = arrayOfNulls<ByteArray>(256 * 256)
    private val custom: MutableMap<Tile, Tile> = HashMap()
    fun containsKey(key: Tile): Boolean {
        return region(key)!![index(key)].toInt() != 0
    }

    operator fun get(key: Tile): Tile {
        val code = region(key)!![index(key)]
        println("Code: $code")
        return when (code) {
            NONE -> Tile();
            CUSTOM -> {
                var goodTile = Tile()
                custom.forEach { t, u ->
                    if(t.distanceTo(key) == 0 && t.z == key.z){
                        goodTile = u
                    }
                }
                goodTile
            }
            N -> key.north();
            NE -> key.northEast();
            E -> key.east();
            SE -> key.southEast();
            S -> key.south();
            SW -> key.southWest();
            W -> key.west();
            NW -> key.northWest();
            else -> Tile()
        };
    }

    fun put(key: Tile, value: Tile) {
        region(key)!![index(key)] = CUSTOM
        custom[key] = value
    }

    fun putN(key: Tile) {
        region(key)!![index(key)] = N
    }

    fun putNE(key: Tile) {
        region(key)!![index(key)] = NE
    }

    fun putE(key: Tile) {
        region(key)!![index(key)] = E
    }

    fun putSE(key: Tile) {
        region(key)!![index(key)] = SE
    }

    fun putS(key: Tile) {
        region(key)!![index(key)] = S
    }

    fun putSW(key: Tile) {
        region(key)!![index(key)] = SW
    }

    fun putW(key: Tile) {
        region(key)!![index(key)] = W
    }

    fun putNW(key: Tile) {
        region(key)!![index(key)] = NW
    }

    private fun index(tile: Tile): Int {
        return tile.x % 64 + tile.y % 64 * 64 + tile.z % 64 * 64 * 64
    }

    private fun region(tile: Tile): ByteArray? {
        val regionIndex = tile.x / 64 * 256 + tile.y / 64
        var region = regions[regionIndex]
        if (region == null) {
            regions[regionIndex] = ByteArray(4 * 64 * 64)
            region = regions[regionIndex]
        }
        return region
    }

    companion object {
        const val NONE: Byte = 0
        const val CUSTOM: Byte = 1
        const val N: Byte = 2
        const val NE: Byte = 3
        const val E: Byte = 4
        const val SE: Byte = 5
        const val S: Byte = 6
        const val SW: Byte = 7
        const val W: Byte = 8
        const val NW: Byte = 9
    }
}