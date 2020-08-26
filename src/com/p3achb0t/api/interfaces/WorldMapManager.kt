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
    fun get__d(): Int
    fun get__h(): Int
    fun get__q(): Int
    fun get__x(): Any
    fun get__g(): AbstractArchive
    fun get__m(): AbstractArchive
    fun get__c(): Int
}
