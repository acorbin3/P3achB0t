package com.p3achb0t.api.wrappers.walking

import com.p3achb0t.Main
import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.Stats
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import java.io.IOException
import java.io.UncheckedIOException

object TransportLoader {
    const val COINS = 995
    val GLARIALS_PEBBLE: Int = 294
    suspend fun buildTransports(ctx: Context): ArrayList<Transport?> {
        val transports = ArrayList<Transport?>()

        try {
            for (line in String(
                Main.javaClass.getResourceAsStream("/transports.txt").readAllBytes()
            ).split("\n").toTypedArray()) {
                val updateline = line.trim { it <= ' ' }
                if (updateline.startsWith("#") || updateline.isEmpty()) {
                    continue
                }
                transports.add(parseTransportLine(line))
            }
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }

        // Edgeville

        // Edgeville
        if (ctx.stats.currentLevel(Stats.Skill.AGILITY) >= 21) {
            transports.add(
                objectTransport(
                    Tile(3142, 3513, 0),
                    Tile(3137, 3516, 0),
                    16530,
                    "Climb-into"
                )
            )
            transports.add(
                objectTransport(
                    Tile(3137, 3516, 0),
                    Tile(3142, 3513, 0),
                    16529,
                    "Climb-into"
                )
            )
        }

        // Glarial's Tomb

        // Glarial's Tomb
        transports.add(itemObjectTransport(Tile(2557, 3444, 0), Tile(2555, 9844, 0), 294, 1992))
        transports.add(itemObjectTransport(Tile(2557, 3445, 0), Tile(2555, 9844, 0), 294, 1992))
        transports.add(itemObjectTransport(Tile(2558, 3443, 0), Tile(2555, 9844, 0), 294, 1992))
        transports.add(itemObjectTransport(Tile(2559, 3443, 0), Tile(2555, 9844, 0), 294, 1992))
        transports.add(itemObjectTransport(Tile(2560, 3444, 0), Tile(2555, 9844, 0), 294, 1992))
        transports.add(itemObjectTransport(Tile(2560, 3445, 0), Tile(2555, 9844, 0), 294, 1992))
        transports.add(itemObjectTransport(Tile(2558, 3446, 0), Tile(2555, 9844, 0), 294, 1992))
        transports.add(itemObjectTransport(Tile(2559, 3446, 0), Tile(2555, 9844, 0), 294, 1992))

        // Waterfall Island

        // Waterfall Island
        transports.add(itemObjectTransport(Tile(2512, 3476, 0), Tile(2513, 3468, 0), 954, 1996))
        transports.add(itemObjectTransport(Tile(2512, 3466, 0), Tile(2511, 3463, 0), 954, 2020))

        // Crabclaw isle

        // Crabclaw isle
        if (ctx.inventory.getCount(COINS, useStack = true) >= 10000) {
            transports.add(
                npcTransport(
                    Tile(1782, 3458, 0),
                    Tile(1778, 3417, 0),
                    7483,
                    "Travel"
                )
            )
        }

        transports.add(npcTransport(Tile(1779, 3418, 0), Tile(1784, 3458, 0), 7484, "Travel"))

        // Port Sarim

        // Port Sarim
        if (ctx.vars.getVarbit(4897) === 0) {
            if (ctx.vars.getVarbit(8063) >= 7) { // todo: lower?
                transports.add(
                    npcChatTransport(
                        Tile(3054, 3245, 0),
                        Tile(1824, 3691, 0),
                        8484,
                        "Can you take me to Great Kourend?"
                    )
                )
            } else {
                transports.add(
                    npcChatTransport(
                        Tile(3054, 3245, 0),
                        Tile(1824, 3691, 0),
                        8484,
                        "That's great, can you take me there please?"
                    )
                )
            }
        } else {
            transports.add(
                npcTransport(
                    Tile(3054, 3245, 0),
                    Tile(1824, 3695, 1),
                    8630,
                    "Port Piscarilius"
                )
            )
        }

        //entrana

        //entrana
        transports.add(
            npcActionTransport(
                Tile(3041, 3237, 0),
                Tile(2834, 3331, 1),
                1166,
                "Take-boat"
            )
        )
        transports.add(
            npcActionTransport(
                Tile(2834, 3335, 0),
                Tile(3048, 3231, 1),
                1170,
                "Take-boat"
            )
        )
        transports.add(
            npcChatTransport(
                Tile(2821, 3374, 0),
                Tile(2822, 9774, 0),
                1164,
                "Well that is a risk I will have to take."
            )
        )


        // Paterdomus


        // Paterdomus
        transports.add(trapdoorTransport(Tile(3405, 3506, 0), Tile(3405, 9906, 0), 1579, 1581))
        transports.add(trapdoorTransport(Tile(3423, 3485, 0), Tile(3440, 9887, 0), 3432, 3433))
        transports.add(trapdoorTransport(Tile(3422, 3484, 0), Tile(3440, 9887, 0), 3432, 3433))

        //in aid of myreque

        //in aid of myreque
        transports.add(
            trapdoorTransport(
                Tile(3491, 3232, 0),
                Tile(14723, 2000, 0),
                12743,
                12743
            )
        )

        // Port Piscarilius

        // Port Piscarilius
        transports.add(
            npcTransport(
                Tile(1824, 3691, 0),
                Tile(3055, 3242, 1),
                10727,
                "Port Sarim"
            )
        )

        // Meyerditch

        // Meyerditch
        transports.add(
            Transport(
                Tile(3638, 3251, 0),
                Tile(3626, 9618, 0),
                0, 0,
                handler = { ctx: Context ->
                    if (ctx.vars.getVarbit(2590) < 1) {
                        ctx.gameObjects.find(17562).first().interact("Press")
                        Utils.sleepUntil({ ctx.vars.getVarbit(2590) == 1 })
                    }
                    if (ctx.vars.getVarbit(2590) < 2) {
                        ctx.gameObjects.find(18120).first().interact("Open")
                        Utils.sleepUntil({ ctx.vars.getVarbit(2590) == 2 })
                    }

                    ctx.gameObjects.find(18120).first().interact("Climb-down")
                    if (ctx.vars.getVarbit(6396) >= 90) {
                        ctx.dialog.chat("Yes.")

                    }
                    true
                })
        )

        transports.add(
            trapdoorTransport(
                Tile(3606, 3215, 0),
                Tile(3603, 9611, 0),
                32577,
                32578
            )
        )

        if (ctx.vars.getVarbit(2573) > 123) {
            transports.add(parseTransportLine("3649 3220 0 3631 3219 0 Enter Door 32660"))
            transports.add(parseTransportLine("3631 3219 0 3649 3219 0 Enter Door 32659"))
            transports.add(parseTransportLine("3649 3219 0 3631 3219 0 Enter Door 32660"))
            transports.add(parseTransportLine("3631 3220 0 3649 3219 0 Enter Door 32659"))
            transports.add(parseTransportLine("3649 3218 0 3631 3219 0 Enter Door 32660"))
            transports.add(parseTransportLine("3631 3218 0 3649 3219 0 Enter Door 32659"))
        }

        /*
         * TODO: if the target is in the wilderness, add these
         * 2561 3311 0 3154 3924 0 Pull Lever 1814
         * 3153 3923 0 2562 3311 0 Pull Lever 1815
         * 3067 10253 0 2271 4680 0 Pull Lever 1816
         * 2271 4680 0 3067 10254 0 Pull Lever 1817
         * 2539 4712 0 3090 3956 0 Pull Lever 5960
         * 3090 3956 0 2539 4712 0 Pull Lever 5959
         * 3090 3475 0 3154 3924 0 Pull Lever 26761
         *
         * wilderness ditch
         */

        // Tree Gnome Village

        /*
         * TODO: if the target is in the wilderness, add these
         * 2561 3311 0 3154 3924 0 Pull Lever 1814
         * 3153 3923 0 2562 3311 0 Pull Lever 1815
         * 3067 10253 0 2271 4680 0 Pull Lever 1816
         * 2271 4680 0 3067 10254 0 Pull Lever 1817
         * 2539 4712 0 3090 3956 0 Pull Lever 5960
         * 3090 3956 0 2539 4712 0 Pull Lever 5959
         * 3090 3475 0 3154 3924 0 Pull Lever 26761
         *
         * wilderness ditch
         */

        // Tree Gnome Village
        if (ctx.vars.getVarp(111) > 0) {
            transports.add(
                npcTransport(
                    Tile(2504, 3192, 0),
                    Tile(2515, 3159, 0),
                    4968,
                    "Follow"
                )
            )
            transports.add(
                npcTransport(
                    Tile(2515, 3159, 0),
                    Tile(2504, 3192, 0),
                    4968,
                    "Follow"
                )
            )
        }

        // Mort Myre Swamp

        // Mort Myre Swamp
        transports.add(objectWarningTransport(Tile(3444, 3458, 0), Tile(3444, 3457, 0), 3506, "Open", 580, 17))
        transports.add(objectWarningTransport(Tile(3443, 3458, 0), Tile(3443, 3457, 0), 3507, "Open", 580, 17))

        return transports
    }

