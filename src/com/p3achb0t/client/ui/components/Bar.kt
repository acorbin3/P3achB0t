package com.p3achb0t.client.ui.components

import com.p3achb0t.client.configs.Constants
import com.p3achb0t.scripts.ScriptManager
import com.p3achb0t.scripts.TestScript
import java.awt.*
import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JPanel

class Bar : JPanel() {

    init {
        layout = BorderLayout()
        add(tabs(), BorderLayout.WEST)
        add(scriptConfig(), BorderLayout.CENTER)
        add(blockInput(), BorderLayout.EAST)
    }

    fun scriptConfig()  : JPanel {
        val panel = JPanel()

        val play = createImageButton(Constants.ICON_PATH + Constants.ICON_PLAY)
        play.addActionListener {

            ScriptManager.instance.start(TestScript())
        }

        val pause = createImageButton(Constants.ICON_PATH + Constants.ICON_PAUSE)
        pause.addActionListener {

        }


        val stop = createImageButton(Constants.ICON_PATH + Constants.ICON_STOP)
        stop.addActionListener {
            ScriptManager.instance.stop()
        }

        panel.add(play)
        panel.add(pause)
        panel.add(stop)

        return panel
    }

    fun blockInput()  : JPanel {
        val panel = JPanel()

        val mouse = createImageButton(Constants.ICON_PATH + Constants.ICON_MOUSE)
        mouse.addActionListener {

        }

        val keyboard = createImageButton(Constants.ICON_PATH + Constants.ICON_KEYBOARD)
        keyboard.addActionListener {

        }


        panel.add(mouse)
        panel.add(keyboard)

        return panel
    }

    fun tabs()  : JPanel {
        val panel = JPanel()

        val add = createImageButton(Constants.ICON_PATH + Constants.ICON_PLUS)
        add.addActionListener {
            TabManager.instance.addInstance()
        }

        panel.add(add)

        return panel
    }

    fun createImageButton(path: String) : JButton {
        val button = JButton()
        val image = ImageIO.read(File(path)).getScaledInstance(26,26, Image.SCALE_SMOOTH)
        button.icon = ImageIcon(image)
        button.margin = Insets(0, 0, 0, 0)
        button.border = null
        //button.isFocusPainted = false
        button.isContentAreaFilled = false
        button.isOpaque = false

        return button
    }

}