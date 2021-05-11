package com.p3achb0t.api.wrappers.walking

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.widgets.WidgetItem

class TeleportLoader(private val ctx: Context) {
    val RING_OF_DUELING = intArrayOf(2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566)
    val GAMES_NECKLACE = intArrayOf(3863, 3855, 3857, 3859, 3861, 3863, 3865, 3867)
    val COMBAT_BRACELET = intArrayOf(11972, 11974, 11118, 11120, 11122, 11124)
    val RING_OF_WEALTH = intArrayOf(11980, 11982, 11984, 11986, 11988)
    val AMULET_OF_GLORY = intArrayOf(1706, 1708, 1710, 1712, 11976, 11978)
    val NECKLACE_OF_PASSAGE = intArrayOf(21146, 21149, 21151, 21153, 21155)
    val BURNING_AMULET = intArrayOf(21166, 21171, 21173, 21175, 21167)
    val XERICS_TALISMAN = 13393
    val SLAYER_RING = intArrayOf(11866, 11867, 11868, 11869, 11870, 11871, 11872, 11873, 21268)
    val DIGSITE_PENDANT = intArrayOf(11190, 11191, 11192, 11193, 11194)
    val DRAKANS_MEDALLION = 22400
    val SKILLS_NECKLACE = intArrayOf(11111, 11109, 11107, 11105, 11970, 11968)

    fun TeleportLoader() {
    }

    fun buildTeleports(): List<Teleport>? {
        val teleports = ArrayList<Teleport>()
        if (ringOfDueling() != null) {
            teleports.add(Teleport(Tile(3315, 3235, 0,ctx=ctx), 2 ) { jewleryAction(ringOfDueling(), "Duel Arena") })
            teleports.add(Teleport(Tile(2440, 3090, 0,ctx=ctx), 2 ) { jewleryAction(ringOfDueling(), "Castle Wars") })
            teleports.add(Teleport(Tile(3151, 3635, 0,ctx=ctx), 2 ) { jewleryAction(ringOfDueling(), "Ferox Enclave") })
        }
        if (gamesNecklace() != null) {
            teleports.add(Teleport(Tile(2898, 3553, 0,ctx=ctx), 2 ) { jewleryAction(gamesNecklace(), "Burtrope") })
            teleports.add(Teleport(Tile(2520, 3571, 0,ctx=ctx), 2 ) { jewleryAction(gamesNecklace(), "Barbarian Outpost") })
            teleports.add(Teleport(Tile(2964, 4382, 2,ctx=ctx), 2 ) { jewleryAction(gamesNecklace(), "Corporeal Beast") })
            teleports.add(Teleport(Tile(3244, 9501, 2,ctx=ctx), 2 ) { jewleryAction(gamesNecklace(), "Tears of Guthix") })
            teleports.add(Teleport(Tile(1624, 3938, 0,ctx=ctx), 2 ) { jewleryAction(gamesNecklace(), "Barbarian Outpost") })
            teleports.add(Teleport(Tile(2520, 3571, 0,ctx=ctx), 2 ) { jewleryAction(gamesNecklace(), "Barbarian Outpost") })
        }
        if (combatBracelet() != null) {
            teleports.add(Teleport(Tile(2882, 3548, 0,ctx=ctx), 2 ) { jewleryAction(combatBracelet(), "Warriors' Guild") })
            teleports.add(Teleport(Tile(3191, 3367, 0,ctx=ctx), 2 ) { jewleryAction(combatBracelet(), "Champions' Guild") })
            teleports.add(Teleport(Tile(3052, 3488, 0,ctx=ctx), 2 ) { jewleryAction(combatBracelet(), "Monastery") })
            teleports.add(Teleport(Tile(2655, 3441, 0,ctx=ctx), 2 ) { jewleryAction(combatBracelet(), "Ranging Guild") })
        }
        if (skillsNecklace() != null) {
            teleports.add(Teleport(Tile(2611, 3390, 0,ctx=ctx), 2 ) { jewleryAction(skillsNecklace(), "Fishing Guild") })
            teleports.add(Teleport(Tile(3050, 9763, 0,ctx=ctx), 2 ) { jewleryAction(skillsNecklace(), "Mining Guild") })
            teleports.add(Teleport(Tile(2933, 3295, 0,ctx=ctx), 2 ) { jewleryAction(skillsNecklace(), "Crafting Guild") })
            teleports.add(Teleport(Tile(3143, 3440, 0,ctx=ctx), 2 ) { jewleryAction(skillsNecklace(), "Cooking Guild") })
            teleports.add(Teleport(Tile(1662, 3505, 0,ctx=ctx), 2 ) { jewleryAction(skillsNecklace(), "Woodcutting Guild") })
            teleports.add(Teleport(Tile(1249, 3718, 0,ctx=ctx), 2 ) { jewleryAction(skillsNecklace(), "Farming Guild") })
        }
        if (ringOfWealth() != null) {
            teleports.add(Teleport(Tile(3163, 3478, 0,ctx=ctx), 2 ) { jewleryAction(ringOfWealth(), "Grand Exchange") })
            teleports.add(Teleport(Tile(2996, 3375, 0,ctx=ctx), 2 ) { jewleryAction(ringOfWealth(), "Falador") })
            //            teleports.add(new Teleport(new Tile, 2, () -> jewleryAction(ringOfWealth(), "Miscellania")));
            teleports.add(Teleport(Tile(2829, 10167, 0,ctx=ctx), 2 ) { jewleryAction(ringOfWealth(), "Dondakan") })
        }
        if (amuletOfGlory() != null) {
            teleports.add(Teleport(Tile(3087, 3496, 0,ctx=ctx), 0 ) { jewleryAction(amuletOfGlory(), "Edgeville") })
            teleports.add(Teleport(Tile(2918, 3176, 0,ctx=ctx), 0 ) { jewleryAction(amuletOfGlory(), "Karamja") })
            teleports.add(Teleport(Tile(3105, 3251, 0,ctx=ctx), 0 ) { jewleryAction(amuletOfGlory(), "Draynor Village") })
            teleports.add(Teleport(Tile(3293, 3163, 0,ctx=ctx), 0 ) { jewleryAction(amuletOfGlory(), "Al Kharid") })
        }
        if (necklaceOfPassage() != null) {
            teleports.add(Teleport(Tile(3114, 3179, 0,ctx=ctx), 2 ) { jewleryAction(necklaceOfPassage(), "Wizards' Tower") })
            teleports.add(Teleport(Tile(2430, 3348, 0,ctx=ctx), 2 ) { jewleryAction(necklaceOfPassage(), "The Outpost") })
            teleports.add(Teleport(Tile(3405, 3157, 0,ctx=ctx), 2 ) { jewleryAction(necklaceOfPassage(), "Eagles' Eyrie") })
        }
        if (burningAmulet() != null) {
            teleports.add(Teleport(Tile(3235, 3636, 0,ctx=ctx), 2 ) { jewleryAction(burningAmulet(), "Chaos Temple") })
            teleports.add(Teleport(Tile(3038, 3651, 0,ctx=ctx), 2 ) { jewleryAction(burningAmulet(), "Bandit Camp") })
            teleports.add(Teleport(Tile(3028, 3842, 0,ctx=ctx), 2 ) { jewleryAction(burningAmulet(), "Lava Maze") })
        }
        if (slayerRing() != null) {
            teleports.add(Teleport(Tile(2432, 3423, 0,ctx=ctx), 2 ) {
                jewleryAction(
                    slayerRing(),
                    "Stronghold Slayer Cave"
                )
            })
            teleports.add(Teleport(Tile(3422, 3537, 0,ctx=ctx), 2 ) { jewleryAction(slayerRing(), "Slayer Tower") })
            teleports.add(Teleport(Tile(2802, 10000, 0,ctx=ctx), 2 ) {
                jewleryAction(
                    slayerRing(),
                    "Fremennik Slayer Dungeon"
                )
            })
            teleports.add(Teleport(Tile(3185, 4601, 0,ctx=ctx), 2 ) { jewleryAction(slayerRing(), "Tarn's Lair") })
            teleports.add(Teleport(Tile(2028, 4636, 0,ctx=ctx), 2 ) { jewleryAction(slayerRing(), "Dark Beasts") })
        }
        if (digsitePendant() != null) {
            teleports.add(Teleport(Tile(3341, 3445, 0,ctx=ctx), 2 ) { jewleryAction(digsitePendant(), "Digsite") })
            //            teleports.add(new Teleport(new Tile, 2, () -> jewleryAction(digsitePendant(), "Fossil Island")));
            teleports.add(Teleport(Tile(3549, 10456, 0,ctx=ctx), 2 ) { jewleryAction(digsitePendant(), "Lithkren") })
        }

//        if (drakansMedallion() != null) {
//            teleports.add(new Teleport(new Tile(3649, 3230, 0,ctx=ctx), 0 , () -> jewleryAction(drakansMedallion(), "Ver Sinhaza")));
//            teleports.add(new Teleport(new Tile(3592, 3337, 0,ctx=ctx), 0 , () -> jewleryAction(drakansMedallion(), "Darkmeyer")));
//        }
        return teleports
    }