    private fun parseTransportLine(line: String): Transport? {
        val parts = line.split(" ").toTypedArray()
        return objectTransport(
            Tile(parts[0].toInt(), parts[1].toInt(), parts[2].toInt()),
            Tile(parts[3].toInt(), parts[4].toInt(), parts[5].toInt()), parts[parts.size - 1].toInt(),
            parts[6].replace('_', ' ')
        )
    }

    private fun objectWarningTransport(
        source: Tile,
        target: Tile,
        id: Int,
        action: String,
        interfaceId: Int,
        widgetId: Int
    ): Transport? {
        return Transport(
            source,
            target, Int.MAX_VALUE,
            0,
            handler = { ctx: Context ->
                ctx.gameObjects.find(id).first().interact(action)
                Utils.sleepUntil({ target.distanceTo() == 0 || ctx.widgets.find(interfaceId, widgetId) != null })
                val widget = ctx.widgets.find(interfaceId, widgetId)
                if (widget != null) {
                    WidgetItem(widget, ctx = ctx).interact("Yes")
                    Utils.sleepUntil({ ctx.widgets.find(interfaceId, widgetId) == null })
                }
                true
            })
    }
}

private fun trapdoorTransport(source: Tile, target: Tile, closedId: Int, openId: Int): Transport? {
    return Transport(source, target, Int.MAX_VALUE, 0, handler = { ctx: Context ->
        if (ctx.gameObjects.find(closedId, source).isNotEmpty()) {
            ctx.gameObjects.find(closedId, source).first().interact("Open")
            Utils.sleepUntil({ ctx.gameObjects.find(closedId, source).isEmpty() })

        }
        ctx.gameObjects.find(openId).first().interact("Climb-down")
    })
}

