package com.p3achb0t.ui.components

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
    }
}