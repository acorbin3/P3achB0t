package com.p3achb0t.client.ui.components

import com.p3achb0t.client.managers.Manager
import com.p3achb0t.client.managers.accounts.AccountManager
import com.p3achb0t.scripts.GoblinKiller
import com.p3achb0t.scripts.TutorialIsland
import com.p3achb0t.widgetexplorer.WidgetExplorerV3
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JTabbedPane

class GameMenu(val tabs: JTabbedPane, var index: Int) : JMenuBar() {

    var manager: Manager
    init {
        focusTraversalKeysEnabled = true
        add(accountMenu())
        add(debugMenu())
        add(addClientMenu())
        manager = Manager()
    }

    private fun accountMenu() : JMenu {
        val menu = JMenu("Account")

        val accountManager = JMenuItem("Manage Accounts")
        accountManager.addActionListener {
            AccountUI(AccountManager())
        }

        menu.add(accountManager)

        return menu
    }

    private fun debugMenu() : JMenu {
        val menu = JMenu("Debug")


        val injection = JMenuItem("Run Goblin script")
        injection.addActionListener {

            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client?.getScriptManager()
            manager?.setScript(GoblinKiller())

        }

        val tutorialIsland = JMenuItem("Run Tutorial Island")
        tutorialIsland.addActionListener {

            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.setScript(TutorialIsland())

        }
        val widgetExplorer = JMenuItem("Open Widget Explorer")
        widgetExplorer.addActionListener {

            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            WidgetExplorerV3.createWidgetExplorer(game.client.getScriptManager().getScript().ctx)
        }


        val mouse = JMenuItem("Move Mouse")
        mouse.addActionListener {

            //val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            //val factory = RuneScapeFactoryTemplates.createAverageComputerUserMotionFactory(game.client?.client, game.client?.applet)
            //factory.move(400,400)
            //factory.move(50,50)

        }

        val test = JMenuItem("Test")
        test.addActionListener {


            manager.addBot_old()

        }

        val test2 = JMenuItem("Test 2")
        test2.addActionListener {

            manager.changeWindow()

        }

        val test3 = JMenuItem("Test 3")
        test3.addActionListener {

            //manager.changeWindow()
            val f = TabManager.instance.getSelected()
            //f.requestFocus()
            f.client.getApplet()?.repaint()
            //f.client.applet.focusTraversalKeysEnabled = true
        }

        val test4 = JMenuItem("Draw")
        test4.addActionListener {

            //manager.changeWindow()
            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            //manager.setScript(MouseIntercept())
            //f.client.applet.focusTraversalKeysEnabled = true
        }

        menu.add(mouse)
        menu.add(injection)
        menu.add(tutorialIsland)
        menu.add(widgetExplorer)
        menu.add(test)
        menu.add(test2)
        menu.add(test3)
        menu.add(test4)

        menu.popupMenu.isLightWeightPopupEnabled = false

        return menu
    }

    private fun addClientMenu() : JMenu {
        val menu = JMenu("Client")

        val drawing = JMenuItem("Add")
        drawing.addActionListener {

            TabManager.instance.addInstance()

        }

        val remove = JMenuItem("Remove")
        remove.addActionListener {

            TabManager.instance.removeInstance()

        }

        val start = JMenuItem("Start")
        start.addActionListener {

            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.start()
        }

        val stop = JMenuItem("Stop")
        stop.addActionListener {

            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.stop()

        }

        val resume = JMenuItem("Resume")
        resume.addActionListener {

            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client?.getScriptManager()
            manager?.resume()

        }

        val suspend = JMenuItem("Suspend")
        suspend.addActionListener {

            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client?.getScriptManager()
            manager?.pause()

        }

        val keyboardEnable = JMenuItem("Toggle Keyboard")
        keyboardEnable.addActionListener {

            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client?.getKeyboard()
            manager?.inputBlocked(!manager.inputBlocked())
            println(manager?.inputBlocked())

        }

        val mouseDisable= JMenuItem("Toggle Mouse")
        mouseDisable.addActionListener {
            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client?.getMouse()
            manager?.inputBlocked(!manager.inputBlocked())
            //println("${manager?.x}, ${manager?.y}")
        }

        val getMouseCoords = JMenuItem("Mouse POS")
        getMouseCoords.addActionListener {
            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client?.getMouse()
            //manager?.inputBlocked(!manager.inputBlocked())
            println("${manager?.getX()}, ${manager?.getY()}")
        }

        menu.add(remove)
        menu.add(drawing)
        menu.add(start)
        menu.add(stop)
        menu.add(suspend)
        menu.add(resume)
        menu.add(keyboardEnable)
        menu.add(mouseDisable)
        menu.add(getMouseCoords)

        menu.popupMenu.isLightWeightPopupEnabled = false

        return menu
    }

}