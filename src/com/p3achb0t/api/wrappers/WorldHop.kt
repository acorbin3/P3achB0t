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
        val worldListSortAscendingButtonIndex = Widget.WidgetIndex("69", "11")
        val listUpButtonChildID = 4
        val listDownButtonChildID = 5
        val listScrollClickableAreaChildID = 0
        val logoutDialog = Widget.WidgetIndex("182", "0")
        val yellowXButton = Widget.WidgetIndex("164", "32")
        val worldSwitchButtonIndex = Widget.WidgetIndex("182", "3")
        val selectYesWorldHop = Widget.WidgetIndex("219", "1")

        val selectYesWorldHopChild = 1
        //[(182,8),(182,1),(182,0),(164,80),(164,69),(164,66),
    }

    val daysOfMembersLeft: Int
    get(){
        return ctx.vars.getVarp(1780)
    }


    val isLoggedIn: Boolean
        get() {
            return GameState.currentState(ctx) == GameState.LOGGED_IN
        }

    val worldListContainer: WidgetItem
        get() {
            return WidgetItem(ctx.widgets.find(worldListContainerIndex.parentID.toInt(), worldListContainerIndex.childID.toInt()), ctx = ctx)
        }
    val worldListSortAscendingButton: WidgetItem
        get(){
            return WidgetItem(ctx.widgets.find(worldListSortAscendingButtonIndex.parentID.toInt(), worldListSortAscendingButtonIndex.childID.toInt()), ctx = ctx)
        }
    val worldListScrollBar: WidgetItem
        get() {
            return WidgetItem(ctx.widgets.find(listScrollBar.parentID.toInt(), listScrollBar.childID.toInt())?.getChildren()?.get(listScrollClickableAreaChildID), ctx = ctx)
        }
    val worldListLogoutDoor: WidgetItem
    get(){
        return WidgetItem(ctx.widgets.find(69, 23), ctx = ctx)
    }
    val redLogoutButton: WidgetItem
        get(){
            return WidgetItem(ctx.widgets.find(182, 12), ctx = ctx)
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

//                println("ID\tProp\thost\tactv\tindex")
                worlds.forEach {
//                    println("${it.getId()}\t${it.getProperties()}\t${it.getHost()}\t${it.getActivity()}\t${it.getIndex()}\t")
                    if (it.getProperties() == 1 && !it.getActivity().contains("Leagues")) {
                        MembersWorlds.add(it.getId())
                    }
                }
            } catch (e: Exception) {

            }
            return MembersWorlds
        }

    fun getWorldWidget(world: Int): WidgetItem {
        return WidgetItem(worldList?.getChildren()?.get(world),index = world, ctx = ctx)
    }

    //417 this is PVP
//    val freeWorldNoTotalRequirement = intArrayOf(301, 308, 316, 326, 335, 379, 380, 382, 383, 384, 393, 394, 397, 398, 399, 418, 425, 426, 430, 431,
//            433, 434, 435, 436, 437,
//            //438, 439, 440, leagues
//            451, 452, 453, 454, 455, 456,
//            // 457, 458, 459, leagues
//            469, 470, 471, 472, 473,
//            // 474, 475, 476, leagues
//            497, 498, 499, 500, 501,
//            //502, 503, 504, leagues
//    )

    val freeWorldNoTotalRequirement: ArrayList<Int>
        get() {
            val worldList = ArrayList<Int>()
            try {
                var worlds = ctx.client.getWorlds()

                worlds.forEach {
                    if (it.getProperties() == 0 && !it.getActivity().contains("Leagues")) {
                        worldList.add(it.getId())
                    }
                }
            } catch (e: Exception) {

            }
            return worldList
        }

    fun isWelcomeRedButtonAvailable(): Boolean{
        return ctx.widgets.find(378,78)?.getIsHidden() == false
    }

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

            //Make sure the world list is in the correct sorted order
            if(ctx.vars.getVarbit(4596) != 0){
                worldListSortAscendingButton.click()
                Utils.sleepUntil({ctx.vars.getVarbit(4596) == 0})
            }

            if(ctx.vars.getVarbit(4596) == 0){
                println("World list in correctly sorted order")
            }

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
            }else{
                println("Couldnt find world, lets pick a random point on the list so we can reset the scroll bar")
                val position = Point(worldListScrollBar.area.x + Random.nextInt(0, worldListScrollBar.area.width), worldListScrollBar.area.y + Random.nextInt(worldListScrollBar.area.height))
                ctx.mouse.click(position)
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

        if(isLoggedIn){
            if(isWorldListOpened){
                worldListLogoutDoor.click()
                sleep(Random.nextLong(600, 1200))
            }
            else if (!isLogoutMenuOpen) {
                println("Clicking on big yellow x")
                widgetYellowXButton.click()
                sleep(Random.nextLong(600, 1200))
            }
            if(isLogoutMenuOpen){
                println("Pressing red logout button")
                redLogoutButton.click()
            }
        }
    }


    fun isMember(): Boolean {
        return ctx.client.getIsMembersWorld()
    }
}
