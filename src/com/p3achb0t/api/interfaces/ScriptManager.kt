package com.p3achb0t.api.interfaces

import com.p3achb0t.injection.Replace.RsCanvas
import com.p3achb0t.client.injection.ScriptManager

interface ScriptManager {

    fun getManager() : ScriptManager

    fun getKeyboard() : Keyboard

    fun getMouse() : Mouse

    fun getCanvas() : RsCanvas
}