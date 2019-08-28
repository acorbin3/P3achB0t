package com.p3achb0t.ui

import com.p3achb0t.analyser.Analyser
import com.p3achb0t.analyser.runestar.RuneStarAnalyzer
import com.p3achb0t.loader.Loader
import com.p3achb0t.ui.components.*
import com.p3achb0t.util.Util
import java.awt.BorderLayout
import java.awt.Dimension
import java.io.File
import java.nio.file.Paths
import java.util.jar.JarFile
import javax.swing.JFrame
import javax.swing.JTabbedPane

class GameWindow : JFrame() {
    var index = 0
    val tabs = JTabbedPane()
    val layout2 = BorderLayout()


    init {

        title = "RuneScape Bot ALPHA"
        layout = layout2
        defaultCloseOperation = EXIT_ON_CLOSE
        preferredSize = Dimension(765, 503)
        tabs.preferredSize = Dimension(765,503)

        size = Dimension(765, 600)
        jMenuBar = GameMenu(tabs, 0)
        add(Bar(), BorderLayout.PAGE_START)
        add(TabManager.instance, BorderLayout.CENTER)
        //add(TabManager.instance)

        add(GameLog(), BorderLayout.PAGE_END)

        isVisible = true
    }

    fun run() {






        setLocationRelativeTo(null)
        TabManager.instance.addInstance()
        //val g = GamePanel()
        //tabs.addTab("Game", g)
        //g.setContext()

        //tabs.addTab("1", tffff)
        //println("before thread")

        //tffff.g()
        //setup()
        //remove(layout2.getLayoutComponent(BorderLayout.PAGE_END))
        //validate()




    }

    fun setup() {

        //jMenuBar = GameMenu()
        //tabs.addTab("1", GameTab(1, tabs))
        //tabs.addTab("2", GameTab(2, tabs))
        //tabs.addTab("3", GameTab(3, tabs))
        //add(tabs)
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
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        setup()
        val g = GameWindow()

        g.run()
    }
}