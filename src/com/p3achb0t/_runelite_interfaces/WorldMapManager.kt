package com.p3achb0t._runelite_interfaces

interface WorldMapManager {
    fun getFonts(): Any
    fun getIcons(): Any
    fun getIsLoaded0(): Boolean
    fun getLoadStarted(): Boolean
    fun getMapAreaData(): WorldMapAreaData
    fun getMapSceneSprites(): Array<IndexedSprite>
    fun getOverviewSprite(): Sprite
    fun getRegions(): Array<Array<WorldMapRegion>>
    fun get__a(): Int
    fun get__i(): Int
    fun get__k(): Int
    fun get__n(): Int
    fun get__g(): Any
    fun get__e(): AbstractIndexCache
    fun get__x(): AbstractIndexCache
    fun get__z(): Int
}
