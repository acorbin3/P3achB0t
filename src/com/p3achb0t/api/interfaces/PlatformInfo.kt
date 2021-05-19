package com.p3achb0t.api.interfaces

interface PlatformInfo : Node {
    fun setCpuCores(value: Int)
    fun setJavaVendorType(value: Int)
    fun setJavaVersionMajor(value: Int)
    fun setJavaVersionMinor(value: Int)
    fun setJavaVersionPatch(value: Int)
    fun setMaxMemoryMB(value: Int)
    fun setOs64Bit(value: Boolean)
    fun setOsType(value: Int)
    fun setOsVersion(value: Int)
    fun set__ai(value: Boolean)
    fun set__ac(value: Int)
    fun set__ad(value: Int)
    fun set__af(value: Int)
    fun set__ah(value: Int)
    fun set__an(value: Int)
    fun set__bb(value: Int)
    fun set__bd(value: Int)
    fun getCpuCores(): Int
    fun getJavaVendorType(): Int
    fun getJavaVersionMajor(): Int
    fun getJavaVersionMinor(): Int
    fun getJavaVersionPatch(): Int
    fun getMaxMemoryMB(): Int
    fun getOs64Bit(): Boolean
    fun getOsType(): Int
    fun getOsVersion(): Int
    fun get__ai(): Boolean
    fun get__ac(): Int
    fun get__ad(): Int
    fun get__af(): Int
    fun get__ah(): Int
    fun get__an(): Int
    fun get__bb(): Int
    fun get__bd(): Int
    fun get__bu(): IntArray
    fun get__ab(): String
    fun get__aq(): String
    fun get__ar(): String
    fun get__az(): String
    fun get__bf(): String
    fun get__bk(): String
    fun get__bw(): String
}
