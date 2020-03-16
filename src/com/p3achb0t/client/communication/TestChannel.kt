package com.p3achb0t.client.communication

import java.lang.Thread.sleep
import kotlin.concurrent.thread

class TestChannel(val id: String) : ChannelInterface {

    private var message: String = ""
    lateinit var  room1: Room

    override fun setChannel(id: String, room: Room) {
        this.room1 = room
    }

    fun getMessage() : String {
        return "$id --> $message"
    }


    override fun receive(id: String, message: String) {
        println("$id $message")
        this.message = message
    }

    override fun send(message: String) {
        room1.notifySubscribers(message)
    }
}


fun main() {

    val com = Communication()

    //com.addChannel("12345")



    thread (start = true){
        var i = 1000;

        val t1 = TestChannel("1")
        println(t1)
        com.subscribeChannel("12345", t1)
        //sleep(0)

        while (true) {
            sleep(200)
            t1.send("id 1 ${i++}")

        }
    }

    thread (start = true){
        var i = 0;
        val t2 = TestChannel("2")
        println(t2)
        com.subscribeChannel("12345", t2)
        sleep(200)
        t2.send("HELLO")
        while (true) {
            sleep(200)
            t2.send("${i++}")
        }
    }



    sleep(2000)
}