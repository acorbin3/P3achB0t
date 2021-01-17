package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context

class ClientMode(val ctx: Context) {
    companion object {
        enum class ModeType {
            ResizeMode,
            FixedMode,
        }

    }

    private var modeInit: Boolean = false
    var modeType: ModeType = ModeType.ResizeMode

    fun getMode(): ModeType {
        //ctx.client.getClientPreferences().getWindowMode()
        return if(!ctx.client.getIsResizable()){
            ModeType.FixedMode
        }else{
            ModeType.ResizeMode
        }
    }
}