package com.p3achb0t.api.interfaces

interface VorbisCodebook {
    fun getDimensions(): Int
    fun getEntries(): Int
    fun getLengths(): IntArray
    fun getMultiplicands(): Array<Array<Float>>
    fun getMults(): IntArray
    fun get__z(): IntArray
}
