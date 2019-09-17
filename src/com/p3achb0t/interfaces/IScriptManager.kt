package com.p3achb0t.interfaces

import com.p3achb0t.client.interfaces.io.Keyboard
import com.p3achb0t.client.interfaces.io.Mouse
import com.p3achb0t.injection.Replace.RsCanvas

interface IScriptManager {

    fun getManager() : ScriptManager

    fun getKeyboard() : Keyboard

    fun getMouse() : Mouse

    fun getCanvas() : RsCanvas
}