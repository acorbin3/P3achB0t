package com.p3achb0t

import com.formdev.flatlaf.FlatDarkLaf
import com.p3achb0t.client.ui.GameWindow
import com.p3achb0t.client.ui.setup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.awt.Color
import java.awt.Font
import javax.swing.JFrame
import javax.swing.JMenuBar
import javax.swing.JRootPane
import javax.swing.UIManager
import javax.swing.plaf.FontUIResource


object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        setup()
        UIManager.setLookAndFeel(FlatDarkLaf())
        for ((key) in UIManager.getDefaults()) {
            val value = UIManager.get(key)
            if (value != null && value is FontUIResource) {
                val fr = value
                val f = FontUIResource(Font("Courier Bold", Font.BOLD, 12))
                UIManager.put(key, f)
            }
        }
        val g = GameWindow()
        CoroutineScope(Dispatchers.Default).launch { g.run() }
    }
}
