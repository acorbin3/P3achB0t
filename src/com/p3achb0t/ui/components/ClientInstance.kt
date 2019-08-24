package com.p3achb0t.ui.components

import com.p3achb0t.RSLoader
import com.p3achb0t.analyser.Analyser
import com.p3achb0t.analyser.runestar.RuneStarAnalyzer
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.Client
import com.p3achb0t.downloader.Parameters
import com.p3achb0t.loader.Loader
import com.p3achb0t.ui.Keyboard

import java.applet.Applet
import java.awt.Dimension
import java.awt.event.MouseEvent
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.JarFile

enum class ClientState {
    RUNNING, PAUSED, STOPPED
}

class ClientInstance {
        //var user: User?
        var script = 0 // placeholder
        var keyboard: Keyboard? = null
        var state: ClientState = ClientState.STOPPED
        var mouseEvent: MouseEvent? = null
        val mouse = Mouse()
        var client: Applet? = null
        var runeStar: RuneStarAnalyzer? = null
        var classLoader: URLClassLoader? = null
        var clientClazz: com.p3achb0t._runestar_interfaces.Client? = null


    init {
        val loader = Loader() // Should maybe be static and check when the client is launched
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
        val clientClazz = classLoader?.loadClass("client")?.newInstance()
        val game: Applet = clientClazz as Applet
        client = game
        Client.client = clientClazz as com.p3achb0t._runestar_interfaces.Client
        this.clientClazz = clientClazz
        game.apply {
            preferredSize = Dimension(765, 503)
            val loader2 = RSLoader()
            game.setStub(loader2)
        }

    }

    fun getApplet() : Applet {
        return client!!
    }

}

/*
init {
        val loader = Loader() // Should maybe be static and check when the client is launched
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
        val clientClazz = classLoader?.loadClass("client")?.newInstance()
        val game: Applet = clientClazz as Applet
        client = game
        Client.client = clientClazz as com.p3achb0t._runestar_interfaces.Client
        this.clientClazz = clientClazz
        game.apply {
            preferredSize = Dimension(765, 503)
            val loader2 = RSLoader()
            game.setStub(loader2)
        }

    }
 */
