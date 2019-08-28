package com.p3achb0t.ui.components

import com.p3achb0t.ui.GlobalConfigs

class Constants {
    companion object {
        // URLs
        const val GAME_BASE_URL = "runescape.com"
        const val GAME_SUB_RUTE = "oldschool"
        const val GAME_WORLD = 83
        const val GAME_WORLD_URL = "http://$GAME_SUB_RUTE" + GAME_WORLD + ".$GAME_BASE_URL"
        const val GAME_WORLD_BASE = GAME_SUB_RUTE + GAME_WORLD + ".$GAME_BASE_URL"

        //Paths
        val USER_DIR = System.getProperty("user.dir")
        const val APPLICATION_CACHE_DIR = "app"
        const val JARS_DIR = "gamepack"
        const val SCRIPTS_DIR = "scripts"


        // Other constants
        val REVISION = 181
        var INJECTED_JAR_NAME = "gamepack-${REVISION}-injected.jar"

        //Icons
        const val RESOURCE_DIR = "resources"
        const val ICONS_DIR = "icons"

        const val ICON_PATH = "../" + RESOURCE_DIR + "/" + ICONS_DIR + "/"

        const val ICON_MOUSE = "mouse.png"
        const val ICON_PAUSE = "pause.png"
        const val ICON_PLAY = "play.png"
        const val ICON_PLUS = "plus.png"
        const val ICON_RESUME = "resume.png"
        const val ICON_STOP = "stop.png"
        const val ICON_KEYBOARD = "keyboard.png"
    }
}