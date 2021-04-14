package com.p3achb0t.api.cache.content.config;

import com.p3achb0t.api.cache.content.CacheType;
import com.p3achb0t.api.cache.content.io.Input;

public abstract class ConfigType extends CacheType {

    public static final int ARCHIVE = 2;

    @Override public final void decode(Input in) {
        decode0(in);
        postDecode();
    }

    protected abstract void decode0(Input input);

    protected void postDecode() {}

    protected static void unrecognisedCode(int code) {
        System.out.println("UnrecognozedCode: " + code);
//        throw new UnsupportedOperationException(Integer.toString(code));
    }
}
