package com.github.onethecrazy.mcclock;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Clock{

    public static FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event){
        if(!McClocks.state.isEnabled)
            return;


        GlStateManager.pushMatrix();

        ClockState state = McClocks.state;

        //------- Assing Clock Text -------
        String clockText = "";
        if(state.showDate){
            clockText += new SimpleDateFormat(state.datePattern).format(new Date()) + " / ";
        }

        if(state.useTwentyFourHourFormat){
            clockText += new SimpleDateFormat("HH:mm:ss").format(new Date());
        }
        else{
            clockText += new SimpleDateFormat("hh:mm:ss").format(new Date());
        }

        if(state.showWeekday){
            clockText += " / " + new SimpleDateFormat("EEEE").format(new Date());
        }

        //------- Apply Scaling -------
        GlStateManager.translate(state.left, state.top, 1);
        GlStateManager.scale(state.size, state.size, 1);

        //------- Apply Text Color -------
        int textColor = state.isChroma ? getChromaColor() : state.textColor;

        //------- Fix Text Size and Position -------
        float requiredTextScale = state.size;
        if((fontRenderer.getStringWidth(clockText) + 16) > (state.right - state.left))
            requiredTextScale *= (state.right - state.left) / ((fontRenderer.getStringWidth(clockText) + 16f));


//include reqTextScale in calculations and fix file structure pls UwU
        float textX = (state.left * requiredTextScale) + ((state.right - state.left) - fontRenderer.getStringWidth(clockText)) / 2f;
        float textY = (state.top * requiredTextScale) + ((state.bottom - state.top) - fontRenderer.FONT_HEIGHT) / 2f;

LogManager.getLogger("McClock").info("X: " + textX + ", Y: " + textY);
        GuiScreen.drawRect(state.left, state.top, state.right, state.bottom, (int)state.backgroundColor);


        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();

        GlStateManager.scale(requiredTextScale, requiredTextScale, 1);
        GlStateManager.translate(textX, textY, 1);

        fontRenderer.drawString(clockText, 0, 0, textColor);

        GlStateManager.popMatrix();
    }

    public static int getChromaColor() {
        long time = System.currentTimeMillis();

        int color = java.awt.Color.HSBtoRGB((time % 2000L) / 2000.0f, 0.8f, 0.8f);
        return color | 0xFF000000;
    }
}
