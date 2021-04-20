package com.p3achb0t.api.wrappers.platforminfo

enum class JavaVendor(val value: Int){
    Sun(1),
    Microsoft(2),
    Apple(3),
    Other(4),
    Oracle(5);
    companion object {
        fun valueOf(value: Int): JavaVendor? = values().find { it.value == value }
    }

}