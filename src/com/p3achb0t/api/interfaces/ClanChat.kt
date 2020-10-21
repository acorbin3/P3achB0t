package com.p3achb0t.api.interfaces

interface ClanChat : UserList {
    fun getLocalUser(): Usernamed
    fun getLoginType(): LoginType
    fun getMinKick(): Byte
    fun getName(): String
    fun getOwner(): String
    fun getRank(): Int
    fun get__x(): Int
}
