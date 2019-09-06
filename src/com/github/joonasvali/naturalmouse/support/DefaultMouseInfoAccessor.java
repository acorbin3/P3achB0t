package com.github.joonasvali.naturalmouse.support;

import com.github.joonasvali.naturalmouse.api.MouseInfoAccessor;
import com.p3achb0t.MainApplet;
import com.p3achb0t.api.Constants;
import com.p3achb0t.api.user_inputs.Mouse;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class DefaultMouseInfoAccessor implements MouseInfoAccessor {
    private Mouse mouse = null;
    public DefaultMouseInfoAccessor(Mouse mouse){
        this.mouse = mouse;
    }

    @Override
    public Point getMousePosition() {

        if (this.mouse != null) {
            return new Point(this.mouse.getMouseEvent().getX(), this.mouse.getMouseEvent().getY());
        } else {
            Random rand = new Random();
            return new Point(rand.nextInt(Constants.INSTANCE.getGAME_FIXED_WIDTH()), rand.nextInt(Constants.INSTANCE.getGAME_FIXED_HEIGHT()));
        }
    }
}
