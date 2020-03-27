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
    fun get__h(): Byte
    fun get__e(): ByteArray
    fun get__i(): ByteArray
    fun get__p(): ByteArray
    fun get__t(): ByteArray
    fun get__ae(): Array<VertexNormal>
    fun get__f(): Array<VertexNormal>
    fun get__ab(): Int
    fun get__af(): Int
    fun get__al(): Int
    fun get__ar(): Int
    fun get__at(): Int
    fun get__g(): Int
    fun get__o(): IntArray
    fun get__r(): IntArray
    fun get__a(): ShortArray
    fun get__b(): ShortArray
    fun get__l(): ShortArray
    fun get__ai(): Short
    fun get__an(): Short
}
