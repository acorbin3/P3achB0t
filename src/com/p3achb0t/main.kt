package com.p3achb0t

import com.formdev.flatlaf.FlatDarkLaf
import com.p3achb0t.api.wrappers.Stats
import com.p3achb0t.client.managers.tracker.FBDataBase
import com.p3achb0t.client.ui.GameWindow
import com.p3achb0t.client.ui.setup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.awt.Font
import javax.swing.UIManager
import javax.swing.UIManager.setLookAndFeel
import javax.swing.plaf.FontUIResource


object Main {
    @JvmStatic
    fun main(args: Array<String>) {

        val db = FBDataBase()
        db.updateStat("1112","xx", Stats.Skill.ATTACK,123)
        db.updateStat("1112","xx", Stats.Skill.ATTACK,222)
        db.updateStat("1112","xx", Stats.Skill.HERBLORE,123)
        db.updateStat("1113","tt", Stats.Skill.HERBLORE,123)
        db.updateStat("1113","tt", Stats.Skill.AGILITY,123)
        println("Done")

        setup()
        setLookAndFeel(FlatDarkLaf())
        for ((key) in UIManager.getDefaults()) {
            val value = UIManager.get(key)
            if (value != null && value is FontUIResource) {
                val fr = value
                val f = FontUIResource(Font("Courier Bold", Font.BOLD, 12))
                UIManager.put(key, f)
            }
        }
        val g = GameWindow()
        CoroutineScope(Dispatchers.Default).launch { g.run() }
    }
}
