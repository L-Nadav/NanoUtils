package com.kyozm.nanoutils.gui.widgets.buttons.settings;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.gui.widgets.buttons.NanoButton;
import com.kyozm.nanoutils.modules.gui.Theme;
import com.kyozm.nanoutils.settings.Setting;
import com.kyozm.nanoutils.utils.FontDrawer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.KeyBinding;

import java.awt.*;

public class KeyBindButton extends NanoButton {

    Color bg;
    Color fg;
    public Setting<KeyBinding> setting;

    public KeyBindButton(int x, int y, int widthIn, int heightIn, Setting<KeyBinding> s) {
        super(x, y, widthIn, heightIn);
        setting = s;
    }

    @Override
    public void onPress(int mouseButton) {
        NanoUtils.gui.tryBind(getWrapper());
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        boolean hover = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

        if (hover) {
            bg = Theme.buttonHoverBG.getVal();
        } else {
            bg = NanoUtils.gui.isBinding(getWrapper()) ? Theme.buttonEnabledBG.getVal() : Theme.buttonDisabledBG.getVal();
        }

        fg = Theme.buttonEnabledFG.getVal();

        Gui.drawRect(x, y, x+width, y+height, bg.getRGB());
        FontDrawer.drawString(setting.name, x+3, y+3, fg);
        FontDrawer.drawString(setting.getVal().getDisplayName(), x + width - FontDrawer.getStringWidth(setting.getVal().getDisplayName()), y+3, fg);


    }
}
