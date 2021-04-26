package com.p3achb0t.api.pricecheck

data class ItemInfo(
    val examine: String,
    val id: Int,
    val members: Boolean,
    val lowalch: Int,
    val limit: Int,
    val value: Int,
    val highalch: Int,
    val icon: String,
    val name: String
)
