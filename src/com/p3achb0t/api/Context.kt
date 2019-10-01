package com.p3achb0t.api

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t.api.user_inputs.Camera
import com.p3achb0t.api.user_inputs.Keyboard
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.GroundItems
import com.p3achb0t.api.wrappers.NPCs
import com.p3achb0t.api.wrappers.Players
import java.applet.Applet

class Context(val obj: Any) {


    val applet: Applet
    var selectedWidget: Component? = null

    val client: Client
    val players: Players
    val groundItems: GroundItems
    val npcs: NPCs
    val mouse: Mouse
    val keyboard: Keyboard
    val camera: Camera

    init {
        client = obj as Client
        mouse = Mouse(obj)
        keyboard = Keyboard(obj)
        applet = obj as Applet
        camera = Camera(this)
        npcs = NPCs(this)
        players = Players(this)
        groundItems = GroundItems(this)

    }
}

