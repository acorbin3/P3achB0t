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
        this.layout = BorderLayout()
    }


    fun initBot(username: String = "Bot", proxy: String="none", world: Int = 80, sessionID: String = UUID.randomUUID().toString()) {
        sessionToken = sessionID

        client = JarLoader.load("./${Constants.APPLICATION_CACHE_DIR}/${Constants.INJECTED_JAR_NAME}",
            "client", proxy) as Client

        applet = client as Applet
        val appletStub = RSAppletStub(ConfigReader(world).read())
        appletStub.appletContext.setApplet(applet!!)

        appletStub.appletResize(Constants.MIN_GAME_SIZE.getWidth().toInt(),
            Constants.MIN_GAME_SIZE.getHeight().toInt()) // size of the game

        applet!!.setStub(appletStub)
        applet!!.init()
        add(applet) // add the game to the JPanel

        instanceManagerInterface = client as InstanceManagerInterface
        // add uuid to the bot
        instanceManagerInterface?.getManager()?.instanceUUID = sessionToken

        GlobalStructs.botTabBar.addBotInstance("$username-$proxy", sessionToken, this)
    }

    fun kill() {
        applet!!.destroy()
        GlobalStructs.botTabBar.killBotInstance(sessionToken)
    }

    fun getInstanceManager(): InstanceManager {
        return instanceManagerInterface!!.getManager()
    }
}