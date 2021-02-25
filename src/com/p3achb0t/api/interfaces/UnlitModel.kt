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
    fun get__k(): Byte
    fun get__b(): ByteArray
    fun get__l(): ByteArray
    fun get__q(): ByteArray
    fun get__x(): ByteArray
    fun get__ay(): Array<VertexNormal>
    fun get__j(): Array<VertexNormal>
    fun get__ac(): Int
    fun get__ak(): Int
    fun get__aq(): Int
    fun get__at(): Int
    fun get__ax(): Int
    fun get__i(): Int
    fun get__p(): IntArray
    fun get__t(): IntArray
    fun get__f(): ShortArray
    fun get__g(): ShortArray
    fun get__u(): ShortArray
    fun get__ag(): Short
    fun get__am(): Short
}
