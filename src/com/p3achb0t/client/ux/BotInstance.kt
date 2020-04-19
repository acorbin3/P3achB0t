package com.p3achb0t.client.ux

import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.injection.InstanceManager
import com.p3achb0t.client.injection.InstanceManagerInterface
import com.p3achb0t.client.loader.JarLoader
import java.applet.Applet
import java.awt.BorderLayout
import java.util.*
import javax.swing.JPanel


class BotInstance : JPanel() {
    // add the canvas to this JPanel

    var sessionToken: String = ""
    private lateinit var applet: Applet
    lateinit var instanceManagerInterface: InstanceManagerInterface

    init {
        // JPanel setup
        this.layout = BorderLayout()
    }


    fun initBot(username: String = "Bot", proxy: String="none", world: Int = 80, sessionID: String = UUID.randomUUID().toString()) {
        sessionToken = sessionID

        JarLoader.load(world, proxy)?.let {
            applet = it
            add(applet) // add the game to the JPanel

            instanceManagerInterface = applet as InstanceManagerInterface
            // add uuid to the bot
            instanceManagerInterface.getManager().instanceUUID = sessionToken

            GlobalStructs.botManager.botTabBar.addBotInstance("$username-$proxy", sessionToken, this)
        }
    }

    fun kill() {
        applet.destroy()
        GlobalStructs.botManager.botTabBar.killBotInstance(sessionToken)
    }

    fun getInstanceManager(): InstanceManager {
        return instanceManagerInterface.getManager()
    }
}