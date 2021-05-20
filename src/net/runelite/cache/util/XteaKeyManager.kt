/*
 * Copyright (c) 2016-2017, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.cache.util

import com.google.common.reflect.TypeToken
import java.util.HashMap
import net.runelite.http.api.RuneLiteAPI
import java.util.stream.Collectors
import java.lang.InterruptedException
import org.slf4j.LoggerFactory
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class XteaKeyManager {
    private val keys: HashMap<Int, IntArray> = HashMap()
    fun loadKeys() {
        val XTEA_URI = URI.create("https://archive.runestats.com/osrs/xtea/2021-05-05-rev195.json")
        val HTTP_CLIENT = HttpClient.newHttpClient()
        var regionKeys: Map<Int?, IntArray?>? = null
        try {
            regionKeys = RuneLiteAPI.GSON.fromJson<List<RegionKey>>(
                HTTP_CLIENT.send(HttpRequest.newBuilder(XTEA_URI).build(), HttpResponse.BodyHandlers.ofString()).body(),
                object : TypeToken<List<RegionKey?>?>() {}.type
            ).stream()
                .collect(Collectors.toMap({ p: RegionKey -> p.mapsquare }, { p: RegionKey -> p.key }))
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        		try
		{

		    regionKeys?.forEach { (t, u) ->
                keys[t as Int] = u as IntArray
            }

		}
		catch ( ex: IOException)
		{
			// happens on release when it is not deployed yet
			logger.debug("unable to load xtea keys", ex);
			return;
		}
        logger.info("Loaded {} keys", keys.size)
    }

    fun getKeys(region: Int): IntArray? {
        return keys[region]
    }

    private class RegionKey private constructor(val mapsquare: Int, val key: IntArray)
    companion object {
        private val logger = LoggerFactory.getLogger(XteaKeyManager::class.java)
    }
}