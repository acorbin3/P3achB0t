package com.p3achb0t.api

import kotlinx.coroutines.delay
import java.util.regex.Pattern
import kotlin.random.Random


class Utils {
    interface Condition {
        suspend fun accept(): Boolean
    }

    companion object {
        suspend fun waitFor(time: Int, condition: Condition): Boolean {
            var t = Timer(Random.nextLong((time * 1000).toLong(), ((time + 2) * 1000).toLong()))
            while (t.isRunning()) {
                if (condition.accept())
                    return true
                delay(Random.nextLong(100, 150))
            }
            return false
        }

        fun cleanColorText(input: String): String {
            return Pattern.compile("<.+?>").matcher(input).replaceAll("")
        }

    }
}