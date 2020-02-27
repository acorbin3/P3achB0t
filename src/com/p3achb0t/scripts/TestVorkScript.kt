package com.p3achb0t.scripts

import com.p3achb0t.UserDetails
import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.LoggingIntoAccount
import com.p3achb0t.api.ScriptManifest
import com.p3achb0t.api.wrappers.widgets.WidgetID
//import com.p3achb0t.scripts_private.Zulrah.RequiredItem
import kotlinx.coroutines.delay
import java.awt.Color
import java.awt.Graphics

@ScriptManifest("testVork","testVork","zak")
class TestVorkScript: AbstractScript()  {
    var itemlistBank = arrayListOf(1540, 1387, 2349, 2357, 555, 1759, 547)
    var items = arrayListOf<Int>()
    var Withdrawn = false
    var allWithdrawn = false
    var Sold = false
    var Deposititems = false
    var resupply = false
    var gotgold = false
    var gotweaponsupplies = false
    var suppliedWeapon = false
    var gotallsupplies = false
    var equipped = true
    var wealthIds = hashSetOf(11980, 11982, 11984, 11986, 11988).shuffled()

    override suspend fun loop() {



//        // if in ge area
//
//        if (!Withdrawn) {
//            if (!ctx.bank.isOpen()) {
//                println("Opening bank")
//                ctx.bank.openAtGe()
//            }
//            if (ctx.bank.isOpen()) {
//                if (ctx.bank.containsAny(itemlistBank)) {
//                        itemlistBank.forEach {
//                            if (ctx.bank.getItemCount(it) > 0) {
//                                ctx.bank.withdrawAlldoActionNoted(it)
//                                delay(Random.nextLong(189, 388))
//                            }
//                        }
//                }
//                if (!ctx.bank.containsAny(itemlistBank)) {
//                    println("got items")
//                    Withdrawn = true
//                }
//            }
//        }
//        if (Withdrawn && !Sold) {
//            var invitems = ctx.inventory.getAllIds()
//            if (!invitems.isEmpty()) {
//                if (!ctx.grandExchange.isOpen()) {
//                    println("Opening ge")
//                    ctx.grandExchange.open()
//                }
//                if (ctx.grandExchange.isOpen()) {
//                    invitems.forEach {
//                        if (it > 0 && it != 995) {
//                            if (ctx.grandExchange.getFirstFreeSlot() == GrandExchange.Companion.Offers.OfferSlotOne) {
//                                ctx.grandExchange.sellItem(it, 1)
//                            }
//                            if (ctx.grandExchange.getFirstFreeSlot() != GrandExchange.Companion.Offers.OfferSlotOne) {
//                                ctx.grandExchange.collectAll()
//                            }
//                        }
//                    }
//                }
//            }
//            if (!ctx.inventory.containsAny(invitems)) {
//                println("sold items")
//                Sold = true
//            }
//        }
//        if (Sold) {
//            if (ctx.grandExchange.isOpen()) {
//                if (ctx.grandExchange.getFirstFreeSlot() != GrandExchange.Companion.Offers.OfferSlotOne) {
//                    ctx.grandExchange.collectAll()
//                }
//                if(!hasItems(REQUIRED_ITEMS)) {
//                    println(ctx.grandExchange.getFirstFreeSlot())
//                    println("has items " + hasItems(REQUIRED_ITEMS))
//                    println(ctx.vars.getVarp(375))
//                    if (ctx.grandExchange.isOpen()) {
//                        REQUIRED_ITEMS.forEach {
//                            var id = it.id
//                            if(it.amountRequired > 1){
//                                id  = id + 1
//                            }
//                            if (!ctx.inventory.Contains(id)) {
//                                if (ctx.grandExchange.getFirstFreeSlot() == GrandExchange.Companion.Offers.OfferSlotOne) {
//                                    println("Buying " + it.name + " for " + it.price + " and ammount " + it.amountRequired)
//                                    ctx.grandExchange.buyItem(it.id, it.price, it.amountRequired)
//                                }
//                                if (ctx.grandExchange.getFirstFreeSlot() != GrandExchange.Companion.Offers.OfferSlotOne) {
//                                    ctx.grandExchange.collectAll()
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            if(hasItems(REQUIRED_ITEMS)){
//                println("Got items!")
//                delay(1000)
//            }
//        }
//        delay(2000)
    }

//    private var REQUIRED_ITEMS: Array<RequiredItem> = arrayOf(
//            RequiredItem(1759, 10, 2222),
//            RequiredItem(2349,  5, 3333),
//            RequiredItem(1189,  3, 4444),
//            RequiredItem(559,  255, 10)
//            )

//    private fun hasItems(item: Array<RequiredItem>): Boolean {
//
//        var gotitems = true
//        for (it in item) {
//            if(it.amountRequired == 1) {
//                if (!ctx.inventory.Contains(it.iD )) {
//                    println("inv does not contain" + it.iD)
//                    gotitems = false
//                }
//            }
//            if(it.amountRequired > 1) {
//                if (!ctx.inventory.Contains((it.iD +1))) {
//                    println("inv does not contain" + it.iD)
//                    gotitems = false
//                }
//                if (ctx.inventory.Contains(it.iD + 1)) {
//                    println(ctx.inventory.getCount((it.iD + 1), true))
//                    if (ctx.inventory.getCount((it.iD + 1), true) < it.amountRequired) {
//                        println("stack of " + ctx.inventory.getCount(it.iD) + " id:" + it.iD + " we need " + it.amountRequired)
//                        gotitems = false
//                    }
//                }
//            }
//        }
//        return gotitems
//    }

    override suspend fun start() {
        items = ctx.inventory.getAllIds()
        try {
            if (ctx.client.getGameState() == 30){
                UserDetails.data.username = ctx.client.getLogin_username()
                UserDetails.data.password = ctx.client.getLogin_password()
            }
        } catch (e: Exception) {
        }
        println("Running Start")
        LoggingIntoAccount(ctx)
        //Lets wait till the client is logged in
        while (ctx.client.getGameState() != 30) {
            delay(100)
        }
    }


    override fun stop() {
        Withdrawn = false
        Sold = false
    }

    override fun draw(g: Graphics) {
        g.color = Color.white
        g.drawString("Current Runtime: ", 10, 450)
        if(ctx.bank.isOpen()){
            g.drawString("contains any bank items: " + ctx.bank.containsAny(itemlistBank) , 10, 350)
        }
        var invitems = ctx.inventory.getAllIds()
        g.drawString("inv contains any items: " + ctx.inventory.containsAny(invitems) , 10, 370)
            g.drawString("getprice: " + ctx.grandExchange.getPrice() , 10, 330)
        g.drawString("getQuant: " + ctx.grandExchange.getQuantity() , 10, 310)
        val chatText =
                ctx.widgets.find(WidgetID.CHATBOX_GROUP_ID, 44)
        val text = chatText?.getText()
        if(text?.contains("Set a price") == true){
            g.drawString("set a price: "  , 10, 290)
        }
        g.drawString("inv item ids " + ctx.inventory.getAllIds()  , 10, 270)
        g.drawString("bank contains any ids " + !ctx.bank.containsAny(items)  , 10, 250)
        g.drawString("inv contains any ids " + !ctx.inventory.containsAny(items)  , 10, 230)
        if(ctx.grandExchange.isOpen()) {
          ctx.grandExchange.getOffers()
        }


        super.draw(g)
    }
}