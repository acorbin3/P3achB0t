package com.p3achb0t.api

import java.awt.Dimension

object Constants {
    /**
     * The original width of the game when running in fixed mode.
     */
    val GAME_FIXED_WIDTH = 765

    /**
     * The original height of the game when running in fixed mode.
     */
    val GAME_FIXED_HEIGHT = 503

    /**
     * Dimension representation of the width and height of the game in fixed mode.
     */
    val GAME_FIXED_SIZE = Dimension(GAME_FIXED_WIDTH, GAME_FIXED_HEIGHT)

    /**
     * The aspect ratio of the game when running in fixed mode.
     */
    val GAME_FIXED_ASPECT_RATIO = GAME_FIXED_WIDTH.toDouble() / GAME_FIXED_HEIGHT.toDouble()

    /**
     * The default camera zoom value.
     */
    val CLIENT_DEFAULT_ZOOM = 512

    /**
     * The width and length of a chunk (8x8 tiles).
     */
    val CHUNK_SIZE = 8

    /**
     * The width and length of a map region (64x64 tiles).
     */
    val REGION_SIZE = 64

    /**
     * The width and length of the scene (13 chunks x 8 tiles).
     */
    val SCENE_SIZE = 104

    /**
     * The max allowed plane by the game.
     *
     *
     * This value is exclusive. The plane is set by 2 bits which restricts
     * the plane value to 0-3.
     */
    val MAX_Z = 4

    val TILE_FLAG_BRIDGE = 2

    /**
     * The number of milliseconds in a client tick.
     *
     *
     * This is the length of a single frame when the client is running at
     * the maximum framerate of 50 fps.
     */
    val CLIENT_TICK_LENGTH = 20

    /**
     * The number of milliseconds in a server game tick.
     *
     *
     * This is the length of a single game cycle under ideal conditions.
     * All game-play actions operate within multiples of this duration.
     */
    val GAME_TICK_LENGTH = 600
}