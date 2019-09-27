package com.p3achb0t.client.managers.tabs

import com.p3achb0t.client.Bot
import com.p3achb0t.client.managers.Manager
import com.p3achb0t.client.test.StandaloneWindow
import com.test.SingleWindow2
import javax.swing.JTabbedPane

class TabManager(val manager: Manager) : JTabbedPane() {

    private val displaying = mutableListOf<String>()

    init {
        validate()
    }

    fun display(session: String, bot: Bot) {
        displaying.add(session)
        add("Bot 1", BotPane(bot))
    }

    fun attach(bot: Bot) {
        add(BotPane(bot))
    }

    fun detach(bot: Bot) {
        val botPane = this.selectedComponent as BotPane
        val window = StandaloneWindow(bot,this)
        Thread.sleep(50) // working but needs some more testing...
        window.start()
        remove(botPane)
    }

    fun destroy(session: String) {
        displaying.remove(session)
        setEnabledAt(selectedIndex, false)
        val botPane = this.selectedComponent as BotPane
        botPane.destroy()
        remove(botPane)
    }

    fun getSelectedTabIndex() : Int {
        return selectedIndex
    }

    fun getSelectedTabBot() : BotPane {
        return selectedComponent as BotPane
    }

    fun resizeView(x: Int, y: Int) {
        val botPane = this.selectedComponent as BotPane
        botPane.resizeView(x,y)
    }

    fun getSelectedTab_old() : Int {
        val f = this.selectedComponent as BotPane
        f.display()
        f.destroy()
        return selectedIndex
    }


}