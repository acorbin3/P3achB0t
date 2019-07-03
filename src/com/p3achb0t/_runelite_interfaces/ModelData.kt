package com.p3achb0t._runelite_interfaces

interface ModelData : Entity {
    fun getFaceAlphas(): ByteArray
    fun getFaceColors(): ShortArray
    fun getFaceCount(): Int
    fun getFaceLabelsAlpha(): Array<IntArray>
    fun getFaceNormals(): Array<FaceNormal>
    fun getFaceTextures(): ShortArray
    fun getIndices1(): IntArray
    fun getIndices2(): IntArray
    fun getIndices3(): IntArray
    fun getIsBoundsCalculated(): Boolean
    fun getVertexLabels(): Array<IntArray>
    fun getVerticesCount(): Int
    fun getVerticesX(): IntArray
    fun getVerticesY(): IntArray
    fun getVerticesZ(): IntArray
    fun get__a(): Byte
    fun get__e(): ByteArray
    fun get__j(): ByteArray
    fun get__k(): ByteArray
    fun get__x(): ByteArray
    fun get__ag(): Array<VertexNormal>
    fun get__v(): Array<VertexNormal>
    fun get__ac(): Int
    fun get__ah(): Int
    fun get__ak(): Int
    fun get__ar(): Int
    fun get__ay(): Int
    fun get__z(): Int
    fun get__b(): IntArray
    fun get__h(): IntArray
    fun get__s(): ShortArray
    fun get__t(): ShortArray
    fun get__y(): ShortArray
    fun get__aj(): Short
    fun get__aq(): Short
}
