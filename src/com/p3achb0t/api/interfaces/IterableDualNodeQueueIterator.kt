package com.p3achb0t.api.interfaces

interface IterableDualNodeQueueIterator {
    fun getQueue(): IterableDualNodeQueue
    fun get__k(): DualNode
    fun get__s(): DualNode
}
