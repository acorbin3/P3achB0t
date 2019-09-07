package com.github.joonasvali.naturalmouse.support;

import com.github.joonasvali.naturalmouse.api.SystemCalls;
import com.p3achb0t.MainApplet;
import com.p3achb0t.api.user_inputs.Mouse;
import com.p3achb0t.ui.Context;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class DefaultSystemCalls implements SystemCalls {
    Context ctx;
    public DefaultSystemCalls(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override
    public void sleep(long time) throws InterruptedException {
        Thread.sleep(time);
    }

    @Override
    public Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * <p>Moves the mouse to specified pixel using the provided Robot.</p>
     *
     * <p>It seems there is a certain delay, measurable in less than milliseconds,
     * before the mouse actually ends up on the requested pixel when using a Robot class.
     * this usually isn't a problem, but when we ask the mouse position right after this call,
     * there's extremely low but real chance we get wrong information back. I didn't add sleep
     * here as it would cause overhead to sleep always, even when we don't instantly use
     * the mouse position, but just acknowledged the issue with this warning.
     * (Use fast unrestricted loop of Robot movement and checking the position after every move to invoke the issue.)</p>
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    @Override
    public void setMousePosition(int x, int y) {

        MouseEvent mouseMove = new MouseEvent(
                this.ctx.getClientInstance().getApplet().getComponent(0),
                MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, x, y, 0, false
        );
//        this.ctx.getMouse().setMouseEvent(mouseMove);
        MouseListener[] ml = this.ctx.getClientInstance().getApplet().getComponent(0).getMouseListeners();
        for (MouseListener mouseListener : ml) {
            if (mouseListener.getClass() == Mouse.class) {
                mouseListener.mouseClicked(mouseMove);
            }
        }
        this.ctx.getClientInstance().getApplet().getComponent(0).dispatchEvent(mouseMove);

    }
}
