package com.p3achb0t.client.ux

import ComScript
import com.p3achb0t.analyser.ScriptClasses
import com.p3achb0t.analyser.ScriptClasses.findAllDebugClasses
import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.DebugScript
import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.scripts.loading.ScriptType
import com.p3achb0t.client.test.ComScript2
import com.p3achb0t.scripts_debug.widgetexplorer.WidgetExplorerV3
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem

class BotNavigationMenu: JMenuBar() {

    // only because they needs to be refreshed
    val debug = JMenu("Debug")
    val abstract = JMenu("Abstract")
    val background = JMenu("Background")

    init {
        instanceMenu()
        scriptMenu()
        botUltra()
        updateAbstractMenu()
        updateDebugMenu()
        ioHandle()
        test()
    }

    private fun instanceMenu() {
        val menu = JMenu("Client")

        val add = JMenuItem("Add")
        add.addActionListener {
            BotInstance().initBot()
        }

        val remove = JMenuItem("Remove")
        remove.addActionListener {
            GlobalStructs.botTabBar.KillIndex()
        }

        val reloadScripts = JMenuItem("Reload scripts")
        reloadScripts.addActionListener {
            GlobalStructs.scripts.refresh()
            refreshScriptMenu()

        }

        val startScripts = JMenuItem("Run script")
        startScripts.addActionListener {
            GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().startScript()

        }

        val stopScripts = JMenuItem("Stop script")
        stopScripts.addActionListener {
            GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().stopScript()

        }

        val startBackgroundScript = JMenuItem("Start background")
        startBackgroundScript.addActionListener {
            GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().runBackgroundScripts()

        }

        val addScript = JMenuItem("Add ComScript")
        addScript.addActionListener {
            GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().addAbstractScript(ComScript())
            println("ComScript")

        }
        val addScript2 = JMenuItem("Add ComScript2")
        addScript2.addActionListener {
            GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().addDebugScript("comScript2", ComScript2())
            println("ComScript2")

        }

        val ipcHelper = JMenuItem("ipcHelper")
        ipcHelper.addActionListener {
            IpcHelper(GlobalStructs.communication)
        }

        menu.add(add)
        menu.add(remove)
        menu.add(reloadScripts)
        menu.add(startScripts)
        menu.add(stopScripts)
        menu.add(startBackgroundScript)
        menu.add(addScript)
        menu.add(addScript2)
        menu.add(ipcHelper)
        menu.popupMenu.isLightWeightPopupEnabled = false
        add(menu)
    }

    private fun refreshScriptMenu() {

        debug.removeAll()
        abstract.removeAll()
        background.removeAll()


        for (script in GlobalStructs.scripts.scripts.values) {
            val currentItem = JMenuItem("${script.name} ${script.version}")
            if (script.type == ScriptType.DebugScript) {
                currentItem.addActionListener {
                    GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().toggleDebugScript(script.fileName)
                }
                debug.add(currentItem)
            } else if (script.type == ScriptType.AbstractScript) {
                currentItem.addActionListener {
                    GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().addAbstractScript(script.fileName)
                }
                abstract.add(currentItem)
            } else if (script.type == ScriptType.BackgroundScript) {
                currentItem.addActionListener {
                    GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().addBackgroundScript(script.fileName)
                }
                background.add(currentItem)
            }

        }
    }


    private fun scriptMenu() {


        refreshScriptMenu()

        debug.popupMenu.isLightWeightPopupEnabled = false
        abstract.popupMenu.isLightWeightPopupEnabled = false
        background.popupMenu.isLightWeightPopupEnabled = false

        add(debug)
        add(abstract)
        add(background)


    }

    private fun backgroundMenu(): JMenu {
        val menu = JMenu("Background")

        for (dscript in GlobalStructs.scripts.scripts.keys) {

            val mouseDebug = JMenuItem(GlobalStructs.scripts.scripts[dscript]!!.name)
            mouseDebug.addActionListener {
                println(dscript)
                GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().toggleDebugScript(dscript)
            }
            menu.add(mouseDebug)
        }

        menu.popupMenu.isLightWeightPopupEnabled = false
        return menu
    }

