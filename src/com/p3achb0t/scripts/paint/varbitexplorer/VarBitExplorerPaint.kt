package com.p3achb0t.scripts.paint.varbitexplorer

import com.p3achb0t.api.PaintScript
import com.p3achb0t.api.ScriptManifest
import java.awt.Graphics

@ScriptManifest("Debug","VarBit Explorer","P3aches", "0.1")
class VarBitExplorerPaint: PaintScript() {
    override fun draw(g: Graphics) {
    }

    override fun start() {
        VarBitExplorer(ctx)
    }

    override fun stop() {
        TODO("Not yet implemented")
    }
}