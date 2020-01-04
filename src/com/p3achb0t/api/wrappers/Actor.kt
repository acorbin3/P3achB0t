package com.p3achb0t.api.wrappers

import com.p3achb0t._runestar_interfaces.Actor
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.Context
import com.p3achb0t.api.Utils
import com.p3achb0t.api.wrappers.interfaces.Interactable
import com.p3achb0t.api.wrappers.interfaces.Locatable
import kotlinx.coroutines.delay
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import kotlin.random.Random


open class Actor(
        var raw: Actor,
        ctx: Context,
        override var loc_ctx: Context? = ctx

) : Locatable , Interactable(ctx) {

    val x: Int
        get() {
            return raw.getX()
        }
    val y: Int
        get() {
            return raw.getY()
        }
    val npcCycle: Int
        get() {
            return raw.getNpcCycle()
        }
    val overheadText: String
        get() {
            return raw.getOverheadText()
        }

    override fun getInteractPoint(): Point {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isMouseOverObj(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun clickOnMiniMap(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getNamePoint(): Point {
        val region = getRegionalLocation()
        return if(ctx?.client != null)Calculations.worldToScreen(region.x, region.y, raw.getHeight(),ctx!!) else Point(-1,-1)
    }

    fun isIdle(): Boolean {
        return raw.getSequence() == -1 && raw.getTargetIndex() == -1 && raw.getMovementSequence() == 808
    }

    suspend fun waitTillIdle(time: Int = 4) {
        //Add a small delay to allow for initial movement from the previous command
        delay(Random.nextLong(450, 700))
        Utils.waitFor(time, object : Utils.Condition {
            override suspend fun accept(): Boolean {
                //Need to make sure we are idle for at least 200ms
                if (ctx!!.players.getLocal().isIdle())
                    delay(100)
                delay(100)
                return if (ctx != null && ctx?.client != null) ctx!!.players.getLocal().isIdle() else return false
            }
        })
    }

    //TODO - fix getting health
//    fun getHealth(): Int {
//        println("Looking for healthbars")
//        var node = raw.getHeadbars().getCurrent()
//        if (raw.getHeadbars().getCurrent() is HealthBar) {
//            println("Found healthbar")
//        }
//        if (node == null) {
//            println("Node is null")
//        }
//
//        var count = 0
//        while (node != null) {
//            println("Looking at node $count")
//            if (node is Health) {
//                println("Found health Bar")
//                var healthData = node.getData().getNode()
//                while (healthData != null && healthData != node.getData()) {
//                    if (healthData is HealthBarData) {
//                        println("Found Health bar data")
//                        return healthData.getCurrentHealth()
//                    }
//                    healthData = healthData.getNext()
//                }
//
//            }
//            count += 1
//            node = node.getNext()
//        }
//        return 0
//    }
    override fun isOnScreen(): Boolean {

        val tilePoly = ctx?.let {
            Calculations.getCanvasTileAreaPoly(
                    it,
                raw.getX(),
                raw.getY()
        )
        }
        return ctx?.let { tilePoly?.bounds?.let { it1 -> Calculations.isOnscreen(it, it1) } } ?: false
    }


    override fun draw(g: Graphics2D) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun draw(g: Graphics2D, color: Color) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGlobalLocation(): Tile {
        return if(ctx?.client != null) Tile(
                (raw.getX() shr 7) + ctx!!.client.getBaseX(),
                (raw.getY() shr 7) + ctx!!.client.getBaseY(),
                ctx!!.client.getPlane(),ctx

        )
        else
            Tile(0, 0, 0, ctx)
    }

}