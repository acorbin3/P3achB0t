package com.p3achb0t.client

import com.p3achb0t.RSLoader
import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.ui.Keyboard
import com.p3achb0t.util.JarLoader

import java.applet.Applet
import java.awt.Dimension

enum class ClientState {
    RUNNING, PAUSED, STOPPED
}

class ClientInstance {

    //var keyboard = Keyboard()
    var category = ""
    var name = ""
    var author = ""
    val mouse = Mouse()
    var state: ClientState = ClientState.STOPPED
    private var applet: Applet
    var client: Client
    var script: AbstractScript? = null



    init {
        val clientClazz = JarLoader.load("./app/gamepack-181-injected.jar","client")
        client = clientClazz as Client
        applet = clientClazz as Applet



        applet.setStub(RSLoader(83))
        applet.preferredSize = Dimension(765, 503)
    }

    fun getApplet() : Applet {
        return applet
    }
}
