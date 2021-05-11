package com.p3achb0t.client.cache.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Util {

    public static byte[] gzip(byte[] bytes) throws IOException {
        var baos = new ByteArrayOutputStream();

        try (var gzip = new GZIPOutputStream(baos)) {
            gzip.write(bytes);
        }

        return baos.toByteArray();
    }

    public static byte[] ungzip(byte[] bytes) throws IOException {
        return new GZIPInputStream(new ByteArrayInputStream(bytes)).readAllBytes();
    }
}
