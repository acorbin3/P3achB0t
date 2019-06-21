package com.p3achb0t._runelite_interfaces

interface Model : Entity {
    fun getBottomY(): Int
    fun getBoundsType(): Int
    fun getDiameter(): Int
    fun getFaceAlphas(): Array<Byte>
    fun getFaceColors1(): Array<Int>
    fun getFaceColors2(): Array<Int>
    fun getFaceColors3(): Array<Int>
    fun getFaceLabelsAlpha(): Array<Array<Int>>
    fun getFaceTextures(): Array<Short>
    fun getIndices1(): Array<Int>
    fun getIndices2(): Array<Int>
    fun getIndices3(): Array<Int>
    fun getIndicesCount(): Int
    fun getIsSingleTile(): Boolean
    fun getRadius(): Int
    fun getVertexLabels(): Array<Array<Int>>
    fun getVerticesCount(): Int
    fun getVerticesX(): Array<Int>
    fun getVerticesY(): Array<Int>
    fun getVerticesZ(): Array<Int>
    fun getXMid(): Int
    fun getXMidOffset(): Int
    fun getXzRadius(): Int
    fun getYMid(): Int
    fun getYMidOffset(): Int
    fun getZMid(): Int
    fun getZMidOffset(): Int
    fun get__y(): Byte
    fun get__s(): Array<Byte>
    fun get__z(): Array<Byte>
    fun get__h(): Int
    fun get__b(): Array<Int>
    fun get__c(): Array<Int>
    fun get__r(): Array<Int>
}
