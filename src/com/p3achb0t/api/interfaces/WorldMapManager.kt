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
    fun get__e(): Int
    fun get__l(): Int
    fun get__o(): Int
    fun get__r(): Any
    fun get__d(): AbstractArchive
    fun get__s(): AbstractArchive
    fun get__g(): Int
}
