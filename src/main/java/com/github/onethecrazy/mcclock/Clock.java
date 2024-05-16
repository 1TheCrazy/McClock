package com.github.onethecrazy.mcclock;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import scala.Float;
import scala.Int;

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
        {
            LogManager.getLogger("McClock").info("size needs to be reworked");
            requiredTextScale *= ((state.right - state.left) / (float)fontRenderer.getStringWidth(clockText));
        }


        int textX = state.left + ((state.right - state.left) - fontRenderer.getStringWidth(clockText)) / 2;
        int textY = state.top + ((state.bottom - state.top) - fontRenderer.FONT_HEIGHT) / 2;


        GuiScreen.drawRect(state.left, state.top, state.right, state.bottom, (int)state.backgroundColor);


//doesnt work
        GlStateManager.scale(1, 1, 1);
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
