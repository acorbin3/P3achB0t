package com.p3achb0t.api.interfaces

interface Message : DualNode {
    fun getCount(): Int
    fun getCycle(): Int
    fun getIsFromFriend0(): TriBool
    fun getIsFromIgnored0(): TriBool
    fun getPrefix(): String
    fun getSender(): String
    fun getSenderUsername(): Username
    fun getText(): String
    fun getType(): Int
}
