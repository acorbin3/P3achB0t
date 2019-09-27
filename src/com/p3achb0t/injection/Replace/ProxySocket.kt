package com.p3achb0t.injection.Replace

import java.net.Proxy

class ProxySocket1(settings: String) {
    // "ip:port;username:password" or "" or "ip:port" SOCKS5 SOCKS4 HTTP
    var ip: String = ""
    var port: Int = 0
    var username: String = ""
    var password: String = ""
    var type = ""

    init {
        if (settings != "") {
            val sp = settings.split(";")

            if (sp.size == 2) {
                type = sp[0]
                val ipPort = sp[1].split(":")
                ip = ipPort[0]
                port = ipPort[1].toInt()
            } else {
                type = sp[0]
                val ipPort = sp[1].split(":")
                ip = ipPort[0]
                port = ipPort[1].toInt()
                val credentials = sp[2].split(":")
                username = credentials[0]
                password = credentials[1]
            }
        }
    }
}
