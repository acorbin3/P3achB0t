package com.p3achb0t.interfaces

import java.awt.Color
import java.awt.Graphics

class PrintScript : Script {
    override fun loop() {
    }

    override fun draw(g: Graphics) {
        g.color = Color.BLUE
        g.drawString("HELLO PEACH", 50, 50)
    }

}