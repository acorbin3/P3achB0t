
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
            ctx.ipc.subscribe(ctx.ipc.uuid, ::callback)
            isSub = true
        }
        delay(200)
        ctx.ipc.send(ctx.ipc.uuid, "id: ${ctx.ipc.scriptUUID}, ComScript")
        //println("send ComScript")
    }

    override fun start() {
    }

    override fun stop() {

    }

    override fun draw(g: Graphics) {
        g.color = Color.CYAN
        g.drawString("Channel id ${ctx.ipc.uuid}", 50,50)
        g.drawString("Script uuid ${ctx.ipc.scriptUUID}", 50,70)

    }


    private fun callback(id: String, message: String) {
        if (message.contains("$r")) {

        } else {
            m = message
            //println("received from [ room: $id, $message ]")
        }
    }
}