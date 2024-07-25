package com.github.onethecrazy.mcclock;

import com.github.onethecrazy.mcclock.commands.ClockCommand;
import com.github.onethecrazy.mcclock.commands.ClockGUICommand;
import com.github.onethecrazy.mcclock.objects.Clock;
import com.github.onethecrazy.mcclock.objects.ClockState;
import com.github.onethecrazy.mcclock.objects.StateSaver;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;

@Mod(modid = "mcclock", useMetadata=true)
public class McClocks {

    public static ClockState state;
    public static GuiScreen commandOpenGui = null;
    public static boolean isConfigGuiOpen = false;
    public static Clock clock ;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        StateSaver.Init();
        clock = new Clock();

        ClientCommandHandler.instance.registerCommand(new ClockGUICommand());
        ClientCommandHandler.instance.registerCommand(new ClockCommand());
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(clock);
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
