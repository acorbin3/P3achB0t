package com.p3achb0t.hook_interfaces

interface ClanMemberHandler : NameProviderHandler {
    fun get_clanName(): Any
    fun get_clanOwner(): Any
}
