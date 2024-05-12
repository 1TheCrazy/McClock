package com.github.onethecrazy.mcclock;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatComponentText;

import java.io.IOException;

public class ClockConfigGUI extends GuiScreen {
    public static final int MOUSE_LEFT = 0;
    public static final int MOUSE_RIGHT = 1;
    public static final int MOUSE_MIDDLE = 2;
    public static final int MOUSE_BACKWARD = 3;
    public static final int MOUSE_FORWARD = 4;


    @Override
    public void initGui() {
        super.initGui();

        this.buttonList.add(new GuiButton(0, width/2 - 50, height/2 - 10, 100, 20, McClocks.Save.isClockEnabled ? "Enabled": "Disabled"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException{
        McClocks.Save.isClockEnabled = !McClocks.Save.isClockEnabled;
        StateSaver.Save();
        button.displayString = McClocks.Save.isClockEnabled ? "Enabled" : "Disabled";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
