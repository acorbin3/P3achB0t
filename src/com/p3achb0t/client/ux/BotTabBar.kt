package com.p3achb0t.client.ux

import com.p3achb0t.api.utils.Time.sleep
import com.p3achb0t.client.accounts.Account
import com.p3achb0t.client.configs.GlobalStructs
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.commons.lang.time.StopWatch
import java.util.*
import javax.swing.JTabbedPane
import javax.swing.SwingUtilities
import kotlin.collections.HashMap
import kotlin.concurrent.thread

class BotTabBar : JTabbedPane() {

    val botInstances = HashMap<String, BotInstance>()
    private var lastSelectedIndex = 0

    init {
        addChangeListener {

            try {
                if (this.tabCount > 0) {

                    println("lastIndex: $lastSelectedIndex CurrentIndex:$selectedIndex")
                    SwingUtilities.invokeLater {
                        for( i in 0..this.tabCount - 1){
                            setEnabledAt(i, true)
                        }
//                        setEnabledAt(lastSelectedIndex, true)
//                        setEnabledAt(selectedIndex, false)
                        lastSelectedIndex = selectedIndex
                    }
                    GlobalStructs.botManager.botNavMenu.updateScriptManagerButtons()
                }
            }catch (e:Exception){e.printStackTrace()}
        }
    }

    fun addBotInstance(tabLabel: String = "Bot",id: String, botInstance: BotInstance) {
        botInstances[id] = botInstance
        addTab(tabLabel, botInstance)
        SwingUtilities.invokeLater {
            selectedIndex = if (tabCount == 0) 0 else tabCount - 1
        }
    }

    fun killBotInstance(id: String) {
        remove(botInstances[id])
        botInstances.remove(id)
    }

    suspend fun restartBotInstance(id:String): String{
        botInstances[id]?.getInstanceManager()?.stopActionScript()

        //Create new tab
        val account = botInstances[id]?.account ?: Account()
        account.uuid = UUID.randomUUID().toString()
        val previousTabCount = this.tabCount
        val textBarInfo = botInstances[id]?.tabBarTextInfo ?: ""
        GlobalScope.launch {
            BotInstance(account, textBarInfo)

        }

        //WAit till we have a new tab
        val timeout = StopWatch()
        timeout.start()
        while(this.tabCount == previousTabCount && timeout.time < 1000*5){
            sleep(50)
        }

        timeout.reset()
        timeout.start()
        // WAit till that tabs context is loaded
        while(botInstances[account.uuid]?.getInstanceManager()?.isContextLoaded == false && timeout.time < 1000*5){
            sleep(50)
        }


        //remove old tab
        try {
            killBotInstance(id)
        } catch (e: Exception){e.printStackTrace()}
        return account.uuid
    }

    fun killSelectedIndex() {
        val currentIndex = selectedIndex
        SwingUtilities.invokeLater {
            selectedIndex = if (currentIndex == 0) 0 else currentIndex - 1
        }
        thread(start = true) {
            val current = getComponentAt(currentIndex) as BotInstance
            current.getInstanceManager().stopActionScript()

            removeTabAt(currentIndex)
            current.kill()
        }
    }
}