package com.p3achb0t.client.injection

import com.p3achb0t.api.interfaces.Keyboard
import com.p3achb0t.api.interfaces.Mouse

interface InstanceManagerInterface {

    fun getManager() : InstanceManager
    fun getKeyboard() : Keyboard
    fun getMouse() : Mouse
}