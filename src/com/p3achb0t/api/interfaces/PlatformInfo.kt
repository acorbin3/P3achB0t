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
    fun set__av(value: Boolean)
    fun set__aa(value: Int)
    fun set__an(value: Int)
    fun set__ao(value: Int)
    fun set__aq(value: Int)
    fun set__au(value: Int)
    fun set__bd(value: Int)
    fun set__bl(value: Int)
    fun getCpuCores(): Int
    fun getJavaVendorType(): Int
    fun getJavaVersionMajor(): Int
    fun getJavaVersionMinor(): Int
    fun getJavaVersionPatch(): Int
    fun getMaxMemoryMB(): Int
    fun getOs64Bit(): Boolean
    fun getOsType(): Int
    fun getOsVersion(): Int
    fun get__av(): Boolean
    fun get__aa(): Int
    fun get__an(): Int
    fun get__ao(): Int
    fun get__aq(): Int
    fun get__au(): Int
    fun get__bd(): Int
    fun get__bl(): Int
    fun get__bu(): IntArray
    fun get__af(): String
    fun get__ak(): String
    fun get__aw(): String
    fun get__ay(): String
    fun get__bq(): String
    fun get__bt(): String
    fun get__bv(): String
}
