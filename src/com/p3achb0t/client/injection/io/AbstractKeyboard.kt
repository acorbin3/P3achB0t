package com.p3achb0t.client.injection.io

import java.awt.event.KeyEvent
import java.awt.event.KeyListener


abstract class AbstractKeyboard : KeyListener {

    private var inputBlocked = false

    fun inputBlocked(value: Boolean) {
        inputBlocked = value
    }

    fun inputBlocked(): Boolean {
        return inputBlocked
    }

    abstract fun _keyPressed(e: KeyEvent)

    abstract fun _keyReleased(e: KeyEvent)

    abstract fun _keyTyped(e: KeyEvent)

    fun sendEvent(e: KeyEvent) {
        //println(e)

        try {
            when (e.id) {
                KeyEvent.KEY_PRESSED -> _keyPressed(e)
                KeyEvent.KEY_RELEASED -> _keyReleased(e)
                KeyEvent.KEY_TYPED -> _keyTyped(e)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    override fun keyTyped(e: KeyEvent) {
//        println("${e} | ${e.getModifiers()} / ${e.getModifiersEx()}")
        if (!inputBlocked) {
            _keyTyped(e)
        }

    }

    override fun keyPressed(e: KeyEvent) {
//        println("${e} | ${e.getModifiers()} / ${e.getModifiersEx()}")
        if (!inputBlocked) {
            _keyPressed(e)
        }

    }

    override fun keyReleased(e: KeyEvent) {
//        println("${e} | ${e.getModifiers()} / ${e.getModifiersEx()}")
        if (!inputBlocked) {
            _keyReleased(e)
        }

    }
}