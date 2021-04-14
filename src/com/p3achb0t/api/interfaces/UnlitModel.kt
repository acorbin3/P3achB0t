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
    fun get__j(): Byte
    fun get__h(): ByteArray
    fun get__m(): ByteArray
    fun get__n(): ByteArray
    fun get__q(): ByteArray
    fun get__ac(): Array<VertexNormal>
    fun get__y(): Array<VertexNormal>
    fun get__aj(): Int
    fun get__ap(): Int
    fun get__aq(): Int
    fun get__at(): Int
    fun get__aw(): Int
    fun get__r(): Int
    fun get__i(): IntArray
    fun get__z(): IntArray
    fun get__t(): ShortArray
    fun get__v(): ShortArray
    fun get__x(): ShortArray
    fun get__am(): Short
    fun get__ay(): Short
}
