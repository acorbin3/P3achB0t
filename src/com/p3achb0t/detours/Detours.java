package com.p3achb0t.detours;

import com.p3achb0t._runestar_interfaces.Client;
import com.p3achb0t.api.AbstractScript;
import com.p3achb0t.api.Context;
import com.p3achb0t.interfaces.ScriptManager;

public class Detours {
    public static void doAction(int actionParam,
                                int widgetID,
                                int menuAction,
                                int id,
                                String menuOption,
                                String menuTarget,
                                int mouseX,
                                int mouseY,
                                int dummy,
                                ScriptManager scriptManager){

        AbstractScript script = scriptManager.getScript();
        Context ctx = script.getCtx();
        Client client = ctx.getClient();
        client.doAction(actionParam,widgetID,menuAction,id,menuOption,menuTarget,mouseX,mouseY,dummy);
    }
}