package com.p3achb0t.client

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.client.loader.ConfigReader
import com.p3achb0t.client.loader.RSAppletStub
import com.p3achb0t.ui.components.Constants
import com.p3achb0t.util.JarLoader

import java.applet.Applet

enum class ClientState {
    RUNNING, PAUSED, STOPPED
}

class ClientInstance {

    //var keyboard = Keyboard()
    var category = ""
    var name = ""
    var author = ""
    var state: ClientState = ClientState.STOPPED
    private var applet: Applet
    var client: Client
    var script: AbstractScript? = null



    init {
        val configReader = ConfigReader()
        val map = configReader.read()


        println("./${Constants.APPLICATION_CACHE_DIR}/${Constants.INJECTED_JAR_NAME}")
        val clientClazz = JarLoader.load("./${Constants.APPLICATION_CACHE_DIR}/${Constants.INJECTED_JAR_NAME}","client")
        client = clientClazz as Client
        applet = clientClazz as Applet

        val appletStub = RSAppletStub(map)
        // Use our setter to set the Applet in the AppletContext
        appletStub.getAppletContext().setApplet(applet)
        // Set the AppletStub of the Applet
        applet.setStub(appletStub)
        // Turn the key and start the Applet up
        //applet.setSize(765, 503)
        //applet.init()


        appletStub.setActive(true)

        //applet.setStub(RSLoader(83))
        //applet.preferredSize = Dimension(765, 503)
    }

    fun getApplet() : Applet {
        return applet
    }

    fun run() {
        applet.init()

        applet.setSize(765, 503)
        //appletStub.setActive(true)
    }
}
