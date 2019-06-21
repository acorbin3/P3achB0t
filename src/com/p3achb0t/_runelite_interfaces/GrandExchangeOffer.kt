package com.p3achb0t._runelite_interfaces

interface GrandExchangeOffer {
    fun getCurrentPrice(): Int
    fun getCurrentQuantity(): Int
    fun getId(): Int
    fun getState(): Byte
    fun getTotalQuantity(): Int
    fun getUnitPrice(): Int
}
