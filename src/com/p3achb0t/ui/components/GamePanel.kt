package com.p3achb0t.ui.components

import com.github.joonasvali.naturalmouse.util.FactoryTemplates
import com.p3achb0t.api.user_inputs.Keyboard
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.client.ClientInstance
import com.p3achb0t.ui.Context
import java.awt.Dimension
import javax.swing.JPanel

class GamePanel : JPanel() {

    lateinit var ctx: Context
    var client: ClientInstance
    lateinit var mouse: Mouse
    lateinit var keyboard: Keyboard

    init {
        //isFocusable = true
        setSize(765, 503)
        //preferredSize = Dimension(765,503)
        validate()

        client = ClientInstance()
        ctx = Context(client.client,
                Mouse(client.applet),
                Keyboard(client.applet),
                client)
        add(client.applet)
//        ctx = Context(client.client,mouse,keyboard,client)

    }


    fun run() {

        //Thread.sleep(2000)
        validate()
        client.run()
        //client.getApplet().init()
        //client.getApplet().start()

    }

}