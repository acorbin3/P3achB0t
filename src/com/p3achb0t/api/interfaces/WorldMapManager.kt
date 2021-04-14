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
    fun get__c(): Int
    fun get__d(): Int
    fun get__j(): Int
    fun get__m(): Int
    fun get__k(): Any
    fun get__h(): AbstractArchive
    fun get__n(): AbstractArchive
    fun get__r(): Int
}
