package com.p3achb0t.client.communication.server

import com.p3achb0t.client.configs.GlobalStructs
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.io.ByteArrayOutputStream
import java.lang.Thread.sleep
import java.net.InetSocketAddress
import java.util.*


class Server : WebSocketServer {

    constructor(port: Int) : super (InetSocketAddress(port))

    constructor(address: InetSocketAddress?) : super(address)

    override fun onOpen(p0: WebSocket?, p1: ClientHandshake?) {
        println("onOpen")
    }

    override fun onClose(p0: WebSocket?, p1: Int, p2: String?, p3: Boolean) {
        println("onClose")
    }

    override fun onMessage(socket: WebSocket?, message: String?) {
        println("onMessage")
        for (i in 0..2) {
            for (s in GlobalStructs.botManager.botTabBar.botInstances) {

                val baos = ByteArrayOutputStream()
                //ImageIO.write(s.value.instanceManagerInterface!!.getManager().takeScreenShot(), "png", baos)
                baos.flush()
                val imageInByte = baos.toByteArray()

                broadcast(Base64.getEncoder().encodeToString(imageInByte))

                println(Base64.getEncoder().encodeToString(imageInByte))
                baos.close()
                //broadcast(imageInByte)

            }
            sleep(1000)
        }
    }

    override fun onStart() {

    }

    override fun onError(p0: WebSocket?, p1: Exception?) {
        println("onError")
    }
}