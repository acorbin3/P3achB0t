package com.p3achb0t.client.accounts

class Account {

    var id: String = ""
    //var username: String = ""
    //var password: String = ""
    //var pin: String = ""
    var script: String = ""
    var minRuntimeSec: Int = 0 // Units: Seconds
    var maxRuntimeSec: Int = 0 // Units: Seconds
    var userBreaks: Boolean = false
    var minBreakTimeSec: Int = 0 // Units: Seconds
    var maxBreakTimeSec: Int = 0 // Units: Seconds
    //var banned: Boolean = false
    //var proxy: String = "none"  // SOCKS5;185.244.192.119:7670 or none
    var world: Int = 80
    var startAutomatically: Boolean = true
    var fps: Int = 15
    var args: String = ""
    var key: String = ""




    override fun toString(): String {
        return "$id [$username, pass:$password, pin:$pin, script:$script, minRuntime:$minRuntimeSec, maxRuntime:$maxRuntimeSec," +
                "useBreaks:$userBreaks, minBreakTime:$minBreakTimeSec,maxBreakTime:$maxBreakTimeSec, banned:$banned, " +
                "proxy:$proxy, world:$world], fps:$fps, args:$args, , key:$key]"
    }

    // Identifier
    var uuid: String = ""

    // UNIX time
    var totalRunningTime: Int = 0
    var sessionRunningTime: Int = 0

    var minimumSessionTime: Int = 0
    var maximumSessionTime: Int = 0
    var minimumBreakTime: Int = 0
    var maximumBreakTime: Int = 0
    var minimumBreakOverSession: Int = 0
    var maximumBreakOverSession: Int = 0
    var lastBreakTime: Int = 0

    // Scripts
    var actionScripts: String = ""
    var debugScripts = arrayListOf<String>()
    var serviceScripts = arrayListOf<String>()

    // Networking
    var proxy: String = "none" // SOCKS5;185.244.192.119:7670 or none

    // Game config
    var username: String = ""
    var password: String = ""
    var pin: String = ""
    var banned: Boolean = false
    var gameWorld: Int = 80

    // Utilities
    var gameFps: Int = 15
    var startActionScriptAutomatically: Boolean = false
    var isOnBreak: Boolean = false







}