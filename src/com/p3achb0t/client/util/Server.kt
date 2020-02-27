package com.p3achb0t.client.util

//import com.p3achb0t.scripts_private.Mule.Main
import org.zeromq.ZMQ

class Server{
fun main(mule: String) {
    val context = ZMQ.context(1)

    val socket = context.socket(ZMQ.REP)
    println("Starting the server...")

    socket.bind("tcp://*:5897")

//    while (true) {
//        if(Utils.getElapsedSeconds(Main.mulewatch.time) == 0) {
//            val rawRequest = socket.recv(0)
//
//            val cleanRequest = String(rawRequest, 0, rawRequest.size)
//            println("Message recieved: $cleanRequest")
//            if (cleanRequest.contains("Mule")) {
//                val parts = cleanRequest.split(":").toTypedArray()
//
//                Main.accountName = parts[2]
//                Main.world = parts[1].toInt()
//                Main.mule = true
//            }
//
//            var plainReply = mule
//
//            var rawReply = plainReply.toByteArray()
//
//            rawReply[rawReply.size - 1] = 0
//
//            socket.send(rawReply, 0)
//            Thread.sleep(500)
//        }
//    }
}
}