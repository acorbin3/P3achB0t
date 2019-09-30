package com.p3achb0t.api

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t.api.user_inputs.Keyboard
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.client.ui.components.GameTab

class Context(
        var client: Client,
        var mouse: Mouse,
        var keyboard: Keyboard,
        var gameTab: GameTab,
        var selectedWidget: Component? = null
)