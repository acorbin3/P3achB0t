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
    fun get__a(): ByteArray
    fun get__s(): ByteArray
    fun get__y(): ByteArray
    fun get__z(): ByteArray
    fun get__ah(): Array<VertexNormal>
    fun get__f(): Array<VertexNormal>
    fun get__ab(): Int
    fun get__ag(): Int
    fun get__ak(): Int
    fun get__am(): Int
    fun get__ax(): Int
    fun get__r(): Int
    fun get__l(): IntArray
    fun get__t(): IntArray
    fun get__d(): ShortArray
    fun get__e(): ShortArray
    fun get__v(): ShortArray
    fun get__ai(): Short
    fun get__ao(): Short
}
