package com.p3achb0t.client.util

//import com.p3achb0t.scripts.ZulrahMain


import org.zeromq.ZMQ

class Client{

fun main(message: String) {
    val context = ZMQ.context(1)

    val socket = context.socket(ZMQ.REQ)
    socket.connect("tcp://localhost:5897")

        var plainRequest = message

        var byteRequest = plainRequest.toByteArray()

        byteRequest[byteRequest.size - 1] = 0

        println("sending request:  $plainRequest")
        socket.send(byteRequest, 0)

        val byteReply = socket.recv(0)

        var plainReply = String(byteReply, 0, byteReply.size - 1)

//        ZulrahMain.mulename = plainReply
//        ZulrahMain.gotReply = true
        println("Received reply: $plainReply")


}
}