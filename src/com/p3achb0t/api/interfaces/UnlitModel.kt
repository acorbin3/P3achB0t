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
    fun get__i(): Byte
    fun get__a(): ByteArray
    fun get__e(): ByteArray
    fun get__l(): ByteArray
    fun get__z(): ByteArray
    fun get__an(): Array<VertexNormal>
    fun get__d(): Array<VertexNormal>
    fun get__ac(): Int
    fun get__ae(): Int
    fun get__ak(): Int
    fun get__as(): Int
    fun get__au(): Int
    fun get__f(): Int
    fun get__g(): IntArray
    fun get__q(): IntArray
    fun get__b(): ShortArray
    fun get__r(): ShortArray
    fun get__y(): ShortArray
    fun get__aj(): Short
    fun get__ax(): Short
}
