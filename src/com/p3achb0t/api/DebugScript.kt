package com.p3achb0t.api

import java.awt.Graphics

abstract class DebugScript {
    lateinit var ctx: Context
    var scriptName = ""

    fun initialize(client: Any) {
        ctx = Context(client)
    }

    abstract fun draw(g: Graphics)
}