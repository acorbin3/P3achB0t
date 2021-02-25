package com.p3achb0t.api.interfaces

interface MilliClock : Clock {
    fun get__d(): Int
    fun get__h(): Int
    fun get__v(): Int
    fun get__y(): Int
    fun get__c(): Long
    fun get__n(): Array<Long>
}
