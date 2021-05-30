package com.p3achb0t.api

import com.p3achb0t.api.Context

enum class LoginResponse(
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
    INVALID(3),
    /**
     * The client is at the login screen.
     */
    BANNED(12),
    /**
     * The client is at the login screen entering authenticator code.
     */
    LOGINSCREEN(2),
    /**
     * There is a player logging in.
     */
    AUTHENTICATOR(4);


    companion object {
        /**
         * Utility method that maps the rank value to its respective
         * [LoginResponse] value.
         *
         * @param state the raw state value
         * @return the gamestate
         */
        fun of(state: Int): LoginResponse {
            for (gs in values()) {
                if (gs.state == state) {
                    return gs
                }
            }
            return UNKNOWN
        }
        fun getLoginResponse(ctx: Context): LoginResponse{
            return of(ctx.client.get__ct_aj())
//            return LoginResponse.UNKNOWN
        }
    }

}