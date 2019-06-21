package com.p3achb0t._runelite_interfaces

interface FriendsList : UserList {
    fun getFriendLoginUpdates(): LinkDeque
    fun getLoginType(): LoginType
    fun get__x(): Int
}
