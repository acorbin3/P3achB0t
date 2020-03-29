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
    fun get__h(): Int
    fun get__n(): Int
    fun get__p(): Int
    fun get__u(): Int
    fun get__q(): Any
    fun get__e(): AbstractArchive
    fun get__t(): AbstractArchive
    fun get__g(): Int
}