    private fun updateDebugMenu(){
        val classes: ArrayList<Class<*>> = ArrayList()
        classes.addAll(findAllDebugClasses("com/p3achb0t/scripts_debug"))
        classes.forEach { clazz ->
            val scriptName = clazz.name.split(".").last()
            if(scriptName.contains("PaintDebug")){
                GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().addDebugScript(scriptName, clazz.newInstance() as DebugScript)
            }else {
                println("Adding menu Item: $scriptName")
                val menuItem1 = JMenuItem("$scriptName")
                val localClazz = clazz
                menuItem1.addActionListener {

                    GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().addDebugScript(scriptName, localClazz.newInstance() as DebugScript)
                }
                debug.add(menuItem1)
            }
        }
    }

    private fun updateAbstractMenu() {
        addScriptsMenus("com/p3achb0t/scripts_private", abstract)
        addScriptsMenus("com/p3achb0t/scripts", abstract)
    }
    private fun addScriptsMenus(scriptsPath: String, menu: JMenu) {
        println("looming at path  $scriptsPath")
        val classes: ArrayList<Class<*>> = ArrayList()

        classes.addAll(ScriptClasses.findAllClasses(scriptsPath))
        //        println("Classes: ")

        classes.forEach { clazz ->
            println("class: ${clazz.name}")
            clazz.annotations.iterator().forEach {
                var manifest = it.toString()

               // println(manifest)
                if(it.toString().contains("ScriptManifest")){
                    manifest = manifest.replace("@com.p3achb0t.api.ScriptManifest(","")
                    manifest = manifest.replace(")","")
                    manifest = manifest.replace("\"","")
                    println("Looking at manifest: $manifest")
                    val splitManifest = manifest.split(",")
                    val category = splitManifest[1].replace("category=", "")
                    val scriptName = splitManifest[2].replace("name=", "").strip()
                    val author = splitManifest[3].replace("author=", "")
                    println("Adding menu Item: $scriptName")
                    val menuItem1 = JMenuItem("$scriptName")
                    val localClazz = clazz
                    menuItem1.addActionListener {
                        GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().addAbstractScript(localClazz.newInstance() as AbstractScript)
                    }
                    menu.add(menuItem1)
                }
            }
        }
    }

    private fun botUltra() {
        val menu = JMenu("Ultra")

        val add = JMenuItem("Add")
        add.addActionListener {
            for (x in 0..5) {
                val bot = BotInstance()
                bot.initBot()
                bot.getInstanceManager().addDebugScript("SexyMouse.jar")
            }
        }

        menu.add(add)

        menu.popupMenu.isLightWeightPopupEnabled = false
        add(menu)
    }

    private fun ioHandle() {
        val menu = JMenu("Input")

        val mouse = JMenuItem("Toggle mouse")
        mouse.addActionListener {
            val f = GlobalStructs.botTabBar.getCurrentIndex().instanceManager!!.getMouse().inputBlocked()
            GlobalStructs.botTabBar.getCurrentIndex().instanceManager!!.getMouse().inputBlocked(!f)

        }

        val keyboard = JMenuItem("Toggle Keyboard")
        keyboard.addActionListener {
            val f = GlobalStructs.botTabBar.getCurrentIndex().instanceManager!!.getKeyboard().inputBlocked()
            GlobalStructs.botTabBar.getCurrentIndex().instanceManager!!.getKeyboard().inputBlocked(!f)

        }


        menu.add(mouse)
        menu.add(keyboard)

        menu.popupMenu.isLightWeightPopupEnabled = false
        add(menu)
    }

    private fun test() {
        val menu = JMenu("Tests")

        val widget = JMenuItem("Widget")
        widget.addActionListener {
            val f = GlobalStructs.botTabBar.getCurrentIndex().instanceManager!!.getManager().ctx
            WidgetExplorerV3(f!!)

        }

        menu.add(widget)

        menu.popupMenu.isLightWeightPopupEnabled = false
        add(menu)
    }
}