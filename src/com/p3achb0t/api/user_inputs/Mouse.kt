package com.p3achb0t.api.user_inputs


import com.naturalmouse.api.MouseMotionFactory
import com.naturalmouse.custom.RuneScapeFactoryTemplates
import com.p3achb0t.api.wrappers.GameObject
import com.p3achb0t.api.wrappers.GroundItem
import com.p3achb0t.api.wrappers.NPC
import com.p3achb0t.client.interfaces.io.Mouse
import com.p3achb0t.interfaces.IScriptManager
import kotlinx.coroutines.delay
import java.applet.Applet
import java.awt.Component
import java.awt.Point
import java.awt.event.MouseEvent


// This class will be used to specify want will be requested do action params when doing a click from the mouse
data class DoActionParams(
        val actionParam: Int = 0,
        val widgetID: Int = 0,
        val menuAction: Int = 0,
        val id: Int = 0,
        val menuOption: String = "",
        val menuTarget: String = "",
        val mouseX: Int = 0,
        val mouseY: Int = 0
)

// This class was replicated based on the mouse movement from:
// https://github.com/cfoust/jane/blob/master/src/automata/tools/input/Mouse.java

class Mouse(obj: Any) {

    companion object {
       var x = 0
        var y = 0

    }

    var overrideDoActionParams = false
    var doActionParams: DoActionParams = DoActionParams()
    private val component: Component = (obj as Applet).getComponent(0)
    private val mouseMotionFactory: MouseMotionFactory = RuneScapeFactoryTemplates.createFastGamerMotionFactory(obj)
    private val mouseHopping: MouseHop = MouseHop(obj as IScriptManager, obj as Applet)
    private val ioMouse: Mouse = (obj as IScriptManager).getMouse()

    fun getX(): Int {
        return ioMouse.getX()
    }


    fun getY(): Int {
        return ioMouse.getY()
    }

    fun getPosition(): Point{
        return Point(ioMouse.getX(),ioMouse.getY())
    }

    enum class ClickType {
        Right,
        Left
    }


    //////
    // Config info on how the mouse would operate

    // Interval in pixels at which we will send the mouse events
    private val MIN_DIST_PIXELS = 2

    private val RATE_PIXELS_PER_SEC = 700

    //////
    //The default position is current position
    suspend fun click(destPoint: Point = getPosition(), clickType: ClickType = ClickType.Left): Boolean {
        return moveMouse(destPoint = destPoint, click = true, clickType = clickType)
    }

    fun doAction(doActionParams: DoActionParams){
        this.overrideDoActionParams = true
        this.doActionParams = doActionParams
        instantclick(Point(0,0))
    }

    fun instantclick(destPoint: Point, click: Boolean = true, clickType: ClickType = ClickType.Left): Boolean {


//        mouseMotionFactory.move(destPoint.x, destPoint.y)
        mouseHopping.setMousePosition(destPoint)

        if (click) {
            val clickMask = if (clickType == ClickType.Right) MouseEvent.BUTTON3_MASK else MouseEvent.BUTTON1_MASK
            val mousePress =
                    MouseEvent(
                            component,
                            MouseEvent.MOUSE_PRESSED,
                            System.currentTimeMillis(),
                            clickMask,
                            destPoint.x,
                            destPoint.y,
                            0,
                            clickType == ClickType.Right
                    )

            ioMouse.sendEvent(mousePress)

            val mouseRelease =
                    MouseEvent(
                            component,
                            MouseEvent.MOUSE_RELEASED,
                            System.currentTimeMillis(),
                            clickMask,
                            destPoint.x,
                            destPoint.y,
//                                if(Random.nextLong(1000, 20000) < 2000){
//                                    1
//                                }
//                                else 0,
                            0,
                            clickType == ClickType.Right
                    )
            ioMouse.sendEvent(mouseRelease)
        }
        return true
    }


    suspend fun instaMove(destPoint: Point){
        mouseHopping.move(destPoint)
    }
    suspend fun moveMouse(destPoint: Point, click: Boolean = false, clickType: ClickType = ClickType.Left): Boolean {
        if (destPoint == Point(-1, -1)) {
            return false
        }

        mouseMotionFactory.move(destPoint.x, destPoint.y)
//        mouseHopping.move(destPoint)

        if (click) {
            val clickMask = if (clickType == ClickType.Right) MouseEvent.BUTTON3_MASK else MouseEvent.BUTTON1_MASK
            val mousePress =
                    MouseEvent(
                            component,
                            MouseEvent.MOUSE_PRESSED,
                            System.currentTimeMillis(),
                            clickMask,
                            destPoint.x,
                            destPoint.y,
//                                if(Random.nextLong(1000, 20000) < 2000){
//                                    1
//                                }
//                                else 0,
                            0,
                            clickType == ClickType.Right
                    )

            ioMouse.sendEvent(mousePress)

            // Create a random number 30-70 to delay between clicks
            var delayTime = Math.floor(Math.random() * 40 + 30)
            delay(delayTime.toLong())
            val mouseRelease =
                    MouseEvent(
                            component,
                            MouseEvent.MOUSE_RELEASED,
                            System.currentTimeMillis(),
                            clickMask,
                            destPoint.x,
                            destPoint.y,
//                                if(Random.nextLong(1000, 20000) < 2000){
//                                    1
//                                }
//                                else 0,
                            0,
                            clickType == ClickType.Right
                    )
            ioMouse.sendEvent(mouseRelease)
            delayTime = Math.floor(Math.random() * 50 + 100)
            delay(delayTime.toLong())
        }
        return true
    }


