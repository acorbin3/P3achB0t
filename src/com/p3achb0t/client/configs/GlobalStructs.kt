package com.p3achb0t.client.configs

import com.p3achb0t.client.communication.ipc.Broker
import com.p3achb0t.client.communication.peer.PeerClient
import com.p3achb0t.client.scripts.loading.LoadScripts
import com.p3achb0t.client.tracker.FBDataBase
import com.p3achb0t.client.ux.BotManager

class GlobalStructs {

    companion object {

        lateinit var botManager: BotManager

        // Loading the scripts just makes the script available for each tab. Each tab has its own instance of the script
        // and will be handled by the InstanceManager
        val scripts = LoadScripts()

        val communication = Broker()
        val peerClient = PeerClient()
        val db : FBDataBase = FBDataBase()
    }

}