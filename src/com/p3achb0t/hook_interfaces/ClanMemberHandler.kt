package com.p3achb0t.hook_interfaces

interface ClanMemberHandler : NameProviderHandler {
    fun getClanName(): String
    fun getClanOwner(): String
}
