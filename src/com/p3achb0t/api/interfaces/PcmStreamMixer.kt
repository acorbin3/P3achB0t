package com.p3achb0t.api.interfaces

interface PcmStreamMixer : PcmStream {
    fun getSubStreams(): NodeDeque
    fun get__c(): Int
    fun get__d(): Int
    fun get__v(): NodeDeque
}
