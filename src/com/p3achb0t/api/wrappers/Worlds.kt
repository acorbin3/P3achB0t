package com.p3achb0t.api.wrappers

import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t.api.Context
import com.p3achb0t.api.user_inputs.DoActionParams
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.Widget
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay
import java.awt.Rectangle
import kotlin.random.Random

class Worlds(val ctx: Context) {

    fun getWorlds(){
        ctx.client.getWorlds()
    }

}
