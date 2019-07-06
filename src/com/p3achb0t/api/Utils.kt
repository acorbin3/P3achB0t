package com.p3achb0t.api

import kotlinx.coroutines.delay
import kotlin.random.Random


class Utils {
    interface Condition {
        suspend fun accept(): Boolean
    }

    companion object {
        suspend fun waitFor(time: Int, condition: Condition): Boolean {
            var t = Timer(Random.nextLong((time * 1000).toLong(), ((time + 2) * 1000).toLong()))
            while (t.isRunning()) {
//                if (Players.getLocal().isMoving())
//                    t.reset();
                if (condition.accept())
                    return true
                delay(Random.nextLong(100, 150))
            }
            return false
        }
    }
}