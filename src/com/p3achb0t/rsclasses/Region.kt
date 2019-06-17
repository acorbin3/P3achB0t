package com.p3achb0t.rsclasses

import org.objectweb.asm.tree.ClassNode

class Region : RSClasses {
    var focusedX = 0
    var focusedY = 0
    var gameObjects = ArrayList<GameObject>()
    var rightClickWalk = ""
    var tiles = ArrayList<ArrayList<ArrayList<Tile>>>()
    var visibilityTilesMap = ArrayList<ArrayList<ArrayList<ArrayList<Boolean>>>>()
    var visibleTiles = ArrayList<ArrayList<Boolean>>()

    constructor()
    constructor(fields: MutableMap<String, Field?>) : super() {
        focusedX = fields["focusedX"]?.resultValue?.toInt() ?: -1
        focusedY = fields["focusedY"]?.resultValue?.toInt() ?: -1
        for (item in fields["gameObjects"]?.arrayData!!) {
            if (item is Field)
                gameObjects.add(GameObject(item.fields))
        }
//        gameObjects = fields["gameObjects"]?.arrayData
        rightClickWalk = fields["rightClickWalk"]?.resultValue.toString()
//        tiles = fields["tiles"]?.resultValue.toString()
//        visibilityTilesMap = fields["visibilityTilesMap"]?.resultValue.toString()
//        visibleTiles = fields["visibleTiles"]?.resultValue.toString()
    }
    override fun analyze(node: ClassNode, rsClassesMap: Map<String, RSClasses>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}