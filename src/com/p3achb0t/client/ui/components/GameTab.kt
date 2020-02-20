package com.p3achb0t.client.ui.components

import com.p3achb0t.client.Bot
import com.p3achb0t.client.managers.Manager
import com.p3achb0t.client.managers.accounts.Account
import java.awt.Color
import java.awt.Dimension
import javax.swing.JPanel

class GameTab(account: Account = Account(), manager: Manager) : JPanel() {

    val client: Bot

     init {
         focusTraversalKeysEnabled = true
         background = Color.BLACK
         client = Bot(80,manager,account)
         size = Dimension(800,600)
         //validate()
         add(client.getApplet())
         revalidate()
    }


}