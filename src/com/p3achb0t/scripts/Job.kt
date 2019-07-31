package com.p3achb0t.scripts

import com.p3achb0t.api.wrappers.widgets.WidgetItem

abstract class Job {
    abstract suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean
    abstract suspend fun execute()
}