package com.p3achb0t.api.tests

import com.p3achb0t.api.DebugScript
import java.awt.Graphics

class MouseDebug : DebugScript() {

    override fun draw(g: Graphics) {
        g.drawString("Mouse(34, 56)", 30 ,30)
    }
}