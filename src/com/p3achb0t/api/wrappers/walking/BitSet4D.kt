package com.p3achb0t.api.wrappers.walking

import java.lang.IndexOutOfBoundsException
import java.nio.ByteBuffer
import java.util.*

class BitSet4D {
    private val sizeX: Int
    private val sizeY: Int
    private val sizeZ: Int
    private val sizeW: Int
    private val bits: BitSet

    constructor(sizeX: Int, sizeY: Int, sizeZ: Int, sizeW: Int) {
        this.sizeX = sizeX
        this.sizeY = sizeY
        this.sizeZ = sizeZ
        this.sizeW = sizeW
        bits = BitSet(sizeX * sizeY * sizeZ * sizeW)
    }

    constructor(buffer: ByteBuffer, sizeX: Int, sizeY: Int, sizeZ: Int, sizeW: Int) {
        this.sizeX = sizeX
        this.sizeY = sizeY
        this.sizeZ = sizeZ
        this.sizeW = sizeW
        val oldLimit = buffer.limit()
        buffer.limit(buffer.position() + (sizeX * sizeY * sizeZ * sizeW + 7) / 8)
        bits = BitSet.valueOf(buffer)
        buffer.position(buffer.limit())
        buffer.limit(oldLimit)
    }

    fun write(buffer: ByteBuffer) {
        val startPos = buffer.position()
        buffer.put(bits.toByteArray())
        buffer.position(startPos + (sizeX * sizeY * sizeZ * sizeW + 7) / 8)
    }

    operator fun get(x: Int, y: Int, z: Int, w: Int): Boolean {
        return bits[index(x, y, z, w)]
    }

    operator fun set(x: Int, y: Int, z: Int, flag: Int, value: Boolean) {
        bits[index(x, y, z, flag)] = value
    }

    fun setAll(value: Boolean) {
        bits[0, bits.size()] = value
    }

    private fun index(x: Int, y: Int, z: Int, w: Int): Int {
        if (x < 0 || y < 0 || z < 0 || w < 0 || x >= sizeX || y >= sizeY || z >= sizeZ || w >= sizeW) {
            throw IndexOutOfBoundsException("($x, $y, $z, $w)")
        }
        var index = z
        index = index * sizeY + y
        index = index * sizeX + x
        index = index * sizeW + w
        return index
    }
}