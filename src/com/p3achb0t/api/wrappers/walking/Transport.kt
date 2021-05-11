package com.p3achb0t.api.wrappers.walking

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.Tile

class Transport(
    val source: Tile,
    val target: Tile,
    val targetRadius: Int,
    val sourceRadius: Int,
    val handler:  suspend (ctx: Context)-> Boolean
)