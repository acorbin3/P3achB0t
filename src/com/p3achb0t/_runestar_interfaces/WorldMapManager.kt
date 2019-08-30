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
    fun get__c(): Int
    fun get__p(): Int
	fun get__r(): Int
	fun get__y(): Int
    fun get__t(): Any
    fun get__d(): AbstractArchive
    fun get__x(): AbstractArchive
    fun get__b(): Int
}
