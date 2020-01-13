package com.p3achb0t.api.user_inputs

import com.p3achb0t.interfaces.IScriptManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.applet.Applet
import java.awt.Component
import java.awt.event.KeyEvent

class Keyboard(val obj: Any) {
    val component: Component = (obj as Applet).getComponent(0)
    val keyboard: com.p3achb0t.client.interfaces.io.Keyboard = (obj as IScriptManager).getKeyboard()

    fun sendKeys(
            keys: String,
            sendReturn: Boolean = false,
            instantEntry: Boolean = false // This is intened to send the keys right a way without any delay
    ) = runBlocking {
        for (c in keys.toCharArray()) {
            var keyCode = KeyEvent.getExtendedKeyCodeForChar(c.toInt())
            if (KeyEvent.CHAR_UNDEFINED.toInt() == keyCode) {
                throw RuntimeException(
                        "Key code not found for character '$c'"
                )
            }

            if (c in 'a'..'z' || c in 'A'..'Z') {
                keyCode = 0
            }

            KeyEvent.VK_SPACE
            println("sending: \"$c\" keycode: $keyCode  Directionality:${c.directionality.value} space:${KeyEvent.VK_SPACE}")
            val down = KeyEvent(
                    component,
                    KeyEvent.KEY_PRESSED,
                    System.currentTimeMillis(),
                    0,
                    keyCode,
                    c,
                    KeyEvent.KEY_LOCATION_STANDARD
            )
            keyboard.sendEvent(down)

            if(!instantEntry)
                delay(20)
            val typeed = KeyEvent(
                    component,
                    KeyEvent.KEY_TYPED,
                    System.currentTimeMillis(),
                    0,
                    0,
                    c,
                    KeyEvent.KEY_LOCATION_UNKNOWN
            )
            keyboard.sendEvent(typeed)
            if(!instantEntry)
                delay(20)

            val up = KeyEvent(
                    component,
                    KeyEvent.KEY_RELEASED,
                    System.currentTimeMillis(),
                    0,
                    keyCode,
                    c,
                    KeyEvent.KEY_LOCATION_STANDARD
            )
            keyboard.sendEvent(up)
            if(!instantEntry)
                delay(20)
        }

        if (sendReturn) {
            pressDownKey(KeyEvent.VK_ENTER)
            if(!instantEntry)
                delay(20)
            release(KeyEvent.VK_ENTER)

        }
    }

    fun pressDownKey(keyCode: Int) {
        val down = KeyEvent(
                component,
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                keyCode, keyCode.toChar()
        )
        keyboard.sendEvent(down)
    }

    fun release(keyCode: Int) {
        val up = KeyEvent(
                component,
                KeyEvent.KEY_RELEASED,
                System.currentTimeMillis(),
                0,
                keyCode, keyCode.toChar()
        )
        keyboard.sendEvent(up)
    }
}

