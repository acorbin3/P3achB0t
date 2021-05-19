package com.p3achb0t.api.interfaces

interface MilliClock : Clock {
    fun get__c(): Int
    fun get__l(): Int
    fun get__o(): Int
    fun get__z(): Int
    fun get__g(): Long
    fun get__h(): Array<Long>
}
