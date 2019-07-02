package com.p3achb0t.api.wrappers

import com.p3achb0t.Main
import com.p3achb0t.api.user_inputs.Mouse
import kotlinx.coroutines.delay
import java.awt.Point
import java.awt.Polygon
import kotlin.random.Random

// This class is used to interact with points on a screen and menu Items such as players, objects, NPCs
class Interact {
    companion object {
        suspend fun interact(ch: Polygon, action: String) {
            //Checking to see if this is on screen
            if (ch.npoints > 0) {
                var point: Point? = null
                //Pick random point in hull
                while (true) {
                    point = Point(
                        Random.nextInt(ch.bounds.x, ch.bounds.width + ch.bounds.x),
                        Random.nextInt(ch.bounds.y, ch.bounds.height + ch.bounds.y)
                    )
                    if (ch.contains(point))
                        break
                }
                // Check to see if we need to right click or not

                point?.let { it1 -> Main.mouse.moveMouse(it1, click = true, clickType = Mouse.ClickType.Right) }
                delay((Math.random() * 50).toLong())
                // Move to Cancel
                val cancelPoint = Menu.getPointForInteraction(action)
                Main.mouse.moveMouse(cancelPoint, click = true)
                var count = 0
                while (Main.clientData.getMenuVisible()) {
                    delay((Math.random() * 50).toLong())
                    count += 1
                    if (count == 10) {
                        val cancelPoint = Menu.getPointForInteraction(action)
                        Main.mouse.moveMouse(cancelPoint, click = true)
                        print("Failed, retrying")
                    }
                }
            }
        }
    }
}