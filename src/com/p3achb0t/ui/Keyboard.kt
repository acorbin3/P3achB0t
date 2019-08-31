package com.p3achb0t.ui

import java.awt.Component
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.*


class Keyboard(private val component: Component) : KeyListener {
    override fun keyTyped(p0: KeyEvent?) {
    }

    override fun keyPressed(p0: KeyEvent?) {
    }

    override fun keyReleased(p0: KeyEvent?) {
    }

    private var pressTime: Long = 0

    /**
     * Types the given String and optionally presses Enter afterwards.
     * @param s The String to type.
     * @param enterAfter True if `KeyEvent.VK_ENTER` should be pressed afterwards. This is actually the '\n' character, for New Line. Useful for logging in.
     */
    @JvmOverloads
    fun sendKeys(s: String, enterAfter: Boolean = true) {

        pressTime = System.currentTimeMillis()
        for (c in s.toCharArray()) {
            for (ke in createKeyClick(component, c)) {
                try {
                    Thread.sleep(5)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                sendKeyEvent(ke)
            }
        }
        if (enterAfter) {
            clickKey(KeyEvent.VK_ENTER)
        }
    }

    /**
     * Creates and sends a single KeyEvent using the given Char.
     * @param c The char to send.
     */
    fun clickKey(c: Char) {

        pressTime = System.currentTimeMillis()
        for (ke in createKeyClick(component, c)) {
            sendKeyEvent(ke)
        }
    }

    /**
     * Creates and sends a given KeyEvent using the given keyCode.
     *
     * Use constants where possible, from [KeyEvent], such as `KeyEvent.VK_ENTER`
     * @param keyCode The keycode to send.
     */
    fun clickKey(keyCode: Int) {

        pressTime = System.currentTimeMillis()
        for (ke in createKeyClick(component, keyCode)) {
            sendKeyEvent(ke)
        }
    }

    /**
     * Creates and sends a given PRESS KeyEvent using the given keyCode. Note, this does not send a Release Event
     * typically associated with a key click.
     *
     * Use constants where possible, from [KeyEvent], such as `KeyEvent.VK_ENTER`
     * @param keyCode
     */
    fun pressKey(keyCode: Int) {

        pressTime = System.currentTimeMillis()
        val ke = createKeyPress(component, keyCode)
        sendKeyEvent(ke)
    }

    /**
     * Creates and sends a given RELEASE KeyEvent using the given keyCode. Note, this does not send a Press Event
     * typically associated with a key click.
     *
     * Use constants where possible, from [KeyEvent], such as `KeyEvent.VK_ENTER`
     * @param keyCode
     */
    fun releaseKey(keyCode: Int) {

        pressTime = System.currentTimeMillis()
        val ke = createKeyRelease(component, keyCode)
        sendKeyEvent(ke)
    }

    /**
     * Creates KeyEvents to perform a Click of the given Char. This includes a Press, Typed and Release event
     * in addition to an initial shiftDown and ending shiftUp if the character is a Special Char such as `!"£$%^&*(`
     *
     * {@see specialChars}
     * @param target Component this event is linked to.
     * @param c Char to send.
     * @return KeyEvents for each action.
     */
    private fun createKeyClick(target: Component, c: Char): Array<KeyEvent> {

        pressTime += 2 * random

        val newChar = specialChars!![c]
        val keyCode = Character.toUpperCase(newChar ?: c).toInt()

        if (Character.isLowerCase(c) || !Character.isLetter(c) && newChar == null) {
            val pressed = KeyEvent(target, KeyEvent.KEY_PRESSED,
                    pressTime, 0, keyCode, c)
            val typed = KeyEvent(target, KeyEvent.KEY_TYPED,
                    pressTime, 0, 0, c)
            pressTime += random
            val released = KeyEvent(target, KeyEvent.KEY_RELEASED,
                    pressTime, 0, keyCode, c)

            return arrayOf<KeyEvent>(pressed, typed, released)
        } else {
            val shiftDown = KeyEvent(target, KeyEvent.KEY_PRESSED,
                    pressTime, KeyEvent.SHIFT_MASK, KeyEvent.VK_SHIFT,
                    KeyEvent.CHAR_UNDEFINED)

            pressTime += random
            val pressed = KeyEvent(target, KeyEvent.KEY_PRESSED,
                    pressTime, KeyEvent.SHIFT_MASK, keyCode, c)
            val typed = KeyEvent(target, KeyEvent.KEY_TYPED,
                    pressTime, KeyEvent.SHIFT_MASK, 0, c)
            pressTime += random
            val released = KeyEvent(target, KeyEvent.KEY_RELEASED,
                    pressTime, KeyEvent.SHIFT_MASK, keyCode, c)
            pressTime += random
            val shiftUp = KeyEvent(target, KeyEvent.KEY_RELEASED,
                    pressTime, 0, KeyEvent.VK_SHIFT, KeyEvent.CHAR_UNDEFINED)

            return arrayOf<KeyEvent>(shiftDown, pressed, typed, released, shiftUp)
        }
    }

    /**
     * Creates KeyEvents for Press and Release of the given keyCode.
     * @param target
     * @param keyCode
     * @return An array containing Press and Release KeyEvents.
     */
    private fun createKeyClick(target: Component, keyCode: Int): Array<KeyEvent> {

        return arrayOf<KeyEvent>(createKeyPress(target, keyCode), createKeyRelease(target, keyCode))
    }

    /**
     * Creates a Press type KeyEvent
     * @param target
     * @param keyCode
     * @return
     */
    private fun createKeyPress(target: Component, keyCode: Int): KeyEvent {
        var modifier = 0
        when (keyCode) {
            KeyEvent.VK_SHIFT -> modifier = KeyEvent.SHIFT_MASK
            KeyEvent.VK_ALT -> modifier = KeyEvent.ALT_MASK
            KeyEvent.VK_CONTROL -> modifier = KeyEvent.CTRL_MASK
        }

        return KeyEvent(target, KeyEvent.KEY_PRESSED,
                pressTime, modifier, keyCode, KeyEvent.CHAR_UNDEFINED)
    }

    /**
     * Creates a Release type KeyEvent
     * @param target
     * @param keyCode
     * @return
     */
    private fun createKeyRelease(target: Component, keyCode: Int): KeyEvent {

        return KeyEvent(target, KeyEvent.KEY_RELEASED,
                pressTime + random, 0, keyCode, KeyEvent.CHAR_UNDEFINED)
    }

    /**
     * Actually triggers sending of a given KeyEvent in the instance of KeyListeners' `component` field.
     * @param e
     */
    fun sendKeyEvent(e: KeyEvent) {
        for (kl in component.keyListeners) {
            if (kl is Keyboard) {
                continue
            }
            if (!e.isConsumed) {
                when (e.id) {
                    KeyEvent.KEY_PRESSED -> kl.keyPressed(e)
                    KeyEvent.KEY_RELEASED -> kl.keyReleased(e)
                    KeyEvent.KEY_TYPED -> kl.keyTyped(e)
                }
            }
        }
    }



    companion object {
        private var specialChars: HashMap<Char, Char>? = null

        /**
         * `KeyEvent.VK_ENTER` is actually New Line, '\n'.
         * The code for the Enter button is 13. It has no associated [KeyEvent] constant.
         */
        val ENTER_KEYCODE = 13

        init {
            val spChars = charArrayOf('~', '!', '@', '#', '%', '^', '&', '*', '(', ')', '_', '+', '{', '}', ':', '<', '>', '?', '"', '|')
            val replace = charArrayOf('`', '1', '2', '3', '5', '6', '7', '8', '9', '0', '-', '=', '[', ']', ';', ',', '.', '/', '\'', '\\')
            specialChars = HashMap(spChars.size)
            for (x in spChars.indices) {
                specialChars!![spChars[x]] = replace[x]
            }
        }


        /**
         * Generates a random number in the range of 40-140.
         * @return The random number
         */
        private val random: Int
            get() {
                //val rand = Random()
                return 150
            }
    }

}
