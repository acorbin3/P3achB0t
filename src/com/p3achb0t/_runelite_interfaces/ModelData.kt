package com.p3achb0t._runelite_interfaces

interface ModelData : Entity {
    fun getFaceAlphas(): Array<Byte>
    fun getFaceColors(): Array<Short>
    fun getFaceCount(): Int
    fun getFaceLabelsAlpha(): Array<Array<Int>>
    fun getFaceNormals(): Array<FaceNormal>
    fun getFaceTextures(): Array<Short>
    fun getIndices1(): Array<Int>
    fun getIndices2(): Array<Int>
    fun getIndices3(): Array<Int>
    fun getIsBoundsCalculated(): Boolean
    fun getVertexLabels(): Array<Array<Int>>
    fun getVerticesCount(): Int
    fun getVerticesX(): Array<Int>
    fun getVerticesY(): Array<Int>
    fun getVerticesZ(): Array<Int>
    fun get__a(): Byte
    fun get__e(): Array<Byte>
    fun get__j(): Array<Byte>
    fun get__k(): Array<Byte>
    fun get__x(): Array<Byte>
    fun get__ag(): Array<VertexNormal>
    fun get__v(): Array<VertexNormal>
    fun get__ac(): Int
    fun get__ah(): Int
    fun get__ak(): Int
    fun get__ar(): Int
    fun get__ay(): Int
    fun get__z(): Int
    fun get__b(): Array<Int>
    fun get__h(): Array<Int>
    fun get__s(): Array<Short>
    fun get__t(): Array<Short>
    fun get__y(): Array<Short>
    fun get__aj(): Short
    fun get__aq(): Short
}
