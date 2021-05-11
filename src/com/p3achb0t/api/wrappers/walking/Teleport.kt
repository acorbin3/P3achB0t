package com.p3achb0t.api.wrappers.walking

import com.p3achb0t.api.wrappers.Tile

class Teleport(val target: Tile, val radius: Int, val handler: suspend ()->Boolean)