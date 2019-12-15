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
    fun get__e(): Int
    fun get__n(): Int
    fun get__o(): Int
    fun get__x(): Int
    fun get__h(): Any
    fun get__i(): AbstractArchive
    fun get__w(): AbstractArchive
    fun get__r(): Int
}
