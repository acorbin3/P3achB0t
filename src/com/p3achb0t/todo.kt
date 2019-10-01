package com.p3achb0t

//Log
// 4/26/2019 - Got mouse movement with mouse press, Got applet state data, trying to loginto applet, counlt get keypress to work yet
// 4/27/2019 - Created a mouse movement utiltity that actually draws a line between points. Got Keypresses to work!
// 5/05/2019  - Figure out I needed to get the super class declared fields to collect all the info on the NPC!
//TODO - Only download new gamepacks if the applet has updated

// - List of TODO's that we are trying to accomplish
//      DONE 3/21/2019 - Install ASM lib
//      DONE - See if we can identify the Node class
//      DONE 3/21/2019 - Identified the Canvas class
//      DONE 3/27/2019 - Identify Client
//      TODO - Identify Animable
//      TODO - Identify Entity
//      DONE 4/27/2019 - Identify Mouse Event
//      DONE 3/27/2019 - Identify Npc
//      DONE 3/27/2019 - AnimatedObject
//      DONE 4/25/2019 - Figured out how to do mouse movements
//      DONE 4/25/2019 - Figured out how to add paintlistners in any set of code
//DONE 3/26/2019 Look for player(is an Actor)
//DONE 3/26/2019 Look for Actor (is an Renderable)


//TODO - Analyser - Pospone
//      TODO - Trim classes that are not used
//      TODO - Remove fields that are not used
//      TODO - Identify Classes
//      TODO - Handle modifiers
//      TODO - Check if new version out: https://gist.github.com/kylestev/6c666a312358e9fd0aa0

//  TODO - Client
//  Done 4/24/2019 - Create an explorer to view this data, maybe update every 1-10s, and refresh button
//  Done 4/24/2019 - Widget - Explorer
//  Enhancement - Add search function
//  DONE 4/13/2019 - Found and can access the widgets
//  DONE 4/14/2019 - Create a java tree with widgets to print resultValue
//  DONE 4/17/2019 - get details on Widgets
//  OBE - Why is the x and y duplicated , and one set seems values are incorrect
//  DONE 4/24/2019 - make boarders around widgets

//  DONE 6/??/2019 - Add in injection hooks


//  DONE 3/31/2019 - Add reflection into the applet
//  DONE 4/9/2019 - Displaying some debug values of the varables in the applet

//  DONE 4/4/2019 - Paint - write text
//  Enhancement - Paint - Draw lines
//  Enhancement - Paint - Draw images
//  DONE! - Paint - Highlight things within the applet

//How to add custom paints in any class:

//MainApplet.customCanvas?.addPaintListener(object: PaintListener {
//    override fun onPaint(g: Graphics) {
//        g.color = Color.white
//        g.drawString("HELLO WORLD", 50, 50)
//    }
//
//})

//  TODO - Identify - ground Items
//  DONE 4/29/2019 - Identify - NPCs
//  DONE 5/7/2019 - Identify - local players
//  DONE 6/??/2019 - Be smarter when getting all the field names. Its possible there could be multiple widgetID's between the different classes and they could get over written

//  DONE 6/18/2019 - Identify - Tiles
//  TODO - Paint - Ground Items
//  DONE 6/18/2019 - Paint - Tiles
//  DONE 6/18/2019 - Paint - NPCs

//  TODO - Interactions - menu
//  TODO - Interactions - walking
//  TODO - Interactions - widgets

// DONE 3/30/2019 - Adding graph of class structure