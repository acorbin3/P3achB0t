package com.p3achb0t.scripts.action.tutorial_island

import com.p3achb0t.api.interfaces.Client
import com.p3achb0t.api.utils.Logging
import com.p3achb0t.api.wrappers.widgets.WidgetItem


abstract class Job(val client: Client): Logging() {
    abstract suspend fun isValidToRun(dialogWidget: WidgetItem? = null): Boolean
    abstract suspend fun execute()
}

