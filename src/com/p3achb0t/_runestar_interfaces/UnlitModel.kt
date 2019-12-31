package com.p3achb0t._runestar_interfaces

interface UnlitModel : Entity {
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
    fun get__s(): Byte
    fun get__a(): ByteArray
    fun get__d(): ByteArray
    fun get__o(): ByteArray
    fun get__t(): ByteArray
    fun get__aa(): Array<VertexNormal>
    fun get__q(): Array<VertexNormal>
    fun get__ah(): Int
    fun get__ak(): Int
    fun get__ao(): Int
    fun get__aq(): Int
    fun get__au(): Int
    fun get__l(): Int
    fun get__h(): IntArray
    fun get__z(): IntArray
    fun get__j(): ShortArray
    fun get__n(): ShortArray
    fun get__u(): ShortArray
    fun get__av(): Short
    fun get__aw(): Short
}
