package com.p3achb0t.client.managers.accounts

class Account {

    var id: String = ""
    var username: String = ""
    var password: String = ""
    var pin: String = ""
    var script: String = ""
    var minRuntime: Int = 0 // Units: Seconds
    var maxRuntime: Int = 0 // Units: Seconds
    var userBreaks: Boolean = false
    var minBreakTime: Int = 0 // Units: Seconds
    var maxBreakTime: Int = 0 // Units: Seconds
    var banned: Boolean = false
    var proxy: String = "none"  // SOCKS5;185.244.192.119:7670 or none
    var world: Int = 80
    var startAutomatically: Boolean = true

    override fun toString(): String {
        return "$id [$username, pass:$password, pin:$pin, script:$script, minRuntime:$minRuntime, maxRuntime:$maxRuntime," +
                "useBreaks:$userBreaks, minBreakTime:$minBreakTime,maxBreakTime:$maxBreakTime, banned:$banned, " +
                "proxy:$proxy, world:$world]"
    }
}