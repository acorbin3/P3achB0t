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
    fun get__o(): ByteArray
    fun get__t(): ByteArray
    fun get__u(): ByteArray
    fun get__y(): ByteArray
    fun get__ab(): Array<VertexNormal>
    fun get__i(): Array<VertexNormal>
    fun get__ah(): Int
    fun get__ar(): Int
    fun get__av(): Int
    fun get__ay(): Int
    fun get__az(): Int
    fun get__r(): Int
    fun get__d(): IntArray
    fun get__v(): IntArray
    fun get__j(): ShortArray
    fun get__s(): ShortArray
    fun get__w(): ShortArray
    fun get__ac(): Short
    fun get__ao(): Short
}
