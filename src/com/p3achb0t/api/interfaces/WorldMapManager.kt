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
    fun get__v(): Int
    fun get__x(): Int
    fun get__d(): Any
    fun get__j(): AbstractArchive
    fun get__m(): AbstractArchive
    fun get__w(): Int
}
