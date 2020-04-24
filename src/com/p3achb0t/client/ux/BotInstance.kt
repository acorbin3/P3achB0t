package com.p3achb0t.client.ux

import com.p3achb0t.client.accounts.Account
import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.injection.InstanceManager
import com.p3achb0t.client.injection.InstanceManagerInterface
import com.p3achb0t.client.loader.JarLoader
import java.applet.Applet
import java.awt.BorderLayout
import java.util.*
import javax.swing.JPanel


class BotInstance(account: Account = Account()) : JPanel() {
    // add the canvas to this JPanel

    var sessionToken: String = ""
    private lateinit var applet: Applet
    lateinit var instanceManagerInterface: InstanceManagerInterface

    init {
        this.layout = BorderLayout()
        JarLoader.load(account)?.let {
            applet = it
            sessionToken = account.uuid
            instanceManagerInterface = applet as InstanceManagerInterface
            add(applet) // add the game to the JPanel
            GlobalStructs.botManager.botTabBar.addBotInstance("${account.username}-${account.proxy}", sessionToken, this)
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