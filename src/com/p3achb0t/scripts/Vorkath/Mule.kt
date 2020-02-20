package com.p3achb0t.scripts.Vorkath

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.tabs.Equipment
import com.p3achb0t.api.wrappers.tabs.Prayer
import com.p3achb0t.scripts.VorkathMain
import com.p3achb0t.scripts.Task
import kotlinx.coroutines.delay

class Mule(val ctx: Context) : Task(ctx.client) {
    override suspend fun isValidToRun(): Boolean {
        return false
    }


    override suspend fun execute() {


    }
}