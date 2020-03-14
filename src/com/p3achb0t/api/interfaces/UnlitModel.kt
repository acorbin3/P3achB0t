package com.p3achb0t.api.interfaces

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
    fun get__x(): Byte
    fun get__h(): ByteArray
    fun get__j(): ByteArray
    fun get__k(): ByteArray
    fun get__m(): ByteArray
    fun get__ae(): Array<VertexNormal>
    fun get__u(): Array<VertexNormal>
    fun get__aa(): Int
    fun get__aj(): Int
    fun get__aq(): Int
    fun get__au(): Int
    fun get__ay(): Int
    fun get__w(): Int
    fun get__b(): IntArray
    fun get__f(): IntArray
    fun get__q(): ShortArray
    fun get__y(): ShortArray
    fun get__z(): ShortArray
    fun get__af(): Short
    fun get__az(): Short
}
