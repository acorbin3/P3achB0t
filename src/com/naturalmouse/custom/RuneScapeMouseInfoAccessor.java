package com.naturalmouse.custom;

import com.naturalmouse.api.MouseInfoAccessor;
import com.p3achb0t._runestar_interfaces.Client;
import com.p3achb0t.interfaces.IScriptManager;

import java.awt.*;

public class RuneScapeMouseInfoAccessor implements MouseInfoAccessor {

    private IScriptManager client;
    public RuneScapeMouseInfoAccessor(Client client) {
        this.client =(IScriptManager) client;
    }

    @Override
    public Point getMousePosition() {
        return new Point(client.getMouse().getX(), client.getMouse().getY());
    }
}