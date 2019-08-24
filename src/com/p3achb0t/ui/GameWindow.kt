package com.p3achb0t.ui

import com.p3achb0t.ui.components.*
import com.p3achb0t.util.Util
import java.awt.Dimension
import java.nio.file.Paths
import javax.swing.JFrame
import javax.swing.JTabbedPane

class GameWindow : JFrame() {
    var index = 0
    val tabs = JTabbedPane()


    init {

        title = "RuneScape Bot ALPHA"
        defaultCloseOperation = EXIT_ON_CLOSE
        preferredSize = Dimension(765, 503)
        tabs.preferredSize = Dimension(766,503)

        size = Dimension(770, 503)
        jMenuBar = GameMenu(tabs, 0)
        add(TabManager.instance)
        isVisible = true
    }

    fun run() {






        setLocationRelativeTo(null)
        TabManager.instance.addInstance()
        //val g = GamePanel()
        //tabs.addTab("Game", g)
        //g.setContext()

        //tabs.addTab("1", tffff)
        println("before thread")

        //tffff.g()
        //setup()




    }

    fun setup() {
        Util.createDirIfNotExist(Paths.get(Constants.APPLICATION_CACHE_DIR, Constants.JARS_DIR).toString())
        // check client revision
        val revision = Util.checkClientRevision(181, 3000)

        if (revision) {

        } else {

        }


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
}


fun main() {
    setup()
    val g = GameWindow()

    g.run()
}