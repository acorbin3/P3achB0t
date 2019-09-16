package com.p3achb0t.interfaces

import com.p3achb0t.client.interfaces.io.Keyboard
import com.p3achb0t.client.interfaces.io.Mouse

interface IScriptManager {

    fun getManager() : ScriptManager

    fun getKeyboard() : Keyboard

    fun getMouse() : Mouse
}