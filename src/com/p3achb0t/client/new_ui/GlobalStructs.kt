package com.p3achb0t.client.new_ui

import com.p3achb0t.client.communication.Communication
import com.p3achb0t.client.managers.scripts.LoadScripts

class GlobalStructs {

    companion object {
        val botTabBar = BotTabBar()
        // minimum [765,503]
        val width = 800
        val height = 600

        val scripts = LoadScripts()

        val communication = Communication()
    }
}