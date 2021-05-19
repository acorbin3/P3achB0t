package com.p3achb0t.api.interfaces

interface Model : Entity {
    fun getBottomY(): Int
    fun getBoundsType(): Int
    fun getDiameter(): Int
    fun getFaceAlphas(): ByteArray
    fun getFaceColors1(): IntArray
    fun getFaceColors2(): IntArray
    fun getFaceColors3(): IntArray
    fun getFaceLabelsAlpha(): Array<IntArray>
    fun getFaceTextures(): ShortArray
    fun getIndices1(): IntArray
    fun getIndices2(): IntArray
    fun getIndices3(): IntArray
    fun getIndicesCount(): Int
    fun getIsSingleTile(): Boolean
    fun getRadius(): Int
    fun getVertexLabels(): Array<IntArray>
    fun getVerticesCount(): Int
    fun getVerticesX(): IntArray
    fun getVerticesY(): IntArray
    fun getVerticesZ(): IntArray
    fun getXMid(): Int
    fun getXMidOffset(): Int
    fun getXzRadius(): Int
    fun getYMid(): Int
    fun getYMidOffset(): Int
    fun getZMid(): Int
    fun getZMidOffset(): Int
    fun get__m(): Byte
    fun get__a(): ByteArray
    fun get__d(): ByteArray
    fun get__j(): Int
    fun get__f(): IntArray
    fun get__s(): IntArray
    fun get__y(): IntArray
}
