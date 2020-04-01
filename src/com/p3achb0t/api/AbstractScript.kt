package com.p3achb0t.api

import com.p3achb0t.api.userinputs.Camera
import com.p3achb0t.api.userinputs.Keyboard
import com.p3achb0t.api.userinputs.Mouse
import com.p3achb0t.api.utils.Logging
import com.p3achb0t.api.wrappers.*
import com.p3achb0t.api.wrappers.quests.QuestData
import com.p3achb0t.api.wrappers.tabs.*
import com.p3achb0t.api.wrappers.widgets.Widgets
import java.awt.Graphics

abstract class AbstractScript: Logging() {

    lateinit var ctx: Context
    var validate: Boolean = false

    abstract suspend fun loop()
    abstract suspend fun start()
    abstract fun stop()
    open fun draw(g: Graphics) {}

    val localPlayer : Player
        get() {
            return ctx.players.getLocal()
        }

    val players : Players
        get()  {
            return ctx.players
        }


    val groundItems : GroundItems
        get()  {
            return ctx.groundItems
        }

    val npcs : NPCs
        get()  {
            return ctx.npcs
        }

    val mouse : Mouse
        get()  {
            return ctx.mouse
        }

    val keyboard : Keyboard
        get()  {
            return ctx.keyboard
        }

    val camera : Camera
        get()  {
            return ctx.camera
        }

    val bank : Bank
        get()  {
            return ctx.bank
        }

    val clientMode : ClientMode
        get()  {
            return ctx.clientMode
        }

    val dialog : Dialog
        get()  {
            return ctx.dialog
        }

    val gameObjects : GameObjects
        get()  {
            return ctx.gameObjects
        }

    val items : Items
        get()  {
            return ctx.items
        }

    val menu : Menu
        get()  {
            return ctx.menu
        }

    val miniMap : MiniMap
        get()  {
            return ctx.miniMap
        }

    val equipment : Equipment
        get()  {
            return ctx.equipment
        }

    val inventory : Inventory
        get()  {
            return ctx.inventory
        }

    val magic : Magic
        get()  {
            return ctx.magic
        }

    val prayer : Prayer
        get()  {
            return ctx.prayer
        }

    val tabs : Tabs
        get()  {
            return ctx.tabs
        }

    val widgets : Widgets
        get()  {
            return ctx.widgets
        }

    val vars : Vars
        get()  {
            return ctx.vars
        }

    val cache : Cache
        get()  {
            return ctx.cache
        }

    val run : Run
        get()  {
            return ctx.run
        }

    val stats : Stats
        get()  {
            return ctx.stats
        }

    val questData : QuestData
        get()  {
            return ctx.questData
        }

    val projectiles : Projectiles
        get()  {
            return ctx.projectiles
        }

    val worldHop : WorldHop
        get()  {
            return ctx.worldHop
        }

    val grandExchange : GrandExchange
        get()  {
            return ctx.grandExchange
        }

    val trade : Trade
        get()  {
            return ctx.trade
        }

    val ipc : Channels
        get()  {
            return ctx.ipc
        }

}