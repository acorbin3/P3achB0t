package com.p3achb0t.client

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.AbstractScript
import com.p3achb0t.client.configs.Constants
import com.p3achb0t.client.interfaces.io.Keyboard
import com.p3achb0t.client.interfaces.io.Mouse
import com.p3achb0t.client.loader.ConfigReader
import com.p3achb0t.client.loader.RSAppletStub
import com.p3achb0t.client.managers.Manager
import com.p3achb0t.client.managers.accounts.Account
import com.p3achb0t.client.util.JarLoader
import com.p3achb0t.interfaces.IScriptManager
import com.p3achb0t.interfaces.ScriptManager
import java.applet.Applet
import java.awt.Dimension
import java.util.*

enum class ClientState {
    RUNNING, PAUSED, STOPPED
}

class Bot(var world: Int, var clientManager: Manager, account: Account = Account()) {

    val id = UUID.randomUUID().toString()
    private val applet: Applet
    private val client: Client
    private val iScriptManager: IScriptManager

    /**
     * Constructor
     */
    init {
        var proxy = "none"
        if(account.world > 0) {
            world = account.world
        }
        if(account.proxy.isNotEmpty()) {
            proxy = account.proxy
        }
        val configReader = ConfigReader(world)
        val map = configReader.read()

        val clientClazz = JarLoader.load(
                "./${Constants.APPLICATION_CACHE_DIR}/${Constants.INJECTED_JAR_NAME}",
                "client",
                proxy
        )
        client = clientClazz as Client
        applet = clientClazz as Applet
        iScriptManager = clientClazz as IScriptManager
        iScriptManager.getManager().setLoginHandlerAccount(account)
        println("Tab:$account)")
        println("Account: ${iScriptManager.getManager().loginHandler.account}")

        val appletStub = RSAppletStub(map)
        appletStub.appletContext.setApplet(applet)
        applet.setStub(appletStub)
        appletStub.appletResize(800,600)
        applet.preferredSize = Dimension(800,600) // needs to be here
        applet.setSize(800,600)// = Dimension(800,600)
        applet.validate()
        applet.init()
        applet.validate()



    }

    fun getScriptManager() : ScriptManager {
        return iScriptManager.getManager()
    }

    fun getKeyboard() : Keyboard {
        return iScriptManager.getKeyboard()
    }

    fun getMouse() : Mouse {
        return iScriptManager.getMouse()
    }

    fun getApplet() : Applet {
        return applet
    }

    fun getClient() : Client {
        return client
    }

    fun setScript(name: AbstractScript) {
        iScriptManager.getManager().setUpScript(name)
    }

    fun getScript() : AbstractScript {
        return iScriptManager.getManager().script
    }

    fun startScript() {
        iScriptManager.getManager().start()
    }

    fun stopScript() {
        iScriptManager.getManager().stop()
    }

    fun resumeScript() {
        iScriptManager.getManager().resume()
    }

    fun pauseScript() {
        iScriptManager.getManager().pause()
    }

    fun addDebugScript(name: String) {
        val debugScript = clientManager.loadedDebugScripts.getScript(name)
        iScriptManager.getManager().addDebugPaint(debugScript)
    }

    fun removeDebugScript(name: String) {
        val debugScript = clientManager.loadedDebugScripts.getScript(name)
        iScriptManager.getManager().removeDebugPaint(debugScript)
    }
}
