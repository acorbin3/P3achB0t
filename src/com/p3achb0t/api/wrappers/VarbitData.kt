package com.p3achb0t.api.wrappers

data class VarbitData (
        val id: Int,
        val startBit: Int,
        val endBit: Int,
        val baseVar: Int
){
    companion object{
        var masklookup: IntArray = IntArray(32)
    }
    init {
        var n = 2
        for (i in 0..31) {
            masklookup[i] = n - 1
            n += n
        }
    }

    fun getVarbitValue(n: Int): Int {
        val n2: Int = masklookup[endBit - startBit]
        return n shr this.startBit and n2
    }
}