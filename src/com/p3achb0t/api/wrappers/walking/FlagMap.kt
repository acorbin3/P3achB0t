package com.p3achb0t.api.wrappers.walking

import java.nio.ByteBuffer
import java.util.*

class FlagMap {
    @JvmField
    val flags: BitSet
    val minX: Int
    val minY: Int
    val maxX: Int
    val maxY: Int
    private val width: Int
    private val height: Int
    private val flagCount: Int

    constructor(minX: Int, minY: Int, maxX: Int, maxY: Int, flagCount: Int) {
        this.minX = minX
        this.minY = minY
        this.maxX = maxX
        this.maxY = maxY
        this.flagCount = flagCount
        width = maxX - minX + 1
        height = maxY - minY + 1
        flags = BitSet(width * height * PLANE_COUNT * flagCount)
    }

    constructor(bytes: ByteArray?, flagCount: Int) {
        val buffer = ByteBuffer.wrap(bytes)
        minX = buffer.int
        minY = buffer.int
        maxX = buffer.int
        maxY = buffer.int
        this.flagCount = flagCount
        width = maxX - minX + 1
        height = maxY - minY + 1
        flags = BitSet.valueOf(buffer)
    }

    fun toBytes(): ByteArray {
        val bytes = ByteArray(16 + flags.size())
        val buffer = ByteBuffer.wrap(bytes)
        buffer.putInt(minX)
        buffer.putInt(minY)
        buffer.putInt(maxX)
        buffer.putInt(maxY)
        buffer.put(flags.toByteArray())
        return bytes
    }

    operator fun get(x: Int, y: Int, z: Int, flag: Int): Boolean {
        return if (x < minX || x > maxX || y < minY || y > maxY || z < 0 || z > PLANE_COUNT - 1) {
            false
        } else flags[index(x, y, z, flag)]
    }

    operator fun set(x: Int, y: Int, z: Int, flag: Int, value: Boolean) {
        flags[index(x, y, z, flag)] = value
    }

    private fun index(x: Int, y: Int, z: Int, flag: Int): Int {
        if (x < minX || x > maxX || y < minY || y > maxY || z < 0 || z > PLANE_COUNT - 1 || flag < 0 || flag > flagCount - 1) {
            throw IndexOutOfBoundsException("$x $y $z")
        }
        return (z * width * height + (y - minY) * width + (x - minX)) * flagCount + flag
    }

    companion object {
        const val PLANE_COUNT = 4
    }
}