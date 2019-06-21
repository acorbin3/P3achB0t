package com.p3achb0t._runelite_interfaces

interface SoundCache {
    fun getMusicSampleIndex(): AbstractIndexCache
    fun getMusicSamples(): NodeHashTable
    fun getRawSounds(): NodeHashTable
    fun getSoundEffectIndex(): AbstractIndexCache
}
