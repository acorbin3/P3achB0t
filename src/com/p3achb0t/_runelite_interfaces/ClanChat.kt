package com.p3achb0t._runelite_interfaces

interface ClanChat : UserList {
    fun getLocalUser(): Usernamed
    fun getLoginType(): LoginType
    fun getName(): String
    fun getOwner(): String
    fun getRank(): Int
    fun get__i(): Int
    fun get__k(): Byte
}
