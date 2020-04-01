package com.p3achb0t.scripts_debug.varbitexplorer

import com.p3achb0t.api.DebugScript
import java.awt.Graphics

class VarBitExplorerDebug: DebugScript() {
    override fun draw(g: Graphics) {
    }

    override fun start() {
        VarBitExplorer(ctx)
    }

    override fun stop() {
        TODO("Not yet implemented")
    }
}