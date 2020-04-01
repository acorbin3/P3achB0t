package com.p3achb0t

import com.p3achb0t.client.ux.BotManager
import com.p3achb0t.client.ux.setup


object Main {

    //var validationKey = PaintDebug.key
    @JvmStatic
    fun main(args: Array<String>) {

        var getNextKey = false
        args.iterator().forEach {
            if(getNextKey){
                //validationKey = PaintDebug.key
            }
            if(it == "-key"){
                getNextKey = true
            }
            println(it)
        }
        //println("validation key: $validationKey")

        setup()
        BotManager()

    }
}
