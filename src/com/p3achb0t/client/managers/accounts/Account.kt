package com.p3achb0t.client.managers.accounts

class Account() {

    var id: String = ""
    var username: String = ""
    var password: String = ""
    var pin: String = ""

    override fun toString(): String {
        return "$id [$username, $password, $pin]"
    }
}