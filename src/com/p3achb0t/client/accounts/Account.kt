package com.p3achb0t.client.accounts


class Account {


    // Identifier
    var uuid: String = ""

    // UNIX time
    var sessionStartTime: Long = -1

    var useBreaks: Boolean = false
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
    var sessionToken: String = ""
    var actionScript: String = ""
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
    var key: String = ""
    var args: String = ""







}