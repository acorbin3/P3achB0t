package com.p3achb0t._runelite_interfaces

interface HealthBarUpdate : Node {
    fun getCycle(): Int
    fun getCycleOffset(): Int
    fun getHealth(): Int
    fun getHealth2(): Int
}
