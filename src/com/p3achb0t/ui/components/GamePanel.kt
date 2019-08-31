package com.p3achb0t.ui.components

import com.p3achb0t.client.ClientInstance
import java.awt.Dimension
import javax.swing.JPanel

class GamePanel : JPanel() {

    var client: ClientInstance? = null

     init {
        //isFocusable = true
        setSize(765,503)
        //preferredSize = Dimension(765,503)
        validate()
        client = ClientInstance()
        add(client?.getApplet())

    }


    fun run() {

        //Thread.sleep(2000)
        validate()
        client?.run()
        //client.getApplet().init()
        //client.getApplet().start()

    }

}