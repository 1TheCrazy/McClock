package com.github.onethecrazy.mcclock.objects;

import com.github.onethecrazy.mcclock.statics.IValueChangedCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class GuiSlider extends GuiButton {
    private float sliderValue;
    private boolean dragging;
    private final float maxValue;
    private final float minValue;
    private final String buttonText;
    private final int decimalPlaces;
    private final IValueChangedCallback valueChangeCallback;

    public GuiSlider(int buttonId, int _x, int _y, int width, int height, String _buttonText, float defaultValue, float _maxValue, float _minValue, int _decimalPlaces, IValueChangedCallback _valueChangeCallback) {
        super(buttonId, _x, _y, width, height, _buttonText + ": " + new BigDecimal(defaultValue).setScale(_decimalPlaces, RoundingMode.HALF_UP).floatValue());
        this.sliderValue = defaultValue;//also init-value

        maxValue = _maxValue;
        minValue = _minValue;
        buttonText = _buttonText;
        decimalPlaces = _decimalPlaces;
        valueChangeCallback = _valueChangeCallback;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY){
        super.drawButton(mc, mouseX, mouseY);

        if(this.visible){
            this.displayString = buttonText + ": " + new BigDecimal(this.sliderValue).setScale(decimalPlaces, RoundingMode.HALF_UP).floatValue();
        }
    }

    @Override
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            if (this.dragging) {
                this.sliderValue = ((float)(mouseX - (this.xPosition + 4)) / (float)(this.width - 8)) * maxValue;
                this.sliderValue = Math.max(minValue, Math.min(sliderValue, maxValue));

                valueChangeCallback.onValueChanged(sliderValue);
            }

            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect((int)(this.xPosition + ((sliderValue * (this.width - 8)) / maxValue)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect((int)(this.xPosition + ((sliderValue * (this.width - 8)) / maxValue)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }


    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            this.sliderValue = (float)(mouseX - (this.xPosition + 4)) / (float)(this.width - 8);
            this.sliderValue = Math.max(minValue, Math.min(sliderValue, maxValue));
            this.dragging = true;
            return true;
        }
        return false;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
    }

    public float getSliderValue() {
        return sliderValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setSliderValue(float value) {
        sliderValue = value;
        valueChangeCallback.onValueChanged(sliderValue);
    }
}
