package com.p3achb0t.api.wrappers

import com.p3achb0t.api.interfaces.NPCType

class NpcDefinition(val accessor: NPCType)  {


    val id: Int get() {
        return if(accessor.getTransformVarbit() == -1) {
            accessor.getId()
        }else{
            accessor.transform().getId()
        }
    }

    val name: String? get() {
        return if(accessor.getTransformVarbit() == -1) {
            accessor.getName()
        }else{
            accessor.transform().getName()
        }
    }

    val actions: Array<String> get() = accessor.getOp()

    val headIconPrayer: Int get() = accessor.getHeadIconPrayer()

    val transforms get() = accessor.getTransforms()



    private fun transform(): NpcDefinition? = accessor.transform()?.let { NpcDefinition(it) }

//        fun get(): NpcDefinition?{
//            NpcDefinition(accessor).let {
//                return if (it.transforms == null) it
//                else it.transform()
//            }
//        }
//        fun get(id: Int): NpcDefinition? {
//            return ctx.npcs.findNpc(id).first().npc?.let { node ->
//                NpcDefinition(node).let {
//                    return if (it.transforms == null) it
//                    else it.transform()
//                }
//            }
//        }
}