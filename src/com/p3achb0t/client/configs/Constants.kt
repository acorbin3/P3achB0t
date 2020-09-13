package com.p3achb0t.client.configs

class Constants {
    companion object {
        // URLs
        const val GAME_BASE_URL = "runescape.com"
        const val GAME_SUB_ROUTE = "oldschool"
        const val GAME_WORLD = 81
        const val GAME_WORLD_BASE = "$GAME_SUB_ROUTE$GAME_WORLD.$GAME_BASE_URL"
        const val GAME_WORLD_URL = "http://$GAME_WORLD_BASE"

        //Paths
        val USER_DIR = System.getProperty("user.dir")
        const val APPLICATION_CACHE_DIR = "app"
        const val JARS_DIR = "gamepack"
        const val SCRIPTS_DIR = "scripts"
        const val SCRIPTS_DEBUG_DIR = "debug"
        const val SCRIPTS_ABSTRACT_DIR = "user"

        const val ACCOUNTS_DIR = "user"
        const val ACCOUNTS_FILE = "accounts.json"

        // Other constants
        val REVISION = 191
        val REVISION_WITH_SUBVERSION = REVISION + .2 // Update this if we want to force a new injected jar
        var INJECTED_JAR_NAME = "gamepack-$REVISION_WITH_SUBVERSION-injected.jar"

        const val MIN_GAME_WIDTH = 765
        const val MIN_GAME_HEIGHT = 503

        //Icons
        const val RESOURCE_DIR = "resources"
        const val ICONS_DIR = "icons"

        const val ICON_PATH = "./" + RESOURCE_DIR + "/" + ICONS_DIR + "/"

        const val ICON_MOUSE = "mouse.png"
        const val ICON_PAUSE = "pause.png"
        const val ICON_PLAY = "play.png"
        const val ICON_PLUS = "plus.png"
        const val ICON_RESUME = "resume.png"
        const val ICON_STOP = "stop.png"
        const val ICON_KEYBOARD = "keyboard.png"
    }
}