    private suspend fun jewleryAction(item: WidgetItem?, target: String): Boolean { // TODO

        item?.interact("Rub")
        ctx.dialog.selectionOption(target)
        return true
    }

    private fun ringOfDueling(): WidgetItem? {
        return ctx.inventory.getAll().firstOrNull { it.id in RING_OF_DUELING }
    }

    private fun gamesNecklace(): WidgetItem? {
        return ctx.inventory.getAll().firstOrNull { it.id in GAMES_NECKLACE}
    }

    private fun combatBracelet(): WidgetItem? {
        return ctx.inventory.getAll().firstOrNull { it.id in COMBAT_BRACELET}
    }

    private fun skillsNecklace(): WidgetItem? {
        return ctx.inventory.getAll().firstOrNull { it.id in SKILLS_NECKLACE}
    }

    private fun ringOfWealth(): WidgetItem? {
        return ctx.inventory.getAll().firstOrNull { it.id in RING_OF_WEALTH}
    }

    private fun amuletOfGlory(): WidgetItem? {
        return ctx.inventory.getAll().firstOrNull { it.id in AMULET_OF_GLORY}
    }

    private fun necklaceOfPassage(): WidgetItem? {
        return ctx.inventory.getAll().firstOrNull { it.id in NECKLACE_OF_PASSAGE}
    }

    private fun burningAmulet(): WidgetItem? {
        return ctx.inventory.getAll().firstOrNull { it.id in BURNING_AMULET}
    }

    private fun xericsTalisman(): WidgetItem? {
        return ctx.inventory.getAll().firstOrNull { it.id == XERICS_TALISMAN}
    }

    private fun slayerRing(): WidgetItem? {
        return ctx.inventory.getAll().firstOrNull { it.id in SLAYER_RING}
    }

    private fun digsitePendant(): WidgetItem? {
        return ctx.inventory.getAll().firstOrNull { it.id in DIGSITE_PENDANT}
    }

    private fun drakansMedallion(): WidgetItem? {
        return ctx.inventory.getAll().firstOrNull { it.id == DRAKANS_MEDALLION}
    }
}