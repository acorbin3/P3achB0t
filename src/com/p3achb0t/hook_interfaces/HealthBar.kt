package com.p3achb0t.hook_interfaces

interface HealthBar : Node {
    fun getComposite(): HealthBarComposite
    fun getData(): Iterable
}
