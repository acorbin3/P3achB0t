package com.p3achb0t.client

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.AbstractScript
import com.p3achb0t.client.loader.ConfigReader
import com.p3achb0t.client.loader.RSAppletStub
import com.p3achb0t.client.configs.Constants
import com.p3achb0t.client.interfaces.io.Keyboard
import com.p3achb0t.client.interfaces.io.Mouse
import com.p3achb0t.client.managers.scripts.LoadDebugScripts
import com.p3achb0t.client.managers.scripts.LoadScripts
import com.p3achb0t.client.util.JarLoader
import com.p3achb0t.interfaces.IScriptManager
import com.p3achb0t.interfaces.ScriptManager
import com.p3achb0t.scripts.WidgetExplorerDebug

import java.applet.Applet
import java.awt.Dimension
import java.util.*

enum class ClientState {
    RUNNING, PAUSED, STOPPED
}

class Bot(world: Int) {
    val loadedDebugScripts = LoadDebugScripts()
    val loadScripts = LoadScripts()
    val id = UUID.randomUUID().toString()
    private lateinit var o: Any
    private val applet: Applet
    private val client: Client
    private val manager: IScriptManager

    /**
     * Constructor
     */
    init {
        val configReader = ConfigReader(world)
        val map = configReader.read()

        val clientClazz = JarLoader.load("./${Constants.APPLICATION_CACHE_DIR}/${Constants.INJECTED_JAR_NAME}","client")
        if (clientClazz != null) {
            o = clientClazz
        }
        client = clientClazz as Client
        applet = clientClazz as Applet
        manager = clientClazz as IScriptManager
        val appletStub = RSAppletStub(map)
        appletStub.appletContext.setApplet(applet)
        applet.setStub(appletStub)
        appletStub.appletResize(800,600);
        applet.preferredSize = Dimension(800,600) // needs to be here
        applet.setSize(800,600)// = Dimension(800,600)
        applet.validate()
        applet.init()
        applet.validate()
    }

    fun getScriptManager() : ScriptManager {
        return manager.getManager()
    }

    fun getKeyboard() : Keyboard {
        return manager.getKeyboard()
    }

    fun getMouse() : Mouse {
        return manager.getMouse()
    }

    fun getApplet() : Applet {
        return applet
    }

    fun getClient() : Client {
        return client
    }

    fun setScript(name: AbstractScript) {
        manager.getManager().setScript(name)
    }

    fun getScript() : AbstractScript {
        return manager.getManager().getScript()
    }

    fun startScript() {
        manager.getManager().start()
    }

    fun stopScript() {
        manager.getManager().stop()
    }

    fun resumeScript() {
        manager.getManager().resume()
    }

    fun pauseScript() {
        manager.getManager().pause()
    }

    fun addDebugScript(name: String) {
        val debugScript = loadedDebugScripts.getScript(name)
        manager.getManager().addDebugPaint(debugScript)
    }

    fun removeDebugScript(name: String) {
        val debugScript = loadedDebugScripts.getScript(name)
        manager.getManager().removeDebugPaint(debugScript)
    }
}
