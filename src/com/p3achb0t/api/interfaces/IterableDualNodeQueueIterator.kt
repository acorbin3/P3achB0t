package com.p3achb0t.api.interfaces

interface IterableDualNodeQueueIterator {
    fun getQueue(): IterableDualNodeQueue
    fun get__c(): DualNode
    fun get__o(): DualNode
}
