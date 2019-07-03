package com.p3achb0t.api.wrappers

import java.awt.Point

interface Interactable {
    fun getInteractPoint(): Point

    fun interact(action: String, option: String): Boolean

    fun interact(action: String): Boolean

    fun click(left: Boolean): Boolean

    fun click(): Boolean
}
