package com.p3achb0t.api

class Utils {
    interface Condition {
        fun accept(): Boolean
    }

    companion object {
        fun waitFor(time: Int, condition: Condition) {

        }
    }
}