package com.github.onethecrazy.mcclock;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = "mcclock", useMetadata=true)
public class McClocks {

    public static SaveClass Save;
    public static GuiScreen commandOpenGui = null;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        StateSaver.Init();

        ClientCommandHandler.instance.registerCommand(new ClockGUICommand());
        ClientCommandHandler.instance.registerCommand(new ClockCommand());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) return;
        if (commandOpenGui != null) {
            Minecraft.getMinecraft().displayGuiScreen(commandOpenGui);
            commandOpenGui = null;
        }
    }
}
