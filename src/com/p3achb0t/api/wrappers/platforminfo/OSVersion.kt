package com.p3achb0t.api.wrappers.platforminfo

enum class OSVersion(val value: Int) {
    Other(-1),
    _4_0(1),
    _4_9(2),
    _5_0(3),
    _5_1(4),
    _5_2(5),
    _6_0(6),
    _6_1(7), // This is windows 7
    _6_2(9),
    _6_3(10),
    _10_0(11), // This is win 10

    //Next set is Mac version
    _10_4(20),
    _10_5(21),
    _10_6(22),
    _10_7(23),
    _10_8(24),
    _10_9(25),
    _10_10(26),
    _10_11(27),
    _10_12(28),
    _10_13(29);

    companion object {
        fun valueOf(value: Int): OSVersion? = values().find { it.value == value }
    }
}