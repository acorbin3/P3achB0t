package com.p3achb0t.api.userinputs.naturalmouse.custom;

import com.p3achb0t.api.interfaces.IOHandler;
import com.p3achb0t.api.userinputs.naturalmouse.api.SystemCalls;
import com.p3achb0t.api.interfaces.Client;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;

public class RuneScapeSystemCalls implements SystemCalls {
    private final Client ctx;
    private final IOHandler manager;
    private final Applet applet;

    public RuneScapeSystemCalls(Client ctx, Applet a) {
        this.ctx = ctx;
        manager = (IOHandler) ctx;
        applet = a;
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
        MouseEvent mouseMove = new MouseEvent(applet.getComponent(0), MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, x,y,0,false);
        manager.getMouse().sendEvent(mouseMove);
        //robot.mouseMove(x, y);
    }
}

/*
public void dispatchEvent(int id, Point position, int modifier, int clickCount)
	{
		this.position = position;
		botMouse.sendEvent(new MouseEvent(context.bot.getCanvas(), id, System.currentTimeMillis(), modifier, position.x, position.y, clickCount, false));
	}
 */