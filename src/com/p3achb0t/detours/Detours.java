package com.p3achb0t.detours;

import com.p3achb0t.api.script.ActionScript;
import com.p3achb0t.api.Context;
import com.p3achb0t.api.interfaces.Client;
import com.p3achb0t.client.injection.InstanceManager;


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
                                InstanceManager scriptManager){

        ActionScript script = scriptManager.getActionScript();
        Context ctx = script.getCtx();
        Client client = ctx.getClient();
        client.doAction(actionParam,widgetID,menuAction,id,menuOption,menuTarget,mouseX,mouseY,dummy);
    }
}