package com.p3achb0t.api.user_inputs

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.awt.Component
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class Keyboard(val component: Component, val keyboard: com.p3achb0t.client.interfaces.io.Keyboard) : KeyListener {
    override fun keyTyped(e: KeyEvent?) {
        println("Typed")
    }

    override fun keyPressed(e: KeyEvent?) {
        println("Pressed")
    }

    override fun keyReleased(e: KeyEvent?) {
        println("Released")
    }

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
                    component,
                    KeyEvent.KEY_PRESSED,
                    System.currentTimeMillis(),
                    0,
                    0, c
            )
            keyboard.sendEvent(down)
            delay(20)
            val typeed = KeyEvent(
                    component,
                    KeyEvent.KEY_TYPED,
                    System.currentTimeMillis(),
                    0,
                    0, c
            )
            for (kl in component.keyListeners) {
                if (kl is Keyboard) {
                    continue
                }
                if (!down.isConsumed) {
                    kl.keyTyped(typeed)
                    break
                }
            }
//            component.dispatchEvent(typeed)
            delay(20)
            val up = KeyEvent(
                    component,
                    KeyEvent.KEY_RELEASED,
                    System.currentTimeMillis(),
                    0,
                    0, c
            )
            keyboard.sendEvent(up)
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


