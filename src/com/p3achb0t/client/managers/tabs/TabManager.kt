package com.p3achb0t.client.managers.tabs

import com.p3achb0t.client.Bot
import com.p3achb0t.client.managers.Manager
import com.p3achb0t.client.test.StandaloneWindow
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.swing.JTabbedPane
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener


class TabManager(val manager: Manager) : JTabbedPane() {

    private var counter = 0
    private val bots = ConcurrentHashMap<Int, Bot>()

    init {
        validate()
        // needs fixing hack!!!!!
        val changeListener: ChangeListener = object : ChangeListener {
            override fun stateChanged(changeEvent: ChangeEvent) {
                val sourceTabbedPane = changeEvent.source as JTabbedPane
                val botPane = sourceTabbedPane.selectedComponent as BotPane
                botPane.bot.getApplet().repaint()
            }
        }
        addChangeListener(changeListener)
    }

    fun create() {
        val bot = Bot(80)
        bots[counter] = bot
        add("$counter", BotPane(bot))
        counter++
    }

    fun destroy(id: Int) {
        val botPane = lookupBotPane(id)
        println(botPane.bot.id)
        Thread {
            botPane.destroy()
            remove(botPane)
            bots.remove(id)
        }.start()
    }

    fun destroy(id: ByteArray) {

        Thread {
            for (i in id) {
                val botPane = lookupBotPane(i.toInt())
                println(botPane.bot.id)
                botPane.destroy()
                remove(botPane)
                bots.remove(i.toInt())
            }

        }.start()
    }

    fun getBots() {
        for (i in bots.keys) {
            println("Bot: $i")
        }
    }

    private fun lookupBotPane(id: Int) : BotPane {
        val index = indexOfTab("$id")
        return getComponentAt(index) as BotPane
    }

    fun lookupBot(id: Int) : Bot {
        val index = indexOfTab("$id")
        return (getComponentAt(index) as BotPane).bot
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
    fun display(bot: Bot) {

        add("Bot", BotPane(bot))
    }

    fun attach(bot: Bot) {
        add(BotPane(bot))
    }

    fun detach() {
        val botPane = this.selectedComponent as BotPane
        val window = StandaloneWindow(botPane.bot,this)
        Thread.sleep(50) // working but needs some more testing...
        window.start()
        remove(botPane)
    }

}