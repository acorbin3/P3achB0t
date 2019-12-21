package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Calculations
import com.p3achb0t.api.Context
import com.p3achb0t.api.user_inputs.Mouse
import kotlinx.coroutines.delay
import java.awt.Point
import java.awt.Polygon
import java.awt.Rectangle
import kotlin.random.Random

// This class is used to interact with points on a screen and menu Items such as players, objects, NPCs
class Interact(val ctx: Context) {
    suspend fun interact(rectangle: Rectangle) {
        interact(rectangle, "")
    }

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
                if (ch.contains(point) && Calculations.isOnscreen(ctx, point))
                    break
            }
            point?.let { interact(it, action) }
        }
    }


    suspend fun interact(point: Point, action: String, retryCount: Int = 0): Boolean {
        println("Action: $action ${point.x},${point.y}")
        if (point == Point(-1, -1)) {
            return false
        }
        if (action.isNotEmpty()) {

            //Move ctx.mouse to the point

            if (ctx.mouse.getX() == point.x && ctx.mouse.getY() == point.y) {
                //Dont need to move the ctx.mouse
            } else {
                point.let { it1 -> ctx.mouse.moveMouse(it1, click = false) }
                delay(Random.nextLong(50, 150)) // Delay just to make sure we pick up the correct menu option
            }

            // Check to see if we need to right click or not
            if (ctx.menu.getHoverAction().contains(action)) {
                // Left click, we are alreay there
                point.let { it1 -> ctx.mouse.click(it1) }
                return true
            } else {
                //Since this is not avaliable we need to right click
                point.let { it1 -> ctx.mouse.click(it1, clickType = Mouse.ClickType.Right) }
                delay(Random.nextLong(50, 150))
                // Move to Action String
                val actionPoint = ctx.menu.getPointForInteraction(action)
                println("Action point: $actionPoint")
                if (actionPoint == Point(-1, -1)) {
                    //Get large box, pick random point thats outside of the menue rect
                    val menuRect = ctx.menu.getRect()
                    while (true) {
                        val x = Random.nextInt(point.x - menuRect.width, point.x + menuRect.width)
                        val y = Random.nextInt(point.y - menuRect.height, point.y + menuRect.height)
                        val newPoint = Point(x, y)
                        // Move ctx.mouse out side of menue
                        if (!menuRect.bounds.contains(newPoint)) {
                            ctx.mouse.moveMouse(newPoint)
                            break
                        }
                    }
                    //Give up after 2 trys
                    if (retryCount == 1) return false
                    //Reinteracte with menu
                    println("Retrying interaction -> $action")
                    interact(point, action, retryCount + 1)
                }
                if (actionPoint == Point(-1, -1)) {
                    print("Could not find $action in menue")
                    return false
                }
                println("Clicking $action")

                var res = ctx.mouse.moveMouse(actionPoint, click = true)
                println("Res: $res")
                delay((Math.random() * 200 + 100).toLong())
                if (res) return true
                var count = 0
                while (ctx.client.getIsMiniMenuOpen()) {
                    delay((Math.random() * 50).toLong())
                    count += 1
                    if (count == 5) {
                        val cancelPoint = ctx.menu.getPointForInteraction(action)
                        res = ctx.mouse.moveMouse(cancelPoint, click = true)
                        print("Failed, retrying")
                    }
                }
                return res
            }
        } else {
            point.let { it1 ->
                return ctx.mouse.moveMouse(
                        it1,
                        click = true,
                        clickType = Mouse.ClickType.Left
                )
            }
        }
    }
}