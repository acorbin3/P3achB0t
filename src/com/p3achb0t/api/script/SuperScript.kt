package com.p3achb0t.api.script

import com.p3achb0t.api.Context
import com.p3achb0t.api.utils.Logging
import com.p3achb0t.client.accounts.Account
import java.awt.Graphics

/*
    This class is the base set of functionality a script should have
*/
abstract class SuperScript: Drawable,Logging() {
    lateinit var ctx: Context
    var account: Account = Account()
    open fun start() {}
    open fun stop() {}
    override fun draw(g: Graphics) {}
}