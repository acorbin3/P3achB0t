package com.p3achb0t.client.ui

import com.p3achb0t.analyser.Analyser
import com.p3achb0t.analyser.runestar.RuneStarAnalyzer
import com.p3achb0t.client.configs.Constants
import com.p3achb0t.client.loader.Loader
import com.p3achb0t.client.ui.components.GameMenu
import com.p3achb0t.client.ui.components.TabManager
import com.p3achb0t.client.util.Util
import kotlinx.coroutines.delay
import java.awt.Dimension
import java.io.File
import java.nio.file.Paths
import java.util.jar.JarFile
import javax.swing.JFrame
import javax.swing.JTabbedPane

class GameWindow : JFrame() {
    var index = 0
    val tabs = JTabbedPane()


    init {

        title = "RuneScape Bot ALPHA"
        defaultCloseOperation = EXIT_ON_CLOSE
        //preferredSize = Dimension(765, 503)
        tabs.preferredSize = Dimension(800,600)
        focusTraversalKeysEnabled = true
        size = Dimension(800, 800)
        jMenuBar = GameMenu(tabs, 0)
        add(TabManager.instance)
        setLocationRelativeTo(null)

        validate()
        isVisible = true
        TabManager.instance.addInstance()



    }

    suspend fun run() {
        //Waiting till game has been loaded and then revalidate
         val client = TabManager.instance.clients[0].client.getScript().ctx.client
        while(client.getGameState() != 10){
            delay(50)
        }
        println("Validating client")
        for (i in 0..10) {
            TabManager.instance.getSelected().client.getApplet().repaint()
        }
    }

    fun setup() {
    }

}

// A setup function should not be placed here

fun setup() {
    System.setProperty("user.home", "cache")
    Util.createDirIfNotExist(Paths.get(Constants.APPLICATION_CACHE_DIR, Constants.JARS_DIR).toString())
    // check applet revision
    val revision = Util.checkClientRevision(Constants.REVISION, 3000)
    if (!revision) {
        println("New revision, need to update hooks")
    }
    //Check to see if we have an injected JAR for the specific revision
    //Handle case where missing injection Jar
    //Download new Gamepack
    //Run analyzer and inject new gamepack
    if(!File("${Constants.APPLICATION_CACHE_DIR}/${Constants.INJECTED_JAR_NAME}").exists()) {
        val loader = Loader()
        val gamePackWithPath = loader.run()
        val gamePackJar = JarFile(gamePackWithPath)
        println("Using $gamePackWithPath")

        val runeStar = RuneStarAnalyzer()
        runeStar.loadHooks()
        runeStar.parseJar(gamePackJar)
        Analyser().createInjectedJar(gamePackJar, runeStar)
    }
}
