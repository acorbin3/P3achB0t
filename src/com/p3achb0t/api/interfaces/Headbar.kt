package com.p3achb0t.api.interfaces

interface Headbar : Node {
    fun getType(): HeadbarType
    fun getUpdates(): IterableNodeDeque
}
