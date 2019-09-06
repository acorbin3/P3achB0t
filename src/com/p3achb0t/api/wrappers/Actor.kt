package com.p3achb0t.api.wrappers

import com.p3achb0t._runestar_interfaces.Actor
import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.Utils
import com.p3achb0t.api.user_inputs.Keyboard
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.interfaces.Interactable
import com.p3achb0t.api.wrappers.interfaces.Locatable
import kotlinx.coroutines.delay
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import kotlin.random.Random


open class Actor(
        var raw: Actor,
        client: Client,
        mouse: Mouse?,
        override var loc_client: Client? = client,
        override var loc_keyboard: Keyboard? = null

) : Locatable , Interactable(client, mouse){

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
        return if(client != null)Calculations.worldToScreen(region.x, region.y, raw.getHeight(),client) else Point(-1,-1)
    }

    fun isIdle(): Boolean {
        return raw.getSequence() == -1 && raw.getTargetIndex() == -1 && raw.getMovementSequence() == 808
    }

    suspend fun waitTillIdle(time: Int = 4) {
        //Add a small delay to allow for initial movement from the previous command
        delay(Random.nextLong(100, 400))
        Utils.waitFor(time, object : Utils.Condition {
            override suspend fun accept(): Boolean {
                delay(100)
                return if(client != null) Players(client).getLocal().isIdle() else return false
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

        val tilePoly = client?.let {
            Calculations.getCanvasTileAreaPoly(
                    it,
                raw.getX(),
                raw.getY()
        )
        }
        return client?.let { tilePoly?.bounds?.let { it1 -> Calculations.isOnscreen(it, it1) } } ?: false
    }


    override fun draw(g: Graphics2D) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun draw(g: Graphics2D, color: Color) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGlobalLocation(): Tile {
        return if(client != null) Tile(
            (raw.getX() shr 7) + client.getBaseX(),
            (raw.getY() shr 7) + client.getBaseY(),
            client.getPlane()
        )
        else
            Tile(0,0,0)
    }

}