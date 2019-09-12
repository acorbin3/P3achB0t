package com.github.joonasvali.naturalmouse.support;

import com.github.joonasvali.naturalmouse.api.MouseInfoAccessor;
//import com.p3achb0t.MainApplet;
import com.p3achb0t.api.Constants;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class DefaultMouseInfoAccessor implements MouseInfoAccessor {

    @Override
    public Point getMousePosition() {
/*
        if (MainApplet.Data.getMouseEvent() != null) {
            return new Point(Objects.requireNonNull(MainApplet.Data.getMouseEvent()).getX(), MainApplet.Data.getMouseEvent().getY());
        } else {
            Random rand = new Random();
            return new Point(rand.nextInt(Constants.INSTANCE.getGAME_FIXED_WIDTH()), rand.nextInt(Constants.INSTANCE.getGAME_FIXED_HEIGHT()));
        }*/
    return new Point(23,45);
    }
}
