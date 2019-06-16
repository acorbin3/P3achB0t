package com.p3achb0t.hook_interfaces

interface NameProviderHandler {
    fun getNameProviders(): Array<NameProvider>
    fun getSize(): Int
}
