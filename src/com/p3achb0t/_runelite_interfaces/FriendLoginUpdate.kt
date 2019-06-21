package com.p3achb0t._runelite_interfaces

interface FriendLoginUpdate : Link {
    fun getTime(): Int
    fun getUsername(): Username
    fun getWorld(): Short
}
