package com.p3achb0t.api.wrappers

import com.p3achb0t.api.wrappers.widgets.Widgets
import com.p3achb0t.ui.Context

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

        if (modeInit) return modeType
        return try {
            val resizeModeWidget = Widgets.find(ctx, 261, 34)
            // For tutorial island we might not have any children yet, then it means we are in resize mode
            if (resizeModeWidget?.getChildren() == null) {
                modeInit = true
                modeType = Companion.ModeType.ResizeMode
                return modeType
            }
            if (resizeModeWidget.getChildren()[0].getSpriteId2() == 1150) {
                modeInit = true
                modeType = Companion.ModeType.ResizeMode
                modeType
            } else {
                modeInit = true
                modeType = Companion.ModeType.FixedMode
                modeType
            }
        } catch (e: Exception) {
            modeType
        }
    }
}