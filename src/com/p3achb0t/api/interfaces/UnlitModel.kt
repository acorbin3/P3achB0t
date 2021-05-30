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
    fun get__e(): Byte
    fun get__a(): ByteArray
    fun get__d(): ByteArray
    fun get__l(): ByteArray
    fun get__s(): ByteArray
    fun get__ae(): Array<VertexNormal>
    fun get__i(): Array<VertexNormal>
    fun get__ad(): Int
    fun get__ag(): Int
    fun get__ai(): Int
    fun get__ar(): Int
    fun get__ax(): Int
    fun get__g(): Int
    fun get__w(): IntArray
    fun get__z(): IntArray
    fun get__k(): ShortArray
    fun get__m(): ShortArray
    fun get__x(): ShortArray
    fun get__ab(): Short
    fun get__ap(): Short
}
