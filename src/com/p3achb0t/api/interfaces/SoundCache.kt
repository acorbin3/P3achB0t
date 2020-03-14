package com.p3achb0t.api.interfaces

interface SoundCache {
    fun getRawSounds(): NodeHashTable
    fun getSoundEffectIndex(): AbstractArchive
    fun getVorbisSampleIndex(): AbstractArchive
    fun getVorbisSamples(): NodeHashTable
}
