package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context

enum class GameState(
        /**
         * The raw state value.
         */
        val state: Int) {
    /**
     * Unknown game state.
     */
    UNKNOWN(-1),
    /**
     * The client is starting.
     */
    STARTING(0),
    /**
     * The client is at the login screen.
     */
    LOGIN_SCREEN(10),
    /**
     * The client is at the login screen entering authenticator code.
     */
    LOGIN_SCREEN_AUTHENTICATOR(11),
    /**
     * There is a player logging in.
     */
    LOGGING_IN(20),
    /**
     * The game is being loaded.
     */
    LOADING(25),
    /**
     * The user has successfully logged in.
     */
    LOGGED_IN(30),
    /**
     * Connection to the server was lost.
     */
    CONNECTION_LOST(40),
    /**
     * A world hop is taking place.
     */
    HOPPING(45);

    companion object {
        /**
         * Utility method that maps the rank value to its respective
         * [GameState] value.
         *
         * @param state the raw state value
         * @return the gamestate
         */
        fun of(state: Int): GameState {
            for (gs in values()) {
                if (gs.state == state) {
                    return gs
                }
            }
            return UNKNOWN
        }
        fun currentState(ctx: Context): GameState{
            return of(ctx.client.getGameState())
        }
    }

}