private suspend fun npcTransport(source: Tile, target: Tile, id: Int, action: String): Transport {
    return Transport(source, target, 1, 15, handler = { ctx: Context ->
        ctx.npcs.findNpc(id).first().interact(action)
    })
}

private fun npcChatTransport(source: Tile, target: Tile, id: Int, vararg chats: String): Transport? {
    return Transport(source, target, 10, 0, handler = { ctx: Context ->
        ctx.npcs.findNpc(id).first().interact("Talk-to")
        ctx.dialog.chat(*chats)
        true
    })
}

private fun npcActionTransport(source: Tile, target: Tile, id: Int, action: String): Transport? {
    return Transport(source, target, 10, 0, handler = { ctx: Context -> ctx.npcs.findNpc(id).first().interact(action) })
}

private fun objectTransport(source: Tile, target: Tile, id: Int, action: String): Transport {
    return Transport(source, target, 1, Int.MAX_VALUE, handler = { ctx: Context ->
        ctx.gameObjects.find(id).first().interact(action)
    })
}

private fun objectChatTransport(
    source: Tile,
    target: Tile,
    id: String,
    action: String,
    vararg options: String
): Transport? {
    return Transport(source, target, Int.MAX_VALUE, 0, handler = { ctx: Context ->
        ctx.gameObjects.find(id).first().interact(action)
        ctx.dialog.chat(*options)
        true
    })
}

private fun itemObjectTransport(source: Tile, target: Tile, itemID: Int, gameObjectID: Int): Transport {
    return Transport(source, target, 1, Int.MAX_VALUE, handler = { ctx: Context ->
        ctx.inventory.getItem(itemID)?.click()
        ctx.gameObjects.find(gameObjectID).first().click()
    })
}
