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
    fun get__p(): Byte
    fun get__b(): ByteArray
    fun get__e(): ByteArray
    fun get__q(): ByteArray
    fun get__x(): ByteArray
    fun get__ag(): Array<VertexNormal>
    fun get__n(): Array<VertexNormal>
    fun get__a(): Int
    fun get__al(): Int
    fun get__ap(): Int
    fun get__at(): Int
    fun get__av(): Int
    fun get__aw(): Int
    fun get__f(): IntArray
    fun get__j(): IntArray
    fun get__d(): ShortArray
    fun get__m(): ShortArray
    fun get__u(): ShortArray
    fun get__ae(): Short
    fun get__ao(): Short
}
