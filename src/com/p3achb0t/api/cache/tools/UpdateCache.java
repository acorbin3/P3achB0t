package com.p3achb0t.api.cache.tools;

import com.p3achb0t.api.cache.format.Cache;
import com.p3achb0t.api.cache.format.disk.DiskCache;
import com.p3achb0t.api.cache.format.net.NetCache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

public class UpdateCache {

    public static void main(String[] args) throws IOException {
        var start = Instant.now();

        try (var net = NetCache.connect(new InetSocketAddress("oldschool7.runescape.com", NetCache.DEFAULT_PORT), 189);
             var disk = DiskCache.open(Path.of(".cache"))) {
            Cache.update(net, disk).join();
        }

        System.out.println(Duration.between(start, Instant.now()));
    }
}
