package com.p3achb0t.client.ui.components

import com.p3achb0t.api.Context
import com.p3achb0t.client.managers.Manager
import com.p3achb0t.scripts.BrutalBlackDrags.BrutalBlackDragsMain
import com.p3achb0t.scripts.RuneDragsMain
import com.p3achb0t.scripts.TestVorkScript
import com.p3achb0t.scripts.TutorialIsland
import com.p3achb0t.scripts.TutorialIslandDoAction
import com.p3achb0t.scripts.varbitexplorer.VarBitExplorer
import com.p3achb0t.scripts_private.chicken_killer.ChickenKiller
import com.p3achb0t.widgetexplorer.WidgetExplorerV3
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem

class GameMenu(var manager: Manager) : JMenuBar() {

    init {
        focusTraversalKeysEnabled = true
        add(accountMenu())
        add(debugMenu())
        add(addClientMenu())
    }

    private fun accountMenu() : JMenu {
        val menu = JMenu("Account")

        val accountManager = JMenuItem("Manage Accounts")
        accountManager.addActionListener {
            AccountUI(manager.accountManager)
        }

        menu.add(accountManager)

        return menu
    }

    private fun debugMenu() : JMenu {
        val menu = JMenu("Debug")



        val menuItem = JMenuItem("Run Tutorial Island")
        menuItem.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.setUpScript(TutorialIsland())

        }
        menu.add(menuItem)



        val menuItem3 = JMenuItem("chicken killer")
        menuItem3.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.setUpScript((ChickenKiller()))

        }
        menu.add(menuItem3)

        val menuItem5 = JMenuItem("Run Tutorial Island with doActions")
        menuItem5.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.setUpScript(TutorialIslandDoAction())

        }
        menu.add(menuItem5)

               val menuItem6 = JMenuItem("runeDrags")
        menuItem6.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.setUpScript(RuneDragsMain())

        }
        menu.add(menuItem6)

        val menuItem7 = JMenuItem("BrutalBlackDrags")
        menuItem7.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.setUpScript(BrutalBlackDragsMain())

        }
        menu.add(menuItem7)


        val widgetExplorer = JMenuItem("Open Widget Explorer")
        widgetExplorer.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            //widget exporer should always be the first in the list of debug scripts
            WidgetExplorerV3.createWidgetExplorer(game.client.getScriptManager().debugScripts[0].ctx)
        }

        menu.add(menuItem7)

        val menuItem8 = JMenuItem("TestingVork")
        menuItem8.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.setUpScript(TestVorkScript())

        }
        menu.add(menuItem8)

        val varbitExplorer = JMenuItem("Open Varbit Explorer")
        varbitExplorer.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())

            VarBitExplorer(Context(game.client.getScriptManager().client))
        }




        val mouse = JMenuItem("Move Mouse")
        mouse.addActionListener {

            //val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            //val factory = RuneScapeFactoryTemplates.createAverageComputerUserMotionFactory(game.client?.client, game.client?.applet)
            //factory.move(400,400)
            //factory.move(50,50)

        }

        val test3 = JMenuItem("Test 3")
        test3.addActionListener {

            //manager.changeWindow()
            val f = manager.tabManager.getSelected()
            //f.requestFocus()
            f.client.getApplet().repaint()
            //f.client.applet.focusTraversalKeysEnabled = true
        }


        val test4 = JMenuItem("Draw")
        test4.addActionListener {

            //manager.changeWindow()
            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            //manager.setScript(MouseIntercept())
            //f.client.applet.focusTraversalKeysEnabled = true
        }

        menu.add(mouse)
        menu.add(widgetExplorer)
        menu.add(varbitExplorer)
        menu.add(test3)
        menu.add(test4)

        menu.popupMenu.isLightWeightPopupEnabled = false

        return menu
    }

    private fun addClientMenu() : JMenu {
        val menu = JMenu("Client")

        val drawing = JMenuItem("Add")
        drawing.addActionListener {

            manager.tabManager.addInstance()

        }

        val remove = JMenuItem("Remove")
        remove.addActionListener {

            manager.tabManager.removeInstance()

        }

        val start = JMenuItem("Start")
        start.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.start()
        }

        val stop = JMenuItem("Stop")
        stop.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.stop()

        }

        val resume = JMenuItem("Resume")
        resume.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.resume()

        }

        val suspend = JMenuItem("Suspend")
        suspend.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.pause()

        }

        val keyboardEnable = JMenuItem("Toggle Keyboard")
        keyboardEnable.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getKeyboard()
            manager.inputBlocked(!manager.inputBlocked())
            println(manager.inputBlocked())

        }

        val mouseDisable= JMenuItem("Toggle Mouse")
        mouseDisable.addActionListener {
            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getMouse()
            manager.inputBlocked(!manager.inputBlocked())
            //println("${manager?.x}, ${manager?.y}")
        }

        val getMouseCoords = JMenuItem("Mouse POS")
        getMouseCoords.addActionListener {
            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getMouse()
            //manager?.inputBlocked(!manager.inputBlocked())
            println("${manager.getX()}, ${manager.getY()}")
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