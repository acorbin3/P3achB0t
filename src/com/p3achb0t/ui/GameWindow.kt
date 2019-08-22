package com.p3achb0t.ui

import com.p3achb0t.ui.components.Constants
import com.p3achb0t.ui.components.GameMenu
import com.p3achb0t.ui.components.GameTab
import com.p3achb0t.util.Util
import java.awt.Dimension
import java.nio.file.Paths
import javax.swing.JFrame
import javax.swing.JTabbedPane

class GameWindow : JFrame() {

    val tabs = JTabbedPane()

    val tffff = GameTab(1, tabs)

    fun run() {
        System.setProperty("user.home", "cache")

        defaultCloseOperation = EXIT_ON_CLOSE

        size = Dimension(850, 700)
        jMenuBar = GameMenu()

        add(tabs)
        setLocationRelativeTo(null)
        isVisible = true
        tabs.addTab("1", tffff)
        tffff.g()
        //setup()

    }

    fun setup() {
        Util.createDirIfNotExist(Paths.get(Constants.APPLICATION_CACHE_DIR, Constants.JARS_DIR).toString())
        // check client revision
        val revision = Util.checkClientRevision(181, 3000)

        if (revision) {

        } else {

        }


        jMenuBar = GameMenu()
        //tabs.addTab("1", GameTab(1, tabs))
        //tabs.addTab("2", GameTab(2, tabs))
        //tabs.addTab("3", GameTab(3, tabs))
        add(tabs)
    }

}


fun main() {
    val g = GameWindow()

    g.run()
}