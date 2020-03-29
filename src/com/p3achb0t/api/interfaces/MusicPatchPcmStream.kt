package com.p3achb0t.api.interfaces

interface MusicPatchPcmStream : PcmStream {
    fun getMixer(): PcmStreamMixer
    fun getQueue(): NodeDeque
    fun getSuperStream(): MidiPcmStream
}
