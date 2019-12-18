package com.p3achb0t._runestar_interfaces

interface WorldMapManager {
    fun getFonts(): Any
    fun getIcons(): Any
    fun getIsLoaded0(): Boolean
    fun getLoadStarted(): Boolean
    fun getMapAreaData(): WorldMapAreaData
    fun getMapSceneSprites(): Array<IndexedSprite>
    fun getOverviewSprite(): Sprite
    fun getRegions(): Array<Array<WorldMapRegion>>
    fun get__k(): Int
    fun get__o(): Int
    fun get__s(): Int
    fun get__v(): Int
    fun get__e(): Any
    fun get__a(): AbstractArchive
    fun get__d(): AbstractArchive
    fun get__l(): Int
}
