package com.github.joonasvali.naturalmouse.support;

import com.github.joonasvali.naturalmouse.api.MouseInfoAccessor;
import com.p3achb0t.MainApplet;
import com.p3achb0t.api.Constants;
import com.p3achb0t.api.user_inputs.Mouse;
import com.p3achb0t.ui.Context;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class DefaultMouseInfoAccessor implements MouseInfoAccessor {
    private Context ctx = null;
    public DefaultMouseInfoAccessor(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public Point getMousePosition() {

        if (this.ctx.getMouse() != null) {
            return new Point(this.ctx.getMouse().getMouseEvent().getX(), this.ctx.getMouse().getMouseEvent().getY());
        } else {
            Random rand = new Random();
            return new Point(rand.nextInt(Constants.INSTANCE.getGAME_FIXED_WIDTH()), rand.nextInt(Constants.INSTANCE.getGAME_FIXED_HEIGHT()));
        }
    }
}
