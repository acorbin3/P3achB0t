package com.p3achb0t.ui.components

import com.p3achb0t.RSLoader
import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.Client
import com.p3achb0t.ui.Keyboard
import com.p3achb0t.util.JarLoader

import java.applet.Applet
import java.awt.Dimension

enum class ClientState {
    RUNNING, PAUSED, STOPPED
}

class ClientInstance {
        //var user: User?
    var keyboard: Keyboard? = null
    val mouse = Mouse()
    private var state: ClientState = ClientState.STOPPED
    private var applet: Applet? = null
    var client: com.p3achb0t._runestar_interfaces.Client? = null
    var script: AbstractScript? = null



    init {
        val clientClazz = JarLoader.load("./app/gamepack-181-injected.jar","client")
        val game: Applet = clientClazz as Applet
        applet = game
        // set script here
        Client.client = clientClazz as com.p3achb0t._runestar_interfaces.Client
        client = clientClazz
        //api = GameApi()
        //applet?.
        game.setStub(RSLoader(83))
        game.preferredSize = Dimension(765, 503)
    }

    fun getApplet() : Applet {
        return applet!!
    }

}

/*
init {
        val loader = Loader() // Should maybe be static and check when the applet is launched
        val gamePackWithPath = loader.run()

        //runeStar = RuneStarAnalyzer()
        //runeStar?.loadHooks()

        val gamePackJar = JarFile(gamePackWithPath)
        //runeStar?.parseJar(gamePackJar)

        val analyser = Analyser()
        //analyser.parseJar(gamePackJar, runeStar)


        Parameters(83)
        println("Starting Client")
        val path = System.getProperty("user.dir")
        val filePathToInjected = "$path/injected_jar.jar"
        val file = File(filePathToInjected)
        val urlArray: Array<URL> = Array(1, init = { file.toURI().toURL() })
        println(file.toURI().toURL())
        classLoader = URLClassLoader(urlArray)
        val clientClazz = classLoader?.loadClass("applet")?.newInstance()
        val game: Applet = clientClazz as Applet
        applet = game
        Client.applet = clientClazz as com.p3achb0t._runestar_interfaces.Client
        this.clientClazz = clientClazz
        game.apply {
            preferredSize = Dimension(765, 503)
            val loader2 = RSLoader()
            game.setStub(loader2)
        }

    }
 */
