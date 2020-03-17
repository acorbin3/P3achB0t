
import com.p3achb0t.api.AbstractScript
import kotlinx.coroutines.delay
import java.awt.Color

import java.awt.Graphics
import kotlin.random.Random

class ComScript : AbstractScript() {

    var isSub = false
    var m = ""
    var r = Random.nextInt(1000)

    override suspend fun loop() {
        if (!isSub) {
            ctx.ipc.subscribe("42", ::callback)
            //ctx.communication.subscribe("1234")
            isSub = true
        }
        delay(200)
        //ctx.communication.send("1234", "id: $r, ComScript")
        ctx.ipc.send("42", "id: $r, ComScript")
        //println("send ComScript")
    }

    override suspend fun start() {
    }

    override fun stop() {

    }

    override fun draw(g: Graphics) {
        g.color = Color.CYAN
        g.drawString("Received: $m",50,50)
        g.drawString("Own id: $r", 50, 65)
    }


    private fun callback(id: String, message: String) {
        if (message.contains("$r")) {

        } else {
            m = message
            //println("received from [ room: $id, $message ]")
        }
    }
}