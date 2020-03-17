package com.p3achb0t.rewrite.scripts

import java.awt.Graphics

interface Script {
    fun draw(g: Graphics)
    fun loop()
    fun start()
    fun stop()
    fun pause()
}