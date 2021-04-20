package com.p3achb0t.api.wrappers.platforminfo

enum class OSType(val value: Int){
    WIN(1),
    MAC(2),
    LINUX(3),
    Other(4);

    companion object {
        fun valueOf(value: Int): OSType? = OSType.values().find { it.value == value }
    }
}