    suspend fun moveMouseNPC(npc: NPC, destPoint: Point, click: Boolean = false, clickType: ClickType = ClickType.Left): Boolean {
        val objpoint = npc.getInteractPoint()
        mouseMotionFactory.move(objpoint.x, objpoint.y)

//        mouseHopping.move(destPoint)
        var hasclicked = false
        if (click && npc.isMouseOverObj() && !hasclicked) {
            val clickMask = if (clickType == ClickType.Right) MouseEvent.BUTTON3_MASK else MouseEvent.BUTTON1_MASK
            val mousePress =
                    MouseEvent(
                            component,
                            MouseEvent.MOUSE_PRESSED,
                            System.currentTimeMillis(),
                            clickMask,
                            destPoint.x,
                            destPoint.y,
//                                if(Random.nextLong(1000, 20000) < 2000){
//                                    1
//                                }
//                                else 0,
                            0,
                            clickType == ClickType.Right
                    )

            ioMouse.sendEvent(mousePress)

            // Create a random number 30-70 to delay between clicks
            var delayTime = Math.floor(Math.random() * 40 + 30)
            delay(delayTime.toLong())
            val mouseRelease =
                    MouseEvent(
                            component,
                            MouseEvent.MOUSE_RELEASED,
                            System.currentTimeMillis(),
                            clickMask,
                            destPoint.x,
                            destPoint.y,
//                                if(Random.nextLong(1000, 20000) < 2000){
//                                    1
//                                }
//                                else 0,
                            0,
                            clickType == ClickType.Right
                    )
            ioMouse.sendEvent(mouseRelease)
            delayTime = Math.floor(Math.random() * 50 + 100)
            delay(delayTime.toLong())
            hasclicked = true
        }



//        var retries = 0
//        while(!npc.isMouseOverObj() && !hasclicked){
//            println("Mouse not over object")
//            println("retries = " + retries)
//            val npcpoint = npc.getInteractPoint()
//            mouseMotionFactory.move(npcpoint.x, npcpoint.y)
//            if (click && npc.isMouseOverObj() && !hasclicked) {
//                val clickMask = if (clickType == ClickType.Right) MouseEvent.BUTTON3_MASK else MouseEvent.BUTTON1_MASK
//                val mousePress =
//                        MouseEvent(
//                                component,
//                                MouseEvent.MOUSE_PRESSED,
//                                System.currentTimeMillis(),
//                                clickMask,
//                                destPoint.x,
//                                destPoint.y,
////                                if(Random.nextLong(1000, 20000) < 2000){
////                                    1
////                                }
////                                else 0,
//                                0,
//                                clickType == ClickType.Right
//                        )
//
//                ioMouse.sendEvent(mousePress)
//
//                // Create a random number 30-70 to delay between clicks
//                var delayTime = Math.floor(Math.random() * 40 + 30)
//                delay(delayTime.toLong())
//                val mouseRelease =
//                        MouseEvent(
//                                component,
//                                MouseEvent.MOUSE_RELEASED,
//                                System.currentTimeMillis(),
//                                clickMask,
//                                destPoint.x,
//                                destPoint.y,
////                                if(Random.nextLong(1000, 20000) < 2000){
////                                    1
////                                }
////                                else 0,
//                                0,
//                                clickType == ClickType.Right
//                        )
//                ioMouse.sendEvent(mouseRelease)
////            delayTime = Math.floor(Math.random() * 50 + 100)
////            delay(delayTime.toLong())
//                hasclicked = true
//            }
//            retries = retries + 1
//            if(npc.isMouseOverObj() || retries > 4 || hasclicked){
//                break
//            }
//        }
        return true
    }

