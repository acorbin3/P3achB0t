package com.p3achb0t.client.ux


import com.p3achb0t.client.configs.GlobalStructs
import java.awt.Dimension
import javax.swing.JTabbedPane
import kotlin.concurrent.thread

class BotTabBar : JTabbedPane() {

    val botInstances = HashMap<String, BotInstance>()

    init {
        preferredSize =  Dimension(GlobalStructs.width, GlobalStructs.height)
    }

    fun addBotInstance(id: String, botInstance: BotInstance) {
        botInstances[id] = botInstance
        addTab("Bot", botInstance)
        selectedIndex = if (tabCount == 0) 0 else tabCount-1
    }

    fun killBotInstance(id: String) {
        remove(botInstances[id])
        botInstances.remove(id)
    }

    fun KillIndex() {
        val currentIndex = selectedIndex;
        selectedIndex = if (currentIndex == 0) 0 else currentIndex-1
        thread(start = true) {
            val current = getComponentAt(currentIndex) as BotInstance
            removeTabAt(currentIndex)
            current.kill()
        }
    }

    fun getCurrentIndex(): BotInstance {
        return selectedComponent as BotInstance
    }

}