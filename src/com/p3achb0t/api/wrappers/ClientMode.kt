package com.p3achb0t.api.wrappers

import com.p3achb0t.api.wrappers.widgets.Widgets

class ClientMode {
    companion object {
        enum class ModeType {
            ResizeMode,
            FixedMode,
        }

        fun getMode(): ModeType {

            try {
                val resizeModeWidget = Widgets.find(261, 34)
                if (resizeModeWidget?.getChildren()?.get(0)?.getTextureId() == 1150)
                    return Companion.ModeType.ResizeMode
                else
                    return Companion.ModeType.FixedMode
            } catch (e: Exception) {
                return Companion.ModeType.ResizeMode
            }

            return Companion.ModeType.ResizeMode
        }
    }
}