    suspend fun moveMouseGroundItem(grounditem: GroundItem, destPoint: Point, click: Boolean = false, clickType: ClickType = ClickType.Left): Boolean {
        val grounditem = grounditem.getInteractPoint()
        mouseMotionFactory.move(grounditem.x, grounditem.y)

//        mouseHopping.move(destPoint)
        var hasclicked = false
        if (click && !hasclicked) {
            val clickMask = if (clickType == ClickType.Right) MouseEvent.BUTTON3_MASK else MouseEvent.BUTTON1_MASK
            val mousePress =
                    MouseEvent(
                            component,
                            MouseEvent.MOUSE_PRESSED,
                            System.currentTimeMillis(),
                            clickMask,
                            destPoint.x,
                            destPoint.y,
//                                if(Random.nextLong(1000, 20000) < 2000){
//                                    1
//                                }
//                                else 0,
                            0,
                            clickType == ClickType.Right
                    )

            ioMouse.sendEvent(mousePress)

            // Create a random number 30-70 to delay between clicks
            var delayTime = Math.floor(Math.random() * 40 + 30)
            delay(delayTime.toLong())
            val mouseRelease =
                    MouseEvent(
                            component,
                            MouseEvent.MOUSE_RELEASED,
                            System.currentTimeMillis(),
                            clickMask,
                            destPoint.x,
                            destPoint.y,
//                                if(Random.nextLong(1000, 20000) < 2000){
//                                    1
//                                }
//                                else 0,
                            0,
                            clickType == ClickType.Right
                    )
            ioMouse.sendEvent(mouseRelease)
            delayTime = Math.floor(Math.random() * 50 + 100)
            delay(delayTime.toLong())
            hasclicked = true
        }
        return true
    }

    suspend fun moveMouseObject(obj: GameObject, destPoint: Point, click: Boolean = false, clickType: ClickType = ClickType.Left): Boolean {
        val objpoint = obj.getInteractPoint()
        println("Moving mouse to object: " + objpoint)
        mouseMotionFactory.move(objpoint.x, objpoint.y)
//        mouseHopping.move(destPoint)
        var hasclicked = false
        if (click && obj.isMouseOverObj() && !hasclicked) {
            val clickMask = if (clickType == ClickType.Right) MouseEvent.BUTTON3_MASK else MouseEvent.BUTTON1_MASK
            val mousePress =
                    MouseEvent(
                            component,
                            MouseEvent.MOUSE_PRESSED,
                            System.currentTimeMillis(),
                            clickMask,
                            destPoint.x,
                            destPoint.y,
//                                if(Random.nextLong(1000, 20000) < 2000){
//                                    1
//                                }
//                                else 0,
                            0,
                            clickType == ClickType.Right
                    )

            ioMouse.sendEvent(mousePress)

            // Create a random number 30-70 to delay between clicks
            var delayTime = Math.floor(Math.random() * 40 + 30)
            delay(delayTime.toLong())
            val mouseRelease =
                    MouseEvent(
                            component,
                            MouseEvent.MOUSE_RELEASED,
                            System.currentTimeMillis(),
                            clickMask,
                            destPoint.x,
                            destPoint.y,
//                                if(Random.nextLong(1000, 20000) < 2000){
//                                    1
//                                }
//                                else 0,
                            0,
                            clickType == ClickType.Right
                    )
            ioMouse.sendEvent(mouseRelease)
            delayTime = Math.floor(Math.random() * 50 + 100)
            delay(delayTime.toLong())
            hasclicked = true
        }

//        var retries = 0
//        while(!npc.isMouseOverObj() && !hasclicked){
//            println("Mouse not over object")
//            println("retries = " + retries)
//            val npcpoint = npc.getInteractPoint()
//            mouseMotionFactory.move(npcpoint.x, npcpoint.y)
//            if (click && npc.isMouseOverObj() && !hasclicked) {
//                val clickMask = if (clickType == ClickType.Right) MouseEvent.BUTTON3_MASK else MouseEvent.BUTTON1_MASK
//                val mousePress =
//                        MouseEvent(
//                                component,
//                                MouseEvent.MOUSE_PRESSED,
//                                System.currentTimeMillis(),
//                                clickMask,
//                                destPoint.x,
//                                destPoint.y,
////                                if(Random.nextLong(1000, 20000) < 2000){
////                                    1
////                                }
////                                else 0,
//                                0,
//                                clickType == ClickType.Right
//                        )
//
//                ioMouse.sendEvent(mousePress)
//
//                // Create a random number 30-70 to delay between clicks
//                var delayTime = Math.floor(Math.random() * 40 + 30)
//                delay(delayTime.toLong())
//                val mouseRelease =
//                        MouseEvent(
//                                component,
//                                MouseEvent.MOUSE_RELEASED,
//                                System.currentTimeMillis(),
//                                clickMask,
//                                destPoint.x,
//                                destPoint.y,
////                                if(Random.nextLong(1000, 20000) < 2000){
////                                    1
////                                }
////                                else 0,
//                                0,
//                                clickType == ClickType.Right
//                        )
//                ioMouse.sendEvent(mouseRelease)
////            delayTime = Math.floor(Math.random() * 50 + 100)
////            delay(delayTime.toLong())
//                hasclicked = true
//            }
//            retries = retries + 1
//            if(npc.isMouseOverObj() || retries > 4 || hasclicked){
//                break
//            }
//        }
        return true
    }

}