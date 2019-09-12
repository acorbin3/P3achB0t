package com.p3achb0t.client.ui.components

import com.p3achb0t.client.Game
import java.awt.Color
import java.awt.Dimension
import javax.swing.JPanel

class GameTab : JPanel() {

    var client: Game? = null

     init {
         background = Color.BLACK
         client = Game()
         size = Dimension(800,600)
         validate()
         add(client?.getApplet())
    }


    fun run() {
        validate()
        client?.run()
    }

}