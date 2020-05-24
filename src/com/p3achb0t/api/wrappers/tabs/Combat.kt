package com.p3achb0t.api.wrappers.tabs

import com.p3achb0t.api.Context

class Combat(val ctx: Context) {
    companion object{
        val styleVarp = 43
        enum class Style(val parentID: Int, val childID: Int,val varpStatusValue: Int){
            PUNCH(593,4, 0),
            KICK(593,8, 1),
            BLOCK_UNARMED(593,12, 3),

            STAB(593,4,0),
            LUNGE(593,8,1),
            SLASH(593,12,2),
            BLOCK_ARMED(593,16,3),

            ACCURATE(593,4,0),
            RAPID(593,8,1),
            LONGRANGE(593,12,3),

        }
    }

    fun isAutoRetaliateOn(): Boolean{
        return ctx.vars.getVarp(172) == 0
    }

    fun isStyleSelected(style: Style): Boolean{
        return ctx.vars.getVarp(styleVarp) == style.varpStatusValue
    }
}