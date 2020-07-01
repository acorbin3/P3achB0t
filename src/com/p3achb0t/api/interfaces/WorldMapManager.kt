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
    fun get__h(): Int
    fun get__k(): Int
    fun get__y(): Int
    fun get__n(): Any
    fun get__a(): AbstractArchive
    fun get__z(): AbstractArchive
    fun get__r(): Int
}
