package com.p3achb0t.api.interfaces

interface UserList {
    fun getArray(): Array<User>
    fun getCapacity(): Int
    fun getComparator(): Any
    fun getPreviousUsernamesMap(): Any
    fun getSize0(): Int
    fun getUsernamesMap(): Any
}
