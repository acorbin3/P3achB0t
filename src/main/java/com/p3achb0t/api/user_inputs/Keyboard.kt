package com.p3achb0t.api.user_inputs

import com.p3achb0t.MainApplet
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.awt.event.KeyEvent

class Keyboard {
    companion object {
        fun sendKeys(keys: String, sendReturn: Boolean = false) = runBlocking {
            for (c in keys.toCharArray()) {
                val keyCode = KeyEvent.getExtendedKeyCodeForChar(c.toInt())
                if (KeyEvent.CHAR_UNDEFINED.toInt() == keyCode) {
                    throw RuntimeException(
                        "Key code not found for character '$c'"
                    )
                }
//        println(c)
                val down = KeyEvent(
                    MainApplet.customCanvas,
                    KeyEvent.KEY_PRESSED,
                    System.currentTimeMillis(),
                    0,
                    0, c
                )
                MainApplet.customCanvas?.dispatchEvent(down)
                delay(20)
                val typeed = KeyEvent(
                    MainApplet.customCanvas,
                    KeyEvent.KEY_TYPED,
                    System.currentTimeMillis(),
                    0,
                    0, c
                )
                MainApplet.customCanvas?.dispatchEvent(typeed)
                delay(20)
                val up = KeyEvent(
                    MainApplet.customCanvas,
                    KeyEvent.KEY_RELEASED,
                    System.currentTimeMillis(),
                    0,
                    0, c
                )
                MainApplet.customCanvas?.dispatchEvent(up)
                delay(20)
            }

            if (sendReturn) {
                pressDownKey(KeyEvent.VK_ENTER)
                delay(20)
                release(KeyEvent.VK_ENTER)

            }
        }

        fun pressDownKey(keyCode: Int) {
            val down = KeyEvent(
                MainApplet.customCanvas,
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                keyCode, keyCode.toChar()
            )
            MainApplet.customCanvas?.dispatchEvent(down)
        }

        fun release(keyCode: Int) {
            val up = KeyEvent(
                MainApplet.customCanvas,
                KeyEvent.KEY_RELEASED,
                System.currentTimeMillis(),
                0,
                keyCode, keyCode.toChar()
            )
            MainApplet.customCanvas?.dispatchEvent(up)
        }
    }
}


