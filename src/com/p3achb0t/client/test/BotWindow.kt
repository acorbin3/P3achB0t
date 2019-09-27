package com.p3achb0t.client.test

import com.p3achb0t.client.managers.Manager
import com.p3achb0t.client.ui.components.BotMenu
import java.awt.Dimension
import javax.swing.JFrame

class BotWindow : JFrame(){

    val manager = Manager()
    init {
        title = "RuneScape Bot ALPHA"
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        focusTraversalKeysEnabled = true
        isFocusable = true
        size = Dimension(800, 800)
        jMenuBar = BotMenu(manager)
        add(manager.tabManager)
        validate()
        isVisible = true

    }
}

fun main() {
    System.setProperty("user.home", "cache")
    BotWindow()
}