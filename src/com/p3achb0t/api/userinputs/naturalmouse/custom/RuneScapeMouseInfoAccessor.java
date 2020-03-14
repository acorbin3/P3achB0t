package com.p3achb0t.api.userinputs.naturalmouse.custom;

import com.p3achb0t.api.interfaces.IOHandler;
import com.p3achb0t.api.userinputs.naturalmouse.api.MouseInfoAccessor;
import com.p3achb0t.api.interfaces.Client;

import java.awt.*;

public class RuneScapeMouseInfoAccessor implements MouseInfoAccessor {

    private IOHandler client;
    public RuneScapeMouseInfoAccessor(Client client) {
        this.client =(IOHandler) client;
    }

    @Override
    public Point getMousePosition() {
        return new Point(client.getMouse().getX(), client.getMouse().getY());
    }
}