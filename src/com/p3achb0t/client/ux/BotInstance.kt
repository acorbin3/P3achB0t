package com.p3achb0t.client.ux

import com.p3achb0t.api.interfaces.Client
import com.p3achb0t.client.configs.Constants
import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.injection.InstanceManager
import com.p3achb0t.client.injection.InstanceManagerInterface
import com.p3achb0t.client.loader.ConfigReader
import com.p3achb0t.client.loader.JarLoader
import com.p3achb0t.client.loader.RSAppletStub
import java.applet.Applet
import java.awt.BorderLayout
import java.awt.Dimension
import java.util.*
import javax.swing.JPanel

class BotInstance : JPanel() {
    // add the canvas to this JPanel

    var applet: Applet? = null
    private var client: Client? = null
    var instanceManagerInterface: InstanceManagerInterface? = null
    var sessionToken: String = ""
    var isRefreshed = false

    init {
        // JPanel setup
        minimumSize = Dimension(GlobalStructs.width, GlobalStructs.height)
        this.layout = BorderLayout()
    }


    fun initBot(username: String = "Bot", proxy: String="none", world: Int = 80, sessionID: String = UUID.randomUUID().toString()) {
        sessionToken = sessionID
        val configReader = ConfigReader(world)
        val map = configReader.read()
        val loadedClient = JarLoader.load(
                "./${Constants.APPLICATION_CACHE_DIR}/${Constants.INJECTED_JAR_NAME}",
                "client",
                proxy
        )
        client = loadedClient as Client
        applet = loadedClient as Applet

        instanceManagerInterface = loadedClient as InstanceManagerInterface
        // add uuid to the bot
        instanceManagerInterface?.getManager()?.instanceUUID = sessionToken
        

        add(applet) // add the game to the JPanel

        GlobalStructs.botTabBar.addBotInstance("$username-$proxy",sessionToken, this)

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
        return instanceManagerInterface!!.getManager()
    }

    override fun getMinimumSize(): Dimension {
        return Dimension(765, 503)
    }

    override fun getPreferredSize(): Dimension {
        return Dimension(GlobalStructs.width, GlobalStructs.height)
    }

}