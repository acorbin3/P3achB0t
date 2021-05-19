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
    fun get__k(): Int
    fun get__p(): Int
    fun get__r(): Int
    fun get__x(): Int
    fun get__t(): Any
    fun get__b(): AbstractArchive
    fun get__q(): AbstractArchive
    fun get__a(): Int
}
