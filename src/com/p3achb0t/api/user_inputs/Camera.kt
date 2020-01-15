package com.p3achb0t.api.user_inputs

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.interfaces.Locatable
import com.p3achb0t.api.wrappers.utils.Timer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.event.KeyEvent
import java.lang.Thread.sleep
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.random.Random


class Camera(val ctx: Context) {
    val x: Int get() = ctx.client.getCameraX()
    val y: Int get() = ctx.client.getCameraY()
    val z: Int get() = ctx.client.getCameraZ()
    val yaw: Int get() = ctx.client.getCameraYaw()
    val pitch: Int get() = ((ctx.client.getCameraPitch() - 128).toDouble() / 255.0 * 100).toInt() // Convert pitch, 0-100
    val angle: Int get() = abs(((this.yaw / 5.68).toInt()) - 360)

    /**
     * View angel to specific Locatable
     *
     * @param locatable such as NPC/GameObject/Tile
     * @return Integer 0-360
     */
    suspend fun getAngleTo(locatable: Locatable): Int {
        val local = ctx.client.getLocalPlayer()

        var degree = 360 - Math.toDegrees(
                atan2(
                        (locatable.getRegionalLocation().x - local.getX()).toDouble(),
                        (local.getY() - locatable.getRegionalLocation().y).toDouble()
                )
        )
        if (degree >= 360) {
            degree -= 360.0
        }
        return degree.toInt()
    }

    suspend fun swingTo(locatable: Locatable) {
        CoroutineScope(IO).launch {
            turnAngleTo(locatable)
        }
    }


    suspend fun turnTo(locatable: Locatable)  {
        CoroutineScope(IO).launch {
            turnPitchTo(locatable)
            delay(100)
        }
        CoroutineScope(IO).launch {
            turnAngleTo(locatable)
            delay(100)
        }

    }

    suspend fun turnTononco(locatable: Locatable)  {
            turnPitchTononeco(locatable)
            delay(100)
            turnAngleTononeco(locatable)
            delay(100)

    }


    suspend fun turnWest() {
        CoroutineScope(IO).launch {
            setAngle(270 + Random.nextInt(-10, 10))
        }
    }

    suspend fun turnEast() {
        CoroutineScope(IO).launch {
            setAngle(90 + Random.nextInt(-10, 10))
        }
    }

    suspend fun turnNorth() {
        CoroutineScope(IO).launch {
            setAngle(0 + Random.nextInt(-10, 10))
        }
    }

    suspend fun turnSouth() {
        CoroutineScope(IO).launch {
            setAngle(180 + Random.nextInt(-10, 10))
        }
    }

    suspend fun setHighPitch() {
        CoroutineScope(IO).launch {
            if (pitch < 100)
                setPitch(90 + Random.nextInt(-5, 7))
        }
    }

    /**
     * set Pitch to given Pitch
     *
     * @param pitch
     * @return Boolean : true if it done correctly else False
     */
    suspend fun setPitch(pitch: Int): Boolean {
//            if (!Game.isLoggedIn())
//                return false

        var _pitch = this.pitch
        println("Pitch update: $_pitch -> $pitch")
        if (_pitch == pitch || Math.abs(_pitch - pitch) <= 5) {
            return true
        } else if (_pitch < pitch) {
            println("Starting to move camera UP")
            CoroutineScope(IO).launch {
                ctx.keyboard.pressDownKey(KeyEvent.VK_UP)
            }
                val t = Timer(5000)

            while (_pitch < pitch && abs(_pitch - pitch) > 5 && t.isRunning()) {
                _pitch = this.pitch
                sleep(59, 100)
            }
            ctx.keyboard.release(KeyEvent.VK_UP)
            println("Finished moving camera Up")

        } else if (_pitch > pitch) {
            println("Starting to move camera Down")
            CoroutineScope(IO).launch {
                ctx.keyboard.pressDownKey(KeyEvent.VK_DOWN)
            }
            val t = Timer(5000)

            while (_pitch > pitch && abs(_pitch - pitch) > 5 && t.isRunning()) {
                _pitch = this.pitch
                sleep(59, 100)
            }
            CoroutineScope(IO).launch {
                ctx.keyboard.release(KeyEvent.VK_DOWN)
            }
            println("Finished moving camera Down")
        }

        return Math.abs(_pitch - pitch) <= 5
    }

    /**
     * @param angle to change
     * @return true if done else false
     */
    suspend fun setAngle(angle: Int): Boolean {
//            if (!Game.isLoggedIn())
//                return false

        val curAngle = this.angle
        val diff = getDiff(curAngle, angle)
        var dir = -1
        var turnLeft = false

        if (normalizeAngle(curAngle + diff) == angle) {
            turnLeft = true
            dir = KeyEvent.VK_LEFT
        } else {
            dir = KeyEvent.VK_RIGHT
        }

        val finalRawAngle = if (turnLeft) curAngle + diff else curAngle - diff
        var curRawAngle = this.angle
        if (diff > 5) {
            // Figure out where we are
            println("Starting to swing camera Left or right")
            ctx.keyboard.pressDownKey(dir)
            for (i in 0..99) {

                if (turnLeft) {
                    curRawAngle = (if (this.angle >= curRawAngle) 0 else 360) + this.angle

                    if (curRawAngle > finalRawAngle)
                        break
                } else {
                    curRawAngle = (if (this.angle <= curRawAngle) 0 else -360) + this.angle

                    if (curRawAngle < finalRawAngle)
                        break
                }

                sleep(100, 200)
            }
            ctx.keyboard.release(dir)
            println("Finished moving camera Left or right")
        }
        return getDiff(this.angle, angle) <= 5
    }

    /**
     * Change Pitch depend on distance between Player and locatable , if 0 > Pitch > 90 it will return random 5,10
     *
     * @param locatable
     */
    //Higest pitch is 383?, lowest is 128
    suspend fun  turnPitchTo(locatable: Locatable) {
        CoroutineScope(IO).launch {
            var pitch = 90 - locatable.distanceTo() * 9
            val factor = if (Random.nextInt(0, 1) == 0) -1 else 1
            pitch += factor * Random.nextInt(5, 10)

            if (pitch > 90) {
                pitch = 90
            } else if (pitch < 0) {
                pitch = Random.nextInt(5, 10)
            }

            setPitch(pitch)
        }
    }

    suspend fun  turnPitchTononeco(locatable: Locatable) {
            var pitch = 90 - locatable.distanceTo() * 9
            val factor = if (Random.nextInt(0, 1) == 0) -1 else 1
            pitch += factor * Random.nextInt(5, 10)

            if (pitch > 90) {
                pitch = 90
            } else if (pitch < 0) {
                pitch = Random.nextInt(5, 10)
            }

            setPitch(pitch)
    }

    /**
     * Change angel to locatable angel so it's get on screen
     *
     * @param locatable
     */
    suspend fun turnAngleTo(locatable: Locatable) {
        CoroutineScope(IO).launch {
            var set = getAngleTo(locatable)

            if (set > 180) {
                set -= 180
            } else {
                set += 180
            }

            setAngle(set)
        }
    }

    suspend fun turnAngleTononeco(locatable: Locatable) {
            var set = getAngleTo(locatable)

            if (set > 180) {
                set -= 180
            } else {
                set += 180
            }

            setAngle(set)
    }

    private suspend fun normalizeAngle(angle: Int): Int {
        return angle % 360
    }

    private suspend fun getDiff(curAngle: Int, angle: Int): Int {
        val raw_diff = normalizeAngle(Math.abs(angle - curAngle))
        return if (raw_diff > 180) 360 - raw_diff else raw_diff
    }
}