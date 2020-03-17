package com.p3achb0t.client.ux

import com.p3achb0t.api.interfaces.Client
import com.p3achb0t.client.configs.Constants
import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.injection.InstanceManager
import com.p3achb0t.client.injection.InstanceManagerInterface
import com.p3achb0t.client.loader.ConfigReader
import com.p3achb0t.client.loader.RSAppletStub
import com.p3achb0t.client.loader.JarLoader
import java.applet.Applet
import java.awt.Dimension
import java.util.*
import javax.swing.JPanel

class BotInstance : JPanel() {
    // add the canvas to this JPanel

    private var applet: Applet? = null
    private var client: Client? = null
    var instanceManager: InstanceManagerInterface? = null
    val sessionToken: String = UUID.randomUUID().toString()

    init {
        // JPanel setup
        minimumSize = Dimension(GlobalStructs.width, GlobalStructs.height)

    }

    fun initBot() {
        val configReader = ConfigReader(80)
        val map = configReader.read()

        val loadedClient = JarLoader.load(
                "./${Constants.APPLICATION_CACHE_DIR}/${Constants.INJECTED_JAR_NAME}",
                "client",
                "none"
        )
        client = loadedClient as Client
        applet = loadedClient as Applet

        instanceManager = loadedClient as InstanceManagerInterface


        add(applet) // add the game to the JPanel

        GlobalStructs.botTabBar.addBotInstance(sessionToken, this)

        val appletStub = RSAppletStub(map)
        appletStub.appletContext.setApplet(applet!!)
        appletStub.appletResize(GlobalStructs.width, GlobalStructs.height) // size of the game

        applet!!.setStub(appletStub)
        applet!!.preferredSize = Dimension(GlobalStructs.width, GlobalStructs.height)
        applet!!.init()


    }

    fun kill() {
        applet!!.destroy()
        GlobalStructs.botTabBar.killBotInstance(sessionToken)
    }

    fun getInstanceManager(): InstanceManager {
        return instanceManager!!.getManager()
    }


    override fun getMinimumSize(): Dimension {
        return Dimension(765, 503)
    }

    override fun getPreferredSize(): Dimension {
        return Dimension(GlobalStructs.width, GlobalStructs.height)
    }

}