package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.interfaces.*
import com.p3achb0t.api.wrappers.interfaces.Interactable
import com.p3achb0t.api.wrappers.interfaces.Locatable
import com.p3achb0t.api.wrappers.utils.Calculations
import com.p3achb0t.api.wrappers.utils.ObjectPositionInfo
import com.p3achb0t.api.wrappers.utils.getConvexHullFromModel
import com.p3achb0t.api.wrappers.utils.getTrianglesFromModel
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Polygon
import java.util.*

class GameObject(
        val sceneryObject: Scenery? = null,
        val wallObject: Wall? = null,
        val floorDecoration: FloorDecoration? = null,
        ctx: Context? = null,
        override var loc_ctx: Context? = ctx
) : Locatable,
        Interactable(ctx) {
    val id: Int
        get() {
            return when {
                sceneryObject != null -> sceneryObject.getTag().shr(17).and(0x7fff).toInt()
                wallObject != null -> wallObject.getTag().shr(17).and(0x7fff).toInt()
                floorDecoration != null -> floorDecoration.getTag().shr(17).and(0x7fff).toInt()
                else -> 0
            }
        }
    val name: String
        get() {
            val objectComposite =  getObjectComposite( id)
            return objectComposite?.getName().toString()
        }
    private val objectPositionInfo: ObjectPositionInfo
        get() {
            return when {
                sceneryObject != null -> ObjectPositionInfo(
                        sceneryObject.getCenterX(),
                        sceneryObject.getCenterY(),
                        sceneryObject.getOrientation()
                )
                wallObject != null -> ObjectPositionInfo(
                        wallObject.getX(),
                        wallObject.getY(),
                        wallObject.getOrientationA()
                )
                floorDecoration != null -> ObjectPositionInfo(
                        floorDecoration.getX(),
                        floorDecoration.getY(),
                        0
                )
                else -> ObjectPositionInfo(0, 0, 0)
            }
        }
    val model: Model?
        get() {
            return when {
                sceneryObject != null -> sceneryObject.getEntity() as Model
                wallObject != null -> wallObject.getEntity1() as Model
                floorDecoration != null -> floorDecoration.getEntity() as Model
                else -> null
            }
        }
    override fun isMouseOverObj(): Boolean {
        val mousePoint = Point(ctx?.mouse?.getX() ?: -1, ctx?.mouse?.getY() ?: -1)
        return getConvexHull().contains(mousePoint)
    }
    override fun getNamePoint(): Point {
        val region = getRegionalLocation()
        return ctx?.let { Calculations.worldToScreen(region.x, region.y, sceneryObject?.getHeight() ?: 0, it) } ?: Point(0,0)
    }
    override suspend fun clickOnMiniMap(): Boolean {
        return when {
            sceneryObject != null -> ctx?.mouse?.click(
                    ctx?.let {
                        Calculations.worldToMiniMap(
                                sceneryObject.getCenterX(),
                                sceneryObject.getCenterY(),
                                it
                        )
                    } ?: Point(0,0)
            )?: false
            wallObject != null -> ctx?.mouse?.click(
                    ctx?.let {
                        Calculations.worldToMiniMap(
                                wallObject.getX(),
                                wallObject.getY(),
                                it
                        )
                    }?: Point(0,0)
            )?: false
            floorDecoration != null -> ctx?.mouse?.click(
                    ctx?.let {
                        Calculations.worldToMiniMap(
                                floorDecoration.getX(),
                                floorDecoration.getY(),
                                it
                        )
                    }?: Point(0,0)
            )?: false
            else -> false
        }
    }

    override fun getInteractPoint(): Point {
        return getRandomPoint(getConvexHull())
    }

    override fun draw(g: Graphics2D) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun draw(g: Graphics2D, color: Color) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGlobalLocation(): Tile {
        if (ctx?.client != null) {
            return when {
                sceneryObject != null -> Tile(
                        sceneryObject.getCenterX() / 128 + ctx?.client!!.getBaseX(),
                        sceneryObject.getCenterY() / 128 + ctx?.client!!.getBaseY(),
                        sceneryObject.getPlane(),ctx

                )
                wallObject != null -> Tile(
                        wallObject.getX() / 128 + ctx?.client!!.getBaseX(),
                        wallObject.getY() / 128 + ctx?.client!!.getBaseY(),
                        sceneryObject?.getPlane() ?: 0,ctx

                )
                floorDecoration != null -> Tile(
                        floorDecoration.getX() / 128 + ctx?.client!!.getBaseX(),
                        floorDecoration.getY() / 128 + ctx?.client!!.getBaseY(),
                        sceneryObject?.getPlane() ?: 0,ctx

                )
                else -> Tile(-1, -1, ctx = ctx)
            }
        }else{
            return Tile()
        }
    }


    override fun isOnScreen(): Boolean {
        return ctx?.let { Calculations.isOnscreen(it,getConvexHull().bounds ) } ?: false
    }

    fun getTriangles(): ArrayList<Polygon> {

        return if (model != null) {
            val positionInfo =
                    objectPositionInfo

            val modelTriangles =
                    getTrianglesFromModel(positionInfo, model!!, ctx!!)

            modelTriangles
        } else {
            ArrayList()
        }
    }

    fun getConvexHull(): Polygon {
        val positionInfo = objectPositionInfo
        return when {
            sceneryObject != null -> {

                var model = sceneryObject.getEntity()
                if (!(model is Model)) {
                    model = model.getModel()
                }
                getConvexHullFromModel(positionInfo, model as Model, ctx!!)
            }
            wallObject != null -> getConvexHullFromModel(positionInfo, wallObject.getEntity1() as Model, ctx!!)
            floorDecoration != null -> getConvexHullFromModel(positionInfo, floorDecoration.getEntity() as Model, ctx!!)
            else -> Polygon()
        }
    }

    fun transform(definition: LocType): LocType? {
        var var1 = -1
        val transforms = definition.getMulti()

        if (definition.getMultiVarbit() != -1) {
            var1 = this.ctx?.client?.getVarbit(definition.getMultiVarbit())!!
        } else if (definition.getMultiVar() != -1) {
            var1 = this.ctx?.client?.getVarps_main()!![definition.getMultiVar()]
        }

        val var2 = if (var1 >= 0 && var1 < transforms.lastIndex) {
            transforms[var1]
        } else {
            transforms[transforms.lastIndex]
        }

        return if (var2 == -1)
            null
        else
            getObjectComposite(var2)
    }

    fun getObjectComposite(
            gameObjectId: Int
    ): LocType? {
        this.ctx!!.client.getLocType_cached().getHashTable().getBuckets().iterator().forEach { bucketItem ->
            if (bucketItem != null) {
                var locType = bucketItem.getNext()
                while (locType != null && locType is LocType) {
                    if (locType.getKey().toInt() == gameObjectId) {
                        var definition = locType as LocType?
                        if (locType.getMulti() != null) {
                            definition = transform(locType)
//                            println("Found Object Transform | ${definition?.getName()} | Actions: ${definition?.getOp()?.contentToString()}")
                        } else {
//                            println("Found Object Definition | ${locType.getName()} | Actions: ${locType.getOp().contentToString()}")
                        }
                        return definition
                    }
                    locType = locType.getNext()
                }
            }
        }
        return null
    }

}
