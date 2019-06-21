package com.p3achb0t._runelite_interfaces

interface MusicPatchPcmStream : PcmStream {
    fun getMixer(): PcmStreamMixer
    fun getQueue(): NodeDeque
    fun get_superStream(): MidiPcmStream
}
