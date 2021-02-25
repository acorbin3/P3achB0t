package com.p3achb0t.api.interfaces

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
    fun get__b(): Int
    fun get__k(): Int
    fun get__w(): Int
    fun get__z(): Any
    fun get__l(): AbstractArchive
    fun get__q(): AbstractArchive
    fun get__i(): Int
}
