package com.p3achb0t.api

import java.awt.Graphics

abstract class DebugScript {

    lateinit var ctx: Context

    abstract fun draw(g: Graphics)
}