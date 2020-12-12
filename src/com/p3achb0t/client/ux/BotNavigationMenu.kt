package com.p3achb0t.client.ux

import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.injection.ScriptState
import com.p3achb0t.client.scripts.loading.ScriptType
import com.p3achb0t.client.ux.scripts_loader.ScriptLoaderUI
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*

class BotNavigationMenu: JMenuBar() {

    // only because they needs to be refreshed
    val paint = JMenu("Paint")
    val action = JMenu("Action")
    val actionAll = JMenu("ActionAll")
    val service = JMenu("Services")

    private val toggleScene = JButton("Toggle Scene")
    private val logoutAllAccounts = JButton("Logout of All Accounts")
    private val startScriptButton = JButton("Start")
    private val pauseScriptButton = JButton("-----")
    private val drawCanvasToggleButton = JButton("Disable Canvas")
    var scriptLoaderUI: ScriptLoaderUI? = null

    init {
        this.layout
        instanceMenu()
        scriptControl()
        scriptMenu()
//        botUltra()
        ioHandle()
        scriptManagerButtons()
    }

    private fun instanceMenu() {
        val menu = JMenu("Client")

        val add = JMenuItem("Add")
        add.addActionListener {
            BotInstance()
        }

        val remove = JMenuItem("Remove")
        remove.addActionListener {
            GlobalStructs.botManager.botTabBar.killSelectedIndex()
        }

        val reloadScripts = JMenuItem("Reload scripts")
        reloadScripts.addActionListener {
            GlobalStructs.scripts.refresh()
            refreshScriptMenu()
        }

        val ipcHelper = JMenuItem("ipcHelper")
        ipcHelper.addActionListener {
            IpcHelper(GlobalStructs.communication)
        }

        menu.add(add)
        menu.add(remove)
        menu.add(reloadScripts)
        menu.add(ipcHelper)

        menu.popupMenu.isLightWeightPopupEnabled = false
        add(menu)
    }

    private fun scriptControl() {

        val menu = JMenu("Script")

        val stop = JMenuItem("Stop")
        stop.addActionListener {
            GlobalStructs.botManager.getSelectedInstanceManager().stopActionScript()
        }

        val pause = JMenuItem("Pause/Resume")
        pause.addActionListener {
            GlobalStructs.botManager.getSelectedInstanceManager().togglePauseActionScript(isFromUI = true)
        }

        val reloadTab = JMenuItem("Reload Tab")
        reloadTab.addActionListener {
            GlobalScope.launch { GlobalStructs.botManager.restartSelectedTab() }

        }

        menu.add(stop)
        menu.add(pause)
        menu.add(reloadTab)

        menu.popupMenu.isLightWeightPopupEnabled = false
        add(menu)
    }

    fun refreshScriptMenu() {

        paint.removeAll()
        action.removeAll()
        actionAll.removeAll()
        service.removeAll()

        for (script in GlobalStructs.scripts.scriptsInformation.values) {
            val currentItem = JMenuItem("${script.name} ${script.version}")
            val currentItemRestAll = JMenuItem("${script.name} ${script.version}")
            when (script.type) {
                ScriptType.PaintScript -> {
                    currentItem.addActionListener {
                        GlobalStructs.botManager.getSelectedInstanceManager().togglePaintScript(script.fileName)
                    }
                    paint.add(currentItem)
                }
                ScriptType.ActionScript -> {
                    currentItem.addActionListener {
                        GlobalStructs.botManager.getSelectedInstanceManager().startActionScript(script.fileName)
                    }
                    //This is intended to switch all accounts open to this script
                    currentItemRestAll.addActionListener {
                        GlobalStructs.botManager.botTabBar.botInstances.forEach { t, u ->
                            u.instanceManagerInterface.getManager().startActionScript(script.fileName)
                        }
                    }
                    action.add(currentItem)
                    actionAll.add(currentItemRestAll)
                }
                ScriptType.ServiceScript -> {
                    currentItem.addActionListener {
                        GlobalStructs.botManager.getSelectedInstanceManager().toggleServiceScript(script.fileName)
                    }
                    service.add(currentItem)
                }
                else -> {}
            }

        }
    }

    private fun scriptMenu() {
        refreshScriptMenu()

        paint.popupMenu.isLightWeightPopupEnabled = false
        action.popupMenu.isLightWeightPopupEnabled = false
        actionAll.popupMenu.isLightWeightPopupEnabled = false
        service.popupMenu.isLightWeightPopupEnabled = false

        add(paint)
        add(action)
        add(service)
        add(actionAll)
    }

    private fun botUltra() {
        val menu = JMenu("Monster Spawner")

        val add = JMenuItem("Kidding just adds 5 tabs XD")
        add.addActionListener {

        }

        menu.add(add)
        menu.popupMenu.isLightWeightPopupEnabled = false
        add(menu)
    }

