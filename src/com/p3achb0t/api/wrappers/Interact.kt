package com.p3achb0t.api.wrappers

import com.p3achb0t.Main
import com.p3achb0t.api.user_inputs.Mouse
import kotlinx.coroutines.delay
import java.awt.Point
import java.awt.Polygon
import java.awt.Rectangle
import kotlin.random.Random

// This class is used to interact with points on a screen and menu Items such as players, objects, NPCs
class Interact {
    companion object {
        suspend fun interact(rectangle: Rectangle, action: String) {
            //Checking to see if this is on screen
            var point: Point? = null
            //Pick random point in hull
            while (true) {
                point = Point(
                    Random.nextInt(rectangle.bounds.x, rectangle.bounds.width + rectangle.bounds.x),
                    Random.nextInt(rectangle.bounds.y, rectangle.bounds.height + rectangle.bounds.y)
                )
                if (rectangle.contains(point))
                    break
            }
            point?.let { interact(it, action) }
        }
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
                point?.let { interact(it, action) }
            }
        }

        //TODO Check if action is in the menu, if not move off menuse and retry
        suspend fun interact(point: Point, action: String): Boolean {
            println("${point.x},${point.y}")
            if (point == Point(-1, -1)) {
                return false
            }
            if (action.isNotEmpty()) {
                // TODO - Check to see if we need to right click or not
                point.let { it1 -> Main.mouse.moveMouse(it1, click = true, clickType = Mouse.ClickType.Right) }
                delay((Math.random() * 50).toLong())
                // Move to Action String
                val actionPoint = Menu.getPointForInteraction(action)
                if (actionPoint == Point(-1, -1)) {
                    //Get large box, pick random point thats outside of the menue rect
                    val menuRect = Menu.getRect()
                    while (true) {
                        val x = Random.nextInt(point.x - menuRect.width, point.x + menuRect.width)
                        val y = Random.nextInt(point.y - menuRect.height, point.y + menuRect.height)
                        val newPoint = Point(x, y)
                        // Move mouse out side of menue
                        if (!menuRect.bounds.contains(newPoint)) {
                            Main.mouse.moveMouse(newPoint)
                            break
                        }
                    }
                    //Reinteracte with menu
                    interact(point, action)
                }
                println("Clicking $action")
                var res = Main.mouse.moveMouse(actionPoint, click = true)
                println("Res: $res")
                delay((Math.random() * 200 + 100).toLong())
                if (res) return true
                var count = 0
                while (Main.clientData.getMenuVisible()) {
                    delay((Math.random() * 50).toLong())
                    count += 1
                    if (count == 10) {
                        val cancelPoint = Menu.getPointForInteraction(action)
                        res = Main.mouse.moveMouse(cancelPoint, click = true)
                        print("Failed, retrying")
                    }
                }
                return res
            } else {
                point.let { it1 -> return Main.mouse.moveMouse(it1, click = true, clickType = Mouse.ClickType.Left) }
            }
        }
    }
}