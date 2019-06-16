package com.p3achb0t.hook_interfaces

interface MessageNode : CacheNode {
    fun getClan(): String
    fun getNameComposite(): NameComposite
    fun getSender(): String
    fun getText(): String
    fun getTime(): Int
    fun getType(): Int
}
