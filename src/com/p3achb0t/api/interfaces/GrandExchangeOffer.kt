package com.p3achb0t.api.interfaces

interface GrandExchangeOffer {
    fun getCurrentPrice(): Int
    fun getCurrentQuantity(): Int
    fun getId(): Int
    fun getState(): Byte
    fun getTotalQuantity(): Int
    fun getUnitPrice(): Int
}
