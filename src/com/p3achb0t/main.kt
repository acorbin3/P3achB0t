package com.p3achb0t

import com.p3achb0t.client.ui.GameWindow
import com.p3achb0t.client.ui.setup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        setup()
        val g = GameWindow()
        CoroutineScope(Dispatchers.Default).launch { g.run() }
    }
}
