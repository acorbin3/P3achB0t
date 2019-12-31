package com.p3achb0t._runestar_interfaces

interface MusicPatchPcmStream : PcmStream {
    fun getMixer(): PcmStreamMixer
    fun getQueue(): NodeDeque
    fun getSuperStream(): MidiPcmStream
}
