package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.user_inputs.DoActionParams
import kotlinx.coroutines.delay

class WorldHop(val ctx: Context) {
    val freeWorldNoTotalRequirement = intArrayOf(301,308,316,326,335,379,380,382,383,384,393,394,397,398,399,417,418,425,426,430,431,
            433,434,435,436,437,438,439,440,451,452,453,454,455,456,457,458,459,469,470,471,472,473,474,475,476,497,498,499,500,591,502,503,504)

    suspend fun switchWorlds(){
        //Open logout menu
        //argument0:-1, argument1:10747934, argument2:57, argument3:1, action:Logout, targetName:, mouseX:781, mouseY:10, argument8:0
        this.ctx.mouse.doAction(DoActionParams(-1,10747934,57,1,"Logout","",0,0))
        delay(1000)
        //Open world hopper
//        argument0:-1, argument1:11927555, argument2:57, argument3:1, action:World Switcher, targetName:, mouseX:670, mouseY:433, argument8:0
        this.ctx.mouse.doAction(DoActionParams(-1,11927555,57,1,"World Switcher","",0,0))
        delay(1000)
        //Pick world
        //argument0:326, argument1:4522000, argument2:57, argument3:1, action:Switch, targetName:<col=ff9040>326</col>, mouseX:620, mouseY:427, argument8:0
        this.ctx.mouse.doAction(DoActionParams(freeWorldNoTotalRequirement.random(),4522000,57,1,"Switch","",0,0))
        delay(1000)
        //click yes
        //argument0:1, argument1:14352385, argument2:30, argument3:0, action:Continue, targetName:, mouseX:261, mouseY:488, argument8:0
        this.ctx.mouse.doAction(DoActionParams(1,14352385,30,1,"Continue","",0,0))
    }
}