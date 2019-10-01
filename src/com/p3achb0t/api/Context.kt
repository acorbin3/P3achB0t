package com.p3achb0t.api

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t.api.user_inputs.Keyboard
import com.p3achb0t.api.user_inputs.Mouse
import java.applet.Applet

class Context(val obj: Any) {

    val client: Client
    val mouse: Mouse
    val keyboard: Keyboard
    val applet: Applet
    var selectedWidget: Component? = null

    init {
        client = obj as Client
        mouse = Mouse(obj)
        keyboard = Keyboard(obj)
        applet = obj as Applet
    }
}

