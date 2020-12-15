package com.kyozm.nanoutils.gui.widgets.buttons;

import com.kyozm.nanoutils.modules.gui.Theme;
import com.kyozm.nanoutils.utils.FontDrawer;
import com.kyozm.nanoutils.utils.InputUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;
import scala.Tuple4;

import java.awt.*;

public class ToggleButton extends NanoButton {

    public boolean toggled = false;
    public Color bg;
    public Color fg;

    int wasPressedT = 0;

    String text;

    public ToggleButton(int x, int y, int widthIn, int heightIn, String text) {
        super(x, y, widthIn, heightIn);
        this.text = text;
    }

    @Override
    public void onPress(int mouseButton) {
        if (mouseButton == 0) {
            toggled = !toggled;
            wasPressedT = 30;
        }
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        boolean hover = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        if (wasPressedT > 0) {
            wasPressedT -= 1;
        }
        if (hover) {
            bg = Theme.buttonHoverBG.getVal();
            Tuple4<Integer, Integer, Integer, Boolean> mouseData = InputUtils.getMouseData();
            if(!mouseData._4() && mouseData._3() != -1 && wasPressedT == 0)
                onPress(mouseData._3());
        } else {
            bg = toggled ? Theme.buttonEnabledBG.getVal() : Theme.buttonDisabledBG.getVal();
        }

        fg = toggled ? Theme.buttonEnabledFG.getVal() : Theme.buttonDisabledFG.getVal();

        Gui.drawRect(x, y, x+width, y+height, bg.getRGB());
        FontDrawer.drawString(text, x+(this.width / 2 - (FontDrawer.getStringWidth(text) / 2)), y+3, fg);
    }
}
