package com.p3achb0t.api.wrappers

import java.lang.Math.pow


enum class Orientation(val value: IntRange = 0..pow(2.0, 8.0).toInt()) {
    SOUTH(value = 0..127),
    SOUTH_WEST(value = 256 - 127..256 + 128),
    WEST(value = 512 - 127..512 + 128),
    NORTH_WEST(value = 768 - 127..768 + 128),
    NORTH(value = 1024 - 127..1024 + 128),
    NORTH_EAST(value = 1280 - 127..1280 + 128),
    EAST(value = 1536 - 127..1536 + 128),
    SOUTH_EAST(value = 1792 - 127..1792 + 128),
    SOUTH_CONTINUE(value = 2048 - 127..2048);

    companion object {
        private val map = Orientation.values().associateBy(Orientation::value)
        fun fromInt(type: Int):Orientation {
            return when (type) {
                in SOUTH.value -> SOUTH
                in SOUTH_WEST.value -> SOUTH_WEST
                in WEST.value -> WEST
                in NORTH_WEST.value -> NORTH_WEST
                in NORTH.value -> NORTH
                in NORTH_EAST.value -> NORTH_EAST
                in EAST.value -> EAST
                in SOUTH_EAST.value -> SOUTH_EAST
                in SOUTH_CONTINUE.value -> SOUTH
                else -> SOUTH
            }
        }

    }
}