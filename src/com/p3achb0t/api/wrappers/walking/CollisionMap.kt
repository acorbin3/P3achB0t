package com.p3achb0t.api.wrappers.walking

import java.nio.ByteBuffer
import java.util.*
import kotlin.experimental.and


class CollisionMap(data:  ByteArray){
    val regions: Array<BitSet4D?> = arrayOfNulls<BitSet4D>(256 * 256)

    init {
        val buffer = ByteBuffer.wrap(data)

        while (buffer.hasRemaining()) {
            var region = buffer.short.and(0xffff.toShort()).toInt()
            regions[region] = BitSet4D(buffer, 64, 64, 4, 2)
        }
    }


    fun toBytes(): ByteArray? {
        val regionCount = Arrays.stream(regions).filter(Objects::nonNull).count() as Int
        val buffer = ByteBuffer.allocate(regionCount * (2 + 64 * 64 * 4 * 2 / 8))
        for (i in regions.indices) {
            if (regions[i] != null) {
                buffer.putShort(i.toShort())
                regions[i]!!.write(buffer)
            }
        }
        return buffer.array()
    }

    fun createRegion(region: Int) {
        regions[region] = BitSet4D(64, 64, 4, 2)
        regions[region]!!.setAll(true)
    }

    operator fun set(x: Int, y: Int, z: Int, w: Int, value: Boolean) {
        val region = regions[x / 64 * 256 + y / 64] ?: return
        region[x % 64, y % 64, z, w] = value
    }

    operator fun get(x: Int, y: Int, z: Int, w: Int): Boolean {
        val region = regions[x / 64 * 256 + y / 64] ?: return false
        return region[x % 64, y % 64, z, w]
    }


    fun n(x: Int, y: Int, z: Int): Boolean {
        return get(x, y, z, 0)
    }

    fun s(x: Int, y: Int, z: Int): Boolean {
        return n(x, y - 1, z)
    }

    fun e(x: Int, y: Int, z: Int): Boolean {
        return get(x, y, z, 1)
    }

    fun w(x: Int, y: Int, z: Int): Boolean {
        return e(x - 1, y, z)
    }

    fun ne(x: Int, y: Int, z: Int): Boolean {
        return n(x, y, z) && e(x, y + 1, z) && e(x, y, z) && n(x + 1, y, z)
    }

    fun nw(x: Int, y: Int, z: Int): Boolean {
        return n(x, y, z) && w(x, y + 1, z) && w(x, y, z) && n(x - 1, y, z)
    }

    fun se(x: Int, y: Int, z: Int): Boolean {
        return s(x, y, z) && e(x, y - 1, z) && e(x, y, z) && s(x + 1, y, z)
    }

    fun sw(x: Int, y: Int, z: Int): Boolean {
        return s(x, y, z) && w(x, y - 1, z) && w(x, y, z) && s(x - 1, y, z)
    }
}