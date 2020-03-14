package com.p3achb0t.scripts

import com.p3achb0t.api.interfaces.Client
import com.p3achb0t.api.wrappers.widgets.WidgetItem


abstract class Job(val client: Client) {
    abstract suspend fun isValidToRun(dialogWidget: WidgetItem? = null): Boolean
    abstract suspend fun execute()
}

