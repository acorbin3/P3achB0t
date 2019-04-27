package com.p3achb0t.user_inputs

import com.p3achb0t.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.awt.event.KeyEvent

fun sendKeys(keys: String) = runBlocking {
    for (c in keys.toCharArray()) {
        val keyCode = KeyEvent.getExtendedKeyCodeForChar(c.toInt())
        if (KeyEvent.CHAR_UNDEFINED.toInt() == keyCode) {
            throw RuntimeException(
                "Key code not found for character '$c'"
            )
        }
//        println(c)
        val down = KeyEvent(
            Main.customCanvas,
            KeyEvent.KEY_PRESSED,
            System.currentTimeMillis(),
            0,
            0, c
        )
        Main.customCanvas?.dispatchEvent(down)
        delay(20)
        val typeed = KeyEvent(
            Main.customCanvas,
            KeyEvent.KEY_TYPED,
            System.currentTimeMillis(),
            0,
            0, c
        )
        Main.customCanvas?.dispatchEvent(typeed)
        delay(20)
        val up = KeyEvent(
            Main.customCanvas,
            KeyEvent.KEY_RELEASED,
            System.currentTimeMillis(),
            0,
            0, c
        )
        Main.customCanvas?.dispatchEvent(up)
        delay(20)
    }
}