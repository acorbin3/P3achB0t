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
    fun get__q(): Byte
    fun get__d(): ByteArray
    fun get__f(): ByteArray
    fun get__g(): ByteArray
    fun get__m(): ByteArray
    fun get__ab(): Array<VertexNormal>
    fun get__e(): Array<VertexNormal>
    fun get__aa(): Int
    fun get__am(): Int
    fun get__an(): Int
    fun get__ap(): Int
    fun get__au(): Int
    fun get__c(): Int
    fun get__r(): IntArray
    fun get__u(): IntArray
    fun get__j(): ShortArray
    fun get__v(): ShortArray
    fun get__y(): ShortArray
    fun get__ag(): Short
    fun get__ao(): Short
}
