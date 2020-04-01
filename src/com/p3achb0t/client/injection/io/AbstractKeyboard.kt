package com.p3achb0t.client.injection.io

import com.p3achb0t.api.interfaces.Keyboard
import java.awt.event.KeyEvent
import java.awt.event.KeyListener


abstract class AbstractKeyboard : KeyListener, Keyboard {

    private var inputBlocked = false

    override fun inputBlocked(value: Boolean) {
        inputBlocked = value
    }

    override fun inputBlocked(): Boolean {
        return inputBlocked
    }

    abstract fun _keyPressed(e: KeyEvent)

    abstract fun _keyReleased(e: KeyEvent)

    abstract fun _keyTyped(e: KeyEvent)

    override fun sendEvent(e: KeyEvent) {
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
        /*
        if(e.isControlDown && e.keyChar.isDigit()){
            when (e.keyChar) {
                '1' -> {
                    println("Swapping debug text")
                    PaintDebug.isDebugTextOn = !PaintDebug.isDebugTextOn
                }
                '2' -> {
                    println("Swapping NPC paint")
                    PaintDebug.isNPCPaintOn = !PaintDebug.isNPCPaintOn
                }
                '3' -> {
                    println("Swapping players")
                    PaintDebug.isPlayerPaintOn = !PaintDebug.isPlayerPaintOn
                }
                '4' -> {
                    println("Swapping gameobjects")
                    PaintDebug.isGameObjectOn = !PaintDebug.isGameObjectOn
                }
                '5' -> {
                    println("Swapping gounditems")
                    PaintDebug.isGroundItemsOn = !PaintDebug.isGroundItemsOn
                }
                '8' -> {
                    println("Swapping can walk")
                    PaintDebug.isCanWalkDebug = !PaintDebug.isCanWalkDebug
                }
                '7' -> {
                    println("Swapping projectiles")
                    PaintDebug.isProjectileDebug = !PaintDebug.isProjectileDebug
                }
                '9' -> {
                    println("Swapping inventory")
                    PaintDebug.isInventoryPaintingDebug = !PaintDebug.isInventoryPaintingDebug
                }
            }
        }*/
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