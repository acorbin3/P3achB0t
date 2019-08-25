package com.p3achb0t.ui.components

import com.p3achb0t.client.ClientInstance
import java.awt.Dimension
import javax.swing.JPanel

class GamePanel : JPanel() {

    val client = ClientInstance()

    init {
        isFocusable = true
        preferredSize = Dimension(765,503)
        add(client.getApplet())
        validate()

    }


    fun run() {

        client.getApplet().init()
        client.getApplet().start()

    }

}