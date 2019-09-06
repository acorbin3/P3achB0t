package com.p3achb0t.ui

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.user_inputs.Keyboard
import com.p3achb0t.api.user_inputs.Mouse

data class Context(var client: Client, var mouse: Mouse, var keyboard: Keyboard)