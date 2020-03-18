package com.p3achb0t.client.configs

import com.p3achb0t.client.communication.ipc.Broker
import com.p3achb0t.client.communication.peer.PeerClient
import com.p3achb0t.client.scripts.loading.LoadScripts
import com.p3achb0t.client.ux.BotTabBar

class GlobalStructs {

    companion object {
        val botTabBar = BotTabBar()
        // minimum [765,503]
        val width = 800
        val height = 600

        val scripts = LoadScripts()

        val communication = Broker()
        val peerClient = PeerClient();
    }
}