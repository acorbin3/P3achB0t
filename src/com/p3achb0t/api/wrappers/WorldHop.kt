package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.interfaces.Component
import com.p3achb0t.api.userinputs.DoActionParams
import com.p3achb0t.api.utils.Time.sleep
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.Widget
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay
import java.awt.Point
import kotlin.random.Random

class WorldHop(val ctx: Context) {
    companion object {
        enum class World(val region: Int) {
            UK(1),
            USA(0),
            AUS(3),
            GER(7),

        }

        val worldListContainerIndex = Widget.WidgetIndex("69", "15")
        val worldListParentIndex = Widget.WidgetIndex("69", "16")
        val listScrollBar = Widget.WidgetIndex("69", "18")
        val listUpButtonChildID = 4
        val listDownButtonChildID = 5
        val listScrollClickableAreaChildID = 0
        val logoutDialog = Widget.WidgetIndex("182", "0")
        val yellowXButton = Widget.WidgetIndex("164", "32")
        val worldSwitchButtonIndex = Widget.WidgetIndex("182", "7")
        val selectYesWorldHop = Widget.WidgetIndex("219", "1")
        val selectYesWorldHopChild = 1
        //[(182,8),(182,1),(182,0),(164,80),(164,69),(164,66),
    }

    val isLoggedIn: Boolean
        get() {
            return GameState.currentState(ctx) == GameState.LOGGED_IN
        }

    val worldListContainer: WidgetItem
        get() {
            return WidgetItem(ctx.widgets.find(worldListContainerIndex.parentID.toInt(), worldListContainerIndex.childID.toInt()), ctx = ctx)
        }
    val worldListScrollBar: WidgetItem
        get() {
            return WidgetItem(ctx.widgets.find(listScrollBar.parentID.toInt(), listScrollBar.childID.toInt())?.getChildren()?.get(listScrollClickableAreaChildID), ctx = ctx)
        }
    val widgetYellowXButton: WidgetItem
        get() {
            return WidgetItem(ctx.widgets.find(yellowXButton.parentID.toInt(), yellowXButton.childID.toInt()), ctx = ctx)
        }
    val widgetWorldSwitchButton: WidgetItem
        get() {
            return WidgetItem(ctx.widgets.find(worldSwitchButtonIndex.parentID.toInt(), worldSwitchButtonIndex.childID.toInt()), ctx = ctx)
        }
    val worldList: Component?
        get() {
            return ctx.widgets.find(worldListParentIndex.parentID.toInt(), worldListParentIndex.childID.toInt())
        }
    val isWorldListOpened: Boolean
        get() {
            return widgetYellowXButton.widget != null
                    && widgetYellowXButton.widget?.getSpriteId2() ?: -1 > 0
                    && worldList != null
                    && worldList?.getCycle()?.minus(ctx.client.getCycle())?.let { kotlin.math.abs(it) } ?: 101 < 100
        }
    val isLogoutMenuOpen: Boolean
        get() {
            return widgetYellowXButton.widget != null
                    && widgetYellowXButton.widget?.getSpriteId2() ?: -1 > 0
                    && ctx.widgets.find(logoutDialog.parentID.toInt(), logoutDialog.childID.toInt())?.getCycle()?.minus(ctx.client.getCycle())?.let { kotlin.math.abs(it) } ?: 101 < 100
        }

    val widgetSelectYesWorldHop: WidgetItem
        get() {
            return WidgetItem(ctx.widgets.find(selectYesWorldHop.parentID.toInt(), selectYesWorldHop.childID.toInt())?.getChildren()?.get(selectYesWorldHopChild), ctx = ctx)
        }
    val membersWorlds: ArrayList<Int>
        get() {
            val MembersWorlds = ArrayList<Int>()
            try {
                var worlds = ctx.client.getWorlds()

                worlds.forEach {
                    if (it.getProperties() == 1) {
                        MembersWorlds.add(it.getId())
                    }
                }
            } catch (e: Exception) {

            }
            return MembersWorlds
        }

