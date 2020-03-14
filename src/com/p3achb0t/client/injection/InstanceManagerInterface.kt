package com.p3achb0t.client.injection

import com.p3achb0t.api.interfaces.Keyboard
import com.p3achb0t.api.interfaces.Mouse
import com.p3achb0t.injection.Replace.RsCanvas

interface InstanceManagerInterface {

    fun getCanvas() : RsCanvas
    fun getManager() : InstanceManager
    fun getKeyboard() : Keyboard
    fun getMouse() : Mouse
}