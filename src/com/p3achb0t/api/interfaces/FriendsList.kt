package com.p3achb0t.api.interfaces

interface FriendsList : UserList {
    fun getFriendLoginUpdates(): LinkDeque
    fun getLoginType(): LoginType
    fun get__z(): Int
}
