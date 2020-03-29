package com.p3achb0t.api.cache.content;

import com.p3achb0t.api.cache.content.io.Input;
import com.p3achb0t.api.cache.content.io.Packet;

import java.nio.ByteBuffer;

public abstract class CacheType {

    public abstract void decode(Input in);

    public final void decode(ByteBuffer buffer) {
        decode(new Packet(buffer));
    }

    public final void decode(byte[] bytes) {
        decode(ByteBuffer.wrap(bytes));
    }
}