    private fun ioHandle() {
        val menu = JMenu("Input")

        val mouse = JMenuItem("Toggle mouse")
        mouse.addActionListener {
            val f = GlobalStructs.botManager.getSelectedInstanceInterface().getMouse().inputBlocked()
            GlobalStructs.botManager.getSelectedInstanceInterface().getMouse().inputBlocked(!f)
        }

        val keyboard = JMenuItem("Toggle Keyboard")
        keyboard.addActionListener {
            val f = GlobalStructs.botManager.getSelectedInstanceInterface().getKeyboard().inputBlocked()
            GlobalStructs.botManager.getSelectedInstanceInterface().getKeyboard().inputBlocked(!f)
        }

        menu.add(mouse)
        menu.add(keyboard)

        menu.popupMenu.isLightWeightPopupEnabled = false
        add(menu)
    }

    fun updateScriptManagerButtons() {
        val instance = GlobalStructs.botManager.getSelectedInstanceManager()
        SwingUtilities.invokeLater {
            //println("Updating buttons | TabState: $tabState")
            when (instance.scriptState) {
                ScriptState.Stopped -> {
                    startScriptButton.text = "Start"
                    pauseScriptButton.isEnabled = false
                    pauseScriptButton.text = "-----"
                }
                ScriptState.Running -> {
                    startScriptButton.text = "Stop"
                    pauseScriptButton.isEnabled = true
                    pauseScriptButton.text = "Pause"
                }
                ScriptState.Paused -> {
                    startScriptButton.text = "Stop"
                    pauseScriptButton.isEnabled = true
                    pauseScriptButton.text = "Resume"
                }
                ScriptState.LoginScreenNotPaused -> {
                    startScriptButton.text = "Stop"
                    pauseScriptButton.isEnabled = false
                    pauseScriptButton.text = "Logging In"
                }
            }
            if (instance.drawCanvas)
                drawCanvasToggleButton.text = "Disable Canvas"
            else
                drawCanvasToggleButton.text = "Enable Canvas"
        }
    }

    private fun scriptManagerButtons(){
        //This should be only used as a temporary solution till the bot gets good enough
        //Then the UI will need a proper rework, to allow for better looks

        //updateScriptManagerButtons()


        pauseScriptButton.addActionListener{
            //TODO Replace isactionscript paused with is script running
            if(GlobalStructs.botManager.getSelectedInstanceManager().scriptState == ScriptState.Running) {
                SwingUtilities.invokeLater {
                    pauseScriptButton.text = "Resume"
                }
                GlobalStructs.botManager.getSelectedInstanceManager().togglePauseActionScript(isFromUI = true)
            } else if (GlobalStructs.botManager.getSelectedInstanceManager().scriptState == ScriptState.Paused){
                SwingUtilities.invokeLater {
                    pauseScriptButton.text = "Paused"
                }
                GlobalStructs.botManager.getSelectedInstanceManager().togglePauseActionScript(isFromUI = true)
            }
            updateScriptManagerButtons()
        }


        toggleScene.addActionListener {
            GlobalStructs.botManager.botTabBar.botInstances.forEach { t, u ->
                u.getInstanceManager().isDisableScenery = !u.getInstanceManager().isDisableScenery
                println("u.getInstanceManager().isDisableScenery: ${u.getInstanceManager().isDisableScenery}")
            }

            println("GlobalStructs.botManager.getSelectedInstanceManager().isDisableScenery: ${GlobalStructs.botManager.getSelectedInstanceManager().isDisableScenery}")

        }
        logoutAllAccounts.addActionListener{
            GlobalStructs.botManager.stopAllRunningAccounts()
        }

        startScriptButton.addActionListener{
            if (GlobalStructs.botManager.getSelectedInstanceManager().scriptState == ScriptState.Stopped) {
                if(scriptLoaderUI == null){
                    scriptLoaderUI = ScriptLoaderUI()
                    scriptLoaderUI?.addWindowListener(object : WindowAdapter() {
                        override fun windowClosing(e: WindowEvent?) {
                            scriptLoaderUI = null
                        }
                    })
                    val parentWindowLocation = this.locationOnScreen
                    SwingUtilities.invokeLater {
                        scriptLoaderUI?.setLocation(parentWindowLocation.x, parentWindowLocation.y)
                        scriptLoaderUI?.isVisible = true
                        scriptLoaderUI?.isAlwaysOnTop = true
                    }
                }
            }
            else {
                GlobalStructs.botManager.getSelectedInstanceManager().stopActionScript()
                updateScriptManagerButtons()
            }

        }
        drawCanvasToggleButton.addActionListener {
            val instance = GlobalStructs.botManager.getSelectedInstanceManager()
            instance.drawCanvas = !instance.drawCanvas
            updateScriptManagerButtons()
        }

        add(Box.createHorizontalGlue())
        add(toggleScene)
        add(Box.createHorizontalStrut(3))
        add(logoutAllAccounts)
        add(Box.createHorizontalStrut(3))
        add(startScriptButton)
        add(Box.createHorizontalStrut(3))
        add(pauseScriptButton)
        add(Box.createHorizontalStrut(3))
        add(drawCanvasToggleButton)
        add(Box.createHorizontalStrut(3))
    }
}