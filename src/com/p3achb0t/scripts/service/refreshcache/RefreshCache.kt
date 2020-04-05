package com.p3achb0t.scripts.service.refreshcache

import com.p3achb0t.api.ScriptManifest
import com.p3achb0t.api.ServiceScript
import com.p3achb0t.api.utils.Script
import java.io.File

@ScriptManifest(Script.SERVICE,"Refresh Cache","P3aches", "0.1")
class RefreshCache: ServiceScript()  {
    override suspend fun loop() {
        val folder = File(".cache")
        folder.deleteRecursively()
        folder.mkdirs()
        ctx.cache.updateCache(true)
    }
}