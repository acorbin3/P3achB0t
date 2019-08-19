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
	fun get__r(): Int
	fun get__u(): Int
	fun get__v(): Int
	fun get__x(): Int
	fun get__b(): Any
	fun get__c(): AbstractArchive
	fun get__f(): AbstractArchive
	fun get__y(): Int
}
