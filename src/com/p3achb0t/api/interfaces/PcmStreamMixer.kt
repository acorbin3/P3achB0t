package com.p3achb0t.api.interfaces

interface PcmStreamMixer : PcmStream {
    fun getSubStreams(): NodeDeque
    fun get__g(): Int
    fun get__o(): Int
    fun get__c(): NodeDeque
}
