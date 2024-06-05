package com.github.onethecrazy.mcclock.objects;

import com.github.onethecrazy.mcclock.McClocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Clock{

    public static FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event){
        if(!McClocks.state.isEnabled)
            return;

        if(McClocks.isConfigGuiOpen && event != null)
            return;

        GlStateManager.pushMatrix();

        ClockState state = McClocks.state;

        //------- Assign Clock Text -------
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
        //GlStateManager.translate(state.left, state.top, 1);
        GlStateManager.scale(state.size, state.size, 1);

        //------- Fix Rect Size -------
        state.bottom = (state.top + (state.heightMargin * 2 + fontRenderer.FONT_HEIGHT - 1));
        state.right = (state.left + (state.widthMargin * 2 + fontRenderer.getStringWidth(clockText)));

        //------- Fix Text Position -------
        int textX = state.widthMargin + state.left;
        int textY = state.heightMargin;

        GuiScreen.drawRect(state.left, state.top, state.right, state.bottom, (int)state.backgroundColor);

        //------- Apply Text Color -------
        if(state.isChroma && state.chromaVariation == 0)
            fontRenderer.drawString(clockText, textX, textY , getChromaColor(0, 1));
        else if (state.isChroma)
            renderChromaVariation(clockText, textY);
        else
            fontRenderer.drawString(clockText, textX, textY , state.textColor);

        GlStateManager.popMatrix();
    }

    public static int getChromaColor(int offset, float speed) {
        long time = System.currentTimeMillis();
        float hue = ((time + offset) % (long)(2000L / speed)) / (2000.0f / speed);
        int color = java.awt.Color.HSBtoRGB(hue, 0.8f, 0.8f);
        return color | 0xFF000000;
    }

    public static void renderChromaVariation(String clockText, int textY) {

        switch(McClocks.state.chromaVariation){
            case 1:
                for(int i = 0; i < clockText.length(); i++){
                    fontRenderer.drawString(clockText.substring(i, i + 1), McClocks.state.widthMargin + fontRenderer.getStringWidth(clockText.substring(0, i)), textY , getChromaColor(i * 50, 1f));
                }
                break;
            case 2:
                for(int i = 0; i < clockText.length(); i++){
                    fontRenderer.drawString(clockText.substring(i, i + 1), McClocks.state.widthMargin + fontRenderer.getStringWidth(clockText.substring(0, i)), textY , getChromaColor(i * -50, 1f));
                }
                break;
            case 3:
                for(int i = 0; i < clockText.length(); i++){
                    fontRenderer.drawString(clockText.substring(i, i + 1), McClocks.state.widthMargin + fontRenderer.getStringWidth(clockText.substring(0, i)), textY , getChromaColor(i * 50 , 0.5f));
                }
                break;


            default:
                break;
        }
    }
}
