package com.github.onethecrazy.mcclock.commands;

import com.github.onethecrazy.mcclock.objects.GuiSlider;
import com.github.onethecrazy.mcclock.McClocks;
import com.github.onethecrazy.mcclock.objects.StateSaver;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.GuiTextField;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

public class ClockConfigGUI extends GuiScreen {

    private boolean isResizeDragging = false;
    private boolean isMoveDragging = false;
    private GuiTextField heightMarginTextField;
    private GuiTextField widthMarginTextField;

    @Override
    public void initGui() {
        super.initGui();

        McClocks.isConfigGuiOpen = true;
        this.buttonList.add(new GuiButton(0, width/2 - 100, height/2 - 76, 200, 20, McClocks.state.isEnabled ? "Enabled": "Disabled"));
        this.buttonList.add(new GuiSlider(1, this.width / 2 - 100, this.height / 2 - 54, 200, 20, "Size", McClocks.state.size, 5f, 0f, 1, this::onSizeChangedCallback));
        this.buttonList.add(new GuiButton(2, width/2 - 100, height/2 - 32, 200, 20, McClocks.state.isChroma ? "Chroma: On (Var. " + McClocks.state.chromaVariation + ")" : "Chroma: Off (Var. " + McClocks.state.chromaVariation + ")" ));
        this.buttonList.add(new GuiButton(7, width/2 - 100 + 200 + 1, height/2 - 32, 10, 20, "<"));
        this.buttonList.add(new GuiButton(8, width/2 - 100 + 200 + 10, height/2 - 32, 10, 20, ">"));
        this.buttonList.add(new GuiButton(3, width/2 - 100, height/2 - 10, 200, 20, McClocks.state.showDate ? "Show Date: On": "Show Date: Off"));
        this.buttonList.add(new GuiButton(4, width/2 - 100, height/2 + 12, 200, 20, McClocks.state.showWeekday ? "Show Weekday: On": "Show Weekday: Off"));
        this.buttonList.add(new GuiButton(5, width/2 - 100, height/2 + 34, 200, 20, "Date Pattern: " + McClocks.state.datePattern));
        this.buttonList.add(new GuiButton(6, width/2 - 100, height/2 + 56, 200, 20,  McClocks.state.useTwentyFourHourFormat ? "24 hour format": "12 hour format"));
        this.heightMarginTextField = new GuiTextField(0, this.fontRendererObj, width/2 - 100 + 2, height/2 + 78, 30, 20); //maybe dont add textfields for this, idk...
        this.widthMarginTextField = new GuiTextField(0, this.fontRendererObj, width/2 - 100 + 183, height/2 + 78, 30, 20);

        this.heightMarginTextField.setMaxStringLength(3);
        this.widthMarginTextField.setMaxStringLength(3);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException{
        if(button.id == 0){
            McClocks.state.isEnabled = !McClocks.state.isEnabled;
            button.displayString = McClocks.state.isEnabled ? "Enabled" : "Disabled";
        }else if(button.id == 2){
            McClocks.state.isChroma = !McClocks.state.isChroma;
            button.displayString = McClocks.state.isChroma ? "Chroma: On (Var. " + McClocks.state.chromaVariation + ")" : "Chroma: Off (Var. " + McClocks.state.chromaVariation + ")";
        }else if(button.id == 3){
            McClocks.state.showDate = !McClocks.state.showDate;
            button.displayString = McClocks.state.showDate ? "Show Date: On" : "Show Date: Off";
        }else if(button.id == 4){
            McClocks.state.showWeekday = !McClocks.state.showWeekday;
            button.displayString = McClocks.state.showWeekday ? "Show Weekday: On" : "Show Weekday: Off";
        }else if(button.id == 5){
            String currentPatten = McClocks.state.datePattern;
            String newDatePattern;

            switch (currentPatten) {
                case "dd/MM/yyyy":
                    newDatePattern = "MM/dd/yyy";
                    break;
                case "MM/dd/yyy":
                    newDatePattern = "dd. MMM yyyy";
                    break;
                case "dd. MMM yyyy":
                    newDatePattern = "dd. MMMM yyyy";
                    break;
                case "dd. MMMM yyyy":
                    newDatePattern = "dd/MM/yyyy";
                    break;

                    //This will never happen, but the compiler wants what the compiler wants...
                default:
                    newDatePattern = "dd/MM/yyyy";
                    break;
            }

            McClocks.state.datePattern = newDatePattern;
            button.displayString = "Date Pattern: " + McClocks.state.datePattern;
        }else if(button.id == 6){
            McClocks.state.useTwentyFourHourFormat = !McClocks.state.useTwentyFourHourFormat;
            button.displayString = McClocks.state.useTwentyFourHourFormat ? "24 hour format": "12 hour format";
        }else if(button.id == 7){
            McClocks.state.chromaVariation -= McClocks.state.chromaVariation == 0 ? 0 : 1;
            buttonList.get(2).displayString = McClocks.state.isChroma ? "Chroma: On (Var. " + McClocks.state.chromaVariation + ")" : "Chroma: Off (Var. " + McClocks.state.chromaVariation + ")";
        }else if(button.id == 8){
            McClocks.state.chromaVariation += McClocks.state.chromaVariation == 3 ? 0 : 1;
            buttonList.get(2).displayString = McClocks.state.isChroma ? "Chroma: On (Var. " + McClocks.state.chromaVariation + ")" : "Chroma: Off (Var. " + McClocks.state.chromaVariation + ")";
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        ResizeDrag(mouseX, mouseY);
        MoveDrag(mouseX, mouseY);

        if(McClocks.state.isEnabled)
            drawRect((int)(McClocks.state.right * McClocks.state.size - 3), (int)(McClocks.state.bottom * McClocks.state.size - 3), (int)(McClocks.state.right * McClocks.state.size + 3), (int)(McClocks.state.bottom * McClocks.state.size + 3), 0xAE7FE8E8);

        GlStateManager.disableDepth();
        McClocks.clock.onRenderGameOverlay(null);
        GlStateManager.enableDepth();

        this.heightMarginTextField.drawTextBox();
        this.widthMarginTextField.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.widthMarginTextField.textboxKeyTyped(typedChar, keyCode);
        this.heightMarginTextField.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if((mouseX >= (int)(McClocks.state.right * McClocks.state.size - 3)) && (mouseX < (int)(McClocks.state.right * McClocks.state.size + 3)) &&
                (mouseY >= (int)(McClocks.state.bottom * McClocks.state.size - 3)) && mouseY < ((int)(McClocks.state.bottom * McClocks.state.size + 3)))
            isResizeDragging = true;

        if((mouseX >= McClocks.state.left * McClocks.state.size) && (mouseX < McClocks.state.right * McClocks.state.size) &&
                (mouseY >= McClocks.state.top * McClocks.state.size) && mouseY < (McClocks.state.bottom * McClocks.state.size) && !isResizeDragging)
            isMoveDragging = true;

        oldX = mouseX;
        oldY = mouseY;

        this.heightMarginTextField.mouseClicked(mouseX, mouseY, mouseButton);
        this.widthMarginTextField.mouseClicked(mouseX, mouseY, mouseButton);

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        isResizeDragging = false;
        isMoveDragging = false;

        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void onGuiClosed(){
        McClocks.isConfigGuiOpen = false;

        StateSaver.Save();
    }

    public void onSizeChangedCallback(float size){
        McClocks.state.size = size;
    }

    public void ResizeDrag(int mouseX, int mouseY){
        if(!McClocks.state.isEnabled)
            return;

        if(isResizeDragging){

            boolean isUsingBottomEdge = ((McClocks.state.bottom * McClocks.state.size) <= (mouseY + 5)) && ((McClocks.state.right * McClocks.state.size) >= (mouseX + 2));

            if(isUsingBottomEdge)
                McClocks.state.size = Math.max(((GuiSlider)this.buttonList.get(1)).getMinValue(), Math.min((McClocks.state.size + ((mouseY - (McClocks.state.bottom * McClocks.state.size)) / (float)McClocks.state.bottom)), ((GuiSlider)this.buttonList.get(1)).getMaxValue()));
            else
                McClocks.state.size = Math.max(((GuiSlider)this.buttonList.get(1)).getMinValue(), Math.min(McClocks.state.size + ((mouseX - (McClocks.state.right * McClocks.state.size)) / (float)McClocks.state.right), ((GuiSlider)this.buttonList.get(1)).getMaxValue()));

            ((GuiSlider)this.buttonList.get(1)).setSliderValue(McClocks.state.size);
        }
    }

    int oldX;
    int oldY;

    public void MoveDrag(int mouseX, int mouseY){
        if(!McClocks.state.isEnabled)
            return;

        if(isMoveDragging){
            McClocks.state.left = (int)((McClocks.state.left + (mouseX - oldX)) / McClocks.state.size);

            LogManager.getLogger("smth").info(((McClocks.state.left + (mouseX - oldX)) / McClocks.state.size) + " as int " + (int)((McClocks.state.left + (mouseX - oldX)) / McClocks.state.size)); //idk fix this ig here issue wtf ??? wanna commit die!!
            LogManager.getLogger("smth").info("X: " + McClocks.state.left + ", Y: " + McClocks.state.top);
            oldX = mouseX;
        }
    }
}