    fun getWorldWidget(world: Int): WidgetItem {
        return WidgetItem(worldList?.getChildren()?.get(world), ctx = ctx)
    }

    val freeWorldNoTotalRequirement = intArrayOf(301, 308, 316, 326, 335, 379, 380, 382, 383, 384, 393, 394, 397, 398, 399, 417, 418, 425, 426, 430, 431,
            433, 434, 435, 436, 437, 438, 439, 440, 451, 452, 453, 454, 455, 456, 457, 458, 459, 469, 470, 471, 472, 473, 474, 475, 476, 497, 498, 499, 500, 591, 502, 503, 504)

    fun isWorldVisible(world: Int): Boolean {


        val worldWidget = getWorldWidget(world)
        return worldListContainer.area.contains(worldWidget.area)

    }

    suspend fun hopRandomP2p() {

        if(membersWorlds.isEmpty()){
            openWorldList()
        }
        if(membersWorlds.isNotEmpty()) {
            val randWorld = membersWorlds.random()
            hopWorld(randWorld)
        }

    }

    suspend fun hopWorld(selectedWorld: Int) {

        println("Hopping to world $selectedWorld")
        //Open up world list
        openWorldList()

        if (isWorldListOpened) {
            var worldWidget = getWorldWidget(selectedWorld)
            var directionUp = false
            //Check which direction to scroll
            directionUp = worldWidget.getBasePoint().y < worldListContainer.getBasePoint().y
            println("World widget ${worldWidget.getBasePoint()} Container = ${worldListContainer.getBasePoint()} up?: = $directionUp")

            //calculate position on scroll bar
            var worldCount = 0
            var worldIndex = 0
            worldList?.getChildren()?.iterator()?.withIndex()?.forEach {

                if (!it.value.getIsHidden()) {
                    worldCount += 1
                }
                if (it.index == selectedWorld) {
                    worldIndex = worldCount
                }
            }


            //total distance / numb items == increment
            val increment = worldListScrollBar.area.height / worldCount.toDouble()
            // find index.
            // index * increment for y position + y offset
            val scrollOffset = (worldIndex * increment).toInt()

            val position = Point(worldListScrollBar.area.x + Random.nextInt(0, worldListScrollBar.area.width), worldListScrollBar.area.y + scrollOffset)

            println("WorldCount = $worldCount WorldIndex= $worldIndex, increment = $increment scrollOffset=$scrollOffset position: $position")
            ctx.mouse.click(position)

            if (isWorldVisible(selectedWorld)) {
                worldWidget = getWorldWidget(selectedWorld)
                worldWidget.click()
                sleep(Random.nextLong(1_000, 2_000))
                widgetSelectYesWorldHop.click()
                sleep(Random.nextLong(8_000, 10_000))
            }
        }
    }

    private suspend fun openWorldList() {
        if (!isWorldListOpened) {
            if (!isLogoutMenuOpen) {
                println("Clicking on big yellow x")
                widgetYellowXButton.click()
                sleep(Random.nextLong(600, 1200))
            }
            if (isLogoutMenuOpen) {
                println("Clicking on the button for world list")
                widgetWorldSwitchButton.click()
                sleep(Random.nextLong(600, 1200))
            }
        }
    }


    fun getCurrent(): Int {
        var World = 0
        try {
            World = ctx.client.getWorldId()
        } catch (e: Exception) {
            println("World Exception")
        }
        return World
    }


    fun getMembershipDays(): Int {
        return ctx.vars.getVarbit(1780)
    }


    suspend fun logout() {
        this.ctx.mouse.doAction(DoActionParams(-1, 11927560, 57, 1, "Logout", "", 0, 0))
        delay(400)

        Utils.waitFor(10, object : Utils.Condition {
            override suspend fun accept(): Boolean {
                delay(50)
                return GameState.currentState(ctx) == GameState.LOGIN_SCREEN
            }
        })
        if (GameState.currentState(ctx) == GameState.LOGGED_IN) {
            logout()
        }
    }


    fun isMember(): Boolean {
        return ctx.client.getIsMembersWorld()
    }
}
