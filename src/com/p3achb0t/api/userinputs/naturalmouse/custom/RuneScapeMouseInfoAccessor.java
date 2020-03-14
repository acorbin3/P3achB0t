package com.p3achb0t.api.userinputs.naturalmouse.custom;

import com.p3achb0t.api.userinputs.naturalmouse.api.MouseInfoAccessor;
import com.p3achb0t.api.interfaces.Client;
import com.p3achb0t.api.interfaces.ScriptManager;

import java.awt.*;

public class RuneScapeMouseInfoAccessor implements MouseInfoAccessor {

    private ScriptManager client;
    public RuneScapeMouseInfoAccessor(Client client) {
        this.client =(ScriptManager) client;
    }

    @Override
    public Point getMousePosition() {
        return new Point(client.getMouse().getX(), client.getMouse().getY());
    }
}