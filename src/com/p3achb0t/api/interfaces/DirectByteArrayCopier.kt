package com.p3achb0t.api.interfaces

interface DirectByteArrayCopier : AbstractByteArrayCopier {
    fun getDirectBuffer(): Any
}
