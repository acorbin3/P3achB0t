package com.p3achb0t.api.script

import com.p3achb0t.api.Context
import com.p3achb0t.api.utils.Logging
import com.p3achb0t.client.accounts.Account
import java.awt.Graphics
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.event.KeyEvent
import java.awt.event.KeyListener


/*
    This class is the base set of functionality a script should have
*/
abstract class SuperScript: KeyListener, Drawable,Logging() {
    lateinit var ctx: Context
    var account: Account = Account()
    open fun start() {}
    open fun stop() {}
    override fun draw(g: Graphics) {}

    companion object{
        var latchPaste = false
    }

    override fun keyPressed(e: KeyEvent?) {
        if(e?.isControlDown == true ) {
            //In order to get other charcter keys to work we need to convert to the ascii decimal number and then use that
            // as the string comparison. This seemed to get letters to work fine.
            // NOTE: if you want to add more characters, they just need to be capitalized
            when (e.keyCode.toString()) {
                'V'.toInt().toString() -> {
                    if(!latchPaste) {
                        latchPaste = true
                        val data = Toolkit.getDefaultToolkit()
                                .getSystemClipboard().getData(DataFlavor.stringFlavor).toString()
                        println("Sending: $data")
                        ctx.keyboard.sendKeys(data, instantEntry = true)
                    }

                }
            }
        }
    }

    override fun keyReleased(e: KeyEvent?) {
        if(e?.isControlDown == false){
            latchPaste = false
        }
    }

    override fun keyTyped(e: KeyEvent?) {

    }
}