package com.p3achb0t.client.new_ui

import com.formdev.flatlaf.FlatDarkLaf
import java.awt.Font
import javax.swing.JFrame
import javax.swing.UIManager
import javax.swing.plaf.FontUIResource

class BotUI : JFrame() {

    init {

        title = "P3achb0t"
        defaultCloseOperation = EXIT_ON_CLOSE

        jMenuBar = NavigationMenu()
        add(GlobalStructs.botTabBar)
        // add one bot
        BotInstance().initBot()

        setLocationRelativeTo(null)
        pack()
        isVisible = true
    }


}

fun lookAndFeel() {
    UIManager.setLookAndFeel(FlatDarkLaf())
    for ((key) in UIManager.getDefaults()) {
        val value = UIManager.get(key)
        if (value != null && value is FontUIResource) {
            val fr = value
            val f = FontUIResource(Font("Courier Bold", Font.BOLD, 12))
            UIManager.put(key, f)
        }
    }
}

fun main() {
    lookAndFeel()
    BotUI();
    //Server(5681).start()
}