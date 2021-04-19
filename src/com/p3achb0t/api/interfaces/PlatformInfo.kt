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
    fun set__aw(value: Boolean)
    fun set__ab(value: Int)
    fun set__ae(value: Int)
    fun set__ap(value: Int)
    fun set__au(value: Int)
    fun set__av(value: Int)
    fun set__bb(value: Int)
    fun set__bh(value: Int)
    fun getCpuCores(): Int
    fun getJavaVendorType(): Int
    fun getJavaVersionMajor(): Int
    fun getJavaVersionMinor(): Int
    fun getJavaVersionPatch(): Int
    fun getMaxMemoryMB(): Int
    fun getOs64Bit(): Boolean
    fun getOsType(): Int
    fun getOsVersion(): Int
    fun get__aw(): Boolean
    fun get__ab(): Int
    fun get__ae(): Int
    fun get__ap(): Int
    fun get__au(): Int
    fun get__av(): Int
    fun get__bb(): Int
    fun get__bh(): Int
    fun get__bi(): IntArray
    fun get__aa(): String
    fun get__ak(): String
    fun get__al(): String
    fun get__ar(): String
    fun get__bk(): String
    fun get__bn(): String
    fun get__br(): String
}
