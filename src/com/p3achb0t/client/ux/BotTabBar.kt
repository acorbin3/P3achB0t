package com.p3achb0t.client.ux

import com.p3achb0t.client.configs.GlobalStructs
import javax.swing.JTabbedPane
import kotlin.concurrent.thread

class BotTabBar : JTabbedPane() {

    val botInstances = HashMap<String, BotInstance>()
    private var lastSelectedIndex = 0

    init {
        addChangeListener {
            setEnabledAt(lastSelectedIndex, true)
            setEnabledAt(selectedIndex, false)
            lastSelectedIndex = selectedIndex
            GlobalStructs.botManager.botNavMenu.updateScriptManagerButtons()
        }
    }

    fun addBotInstance(tabLabel: String = "Bot",id: String, botInstance: BotInstance) {
        botInstances[id] = botInstance
        addTab(tabLabel, botInstance)
        selectedIndex = if (tabCount == 0) 0 else tabCount-1
    }

    fun killBotInstance(id: String) {
        remove(botInstances[id])
        botInstances.remove(id)
    }

    fun killSelectedIndex() {
        val currentIndex = selectedIndex
        selectedIndex = if (currentIndex == 0) 0 else currentIndex - 1
        thread(start = true) {
            val current = getComponentAt(currentIndex) as BotInstance
            removeTabAt(currentIndex)
            current.kill()
        }
    }
}