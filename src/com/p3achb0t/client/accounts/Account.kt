package com.p3achb0t.client.accounts

import java.util.*


class Account {


    // Identifier
    var uuid: String = UUID.randomUUID().toString()

    // UNIX time
    var sessionStartTime: Long = -1

    var useBreaks: Boolean = false


    //All the times will be in units of minutes. Any time of zero will be ignored for evaluating
    // A session is considered how long do you want the account to run before taking a break.
    // The session run time will reset with a random value between the min and max once a break has finished

    var minimumSessionTime: Int = 0
    var maximumSessionTime: Int = 0
    var minimumBreakTime: Int = 0
    var maximumBreakTime: Int = 0
    var minimumBreakOverSession: Int = 0
    var maximumBreakOverSession: Int = 0
    var lastBreakTime: Int = 0

    // Window time is a concept were the script will only run between 7pm to 5am or something like that
    // Reference would be that cambridge is GMT+1
    // Time will be in minutes and converted to when the script would start or stop.
    // Example 60 would equal 1h or 1AM where 875 would be 14:35 or 2:25PM
    var useWindowTime: Boolean = false
    var windowStartTime: Int = 1_080 // range [0,1440] in minutes. 1080 == 6pm or 18:00
    var windowEndHourTime: Int = 360 // range [0,1440] in minutes 360 = 6AM

    // Scripts
    var sessionToken: String = UUID.randomUUID().toString()
    var actionScript: String = ""
    var debugScripts = arrayListOf<String>()
    var serviceScripts = arrayListOf<String>()

    // Networking
    var proxy: String = "none" // SOCKS5;185.244.192.119:7670 or none

    // Game config
    var username: String = UUID.randomUUID().toString()
    var password: String = ""
    var pin: String = ""
    var banned: Boolean = false
    var gameWorld: Int = 80

    // Utilities
    var gameFps: Int = 30
    var startActionScriptAutomatically: Boolean = false
    var isOnBreak: Boolean = false
    var key: String = ""
    var args: String = ""

    fun getRandomDat(): String {
        return if (username == "") "random.dat" else "$username.dat"
    }
}