package com.p3achb0t.api.script

import com.p3achb0t.api.Context
import com.p3achb0t.api.utils.Logging
import java.awt.Graphics

/*
    This class is the base set of functionality a script should have
*/
abstract class SuperScript: Logging() {
    lateinit var ctx: Context
    open fun start() {}
    open fun stop() {}
    open fun draw(g: Graphics) {}
}