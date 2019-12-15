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
    fun get__n(): Byte
    fun get__c(): ByteArray
    fun get__i(): ByteArray
    fun get__w(): ByteArray
    fun get__x(): ByteArray
    fun get__af(): Array<VertexNormal>
    fun get__j(): Array<VertexNormal>
    fun get__aa(): Int
    fun get__ag(): Int
    fun get__ak(): Int
    fun get__ao(): Int
    fun get__aw(): Int
    fun get__r(): Int
    fun get__m(): IntArray
    fun get__t(): IntArray
    fun get__a(): ShortArray
    fun get__d(): ShortArray
    fun get__s(): ShortArray
    fun get__ad(): Short
    fun get__am(): Short
}
