package com.p3achb0t.client.ux


import com.p3achb0t.client.configs.GlobalStructs
import java.awt.Dimension
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JTabbedPane
import kotlin.concurrent.thread


class BotTabBar : JTabbedPane(), MouseListener {

    val botInstances = HashMap<String, BotInstance>()
    private var lastSelectedIndex = 0

    init {
        preferredSize =  Dimension(GlobalStructs.width, GlobalStructs.height)
        this.addMouseListener(this)
        this.addChangeListener {
            this.setEnabledAt(lastSelectedIndex, true)
            this.setEnabledAt(this.selectedIndex, false)
            lastSelectedIndex = this.selectedIndex
        }
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

    override fun mouseReleased(p0: MouseEvent?) {

    }

    override fun mouseEntered(p0: MouseEvent?) {
        if (selectedComponent != null) {
            //val t= getCurrentIndex().applet?.requestFocus()

            //println("requestFocusInWindow $t")
        }
    }

    override fun mouseClicked(p0: MouseEvent?) {
    }

    override fun mouseExited(p0: MouseEvent?) {
    }

    override fun mousePressed(p0: MouseEvent?) {
    }

}