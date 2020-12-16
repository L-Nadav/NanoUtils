package com.kyozm.nanoutils.gui.widgets.buttons.settings;

import com.kyozm.nanoutils.gui.widgets.buttons.ToggleableSubmenuOpener;
import com.kyozm.nanoutils.modules.gui.Theme;
import com.kyozm.nanoutils.settings.Setting;
import com.kyozm.nanoutils.utils.FontDrawer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class CheckButton extends ToggleableSubmenuOpener {
    private Setting<Boolean> setting;
    public boolean centered = false;

    public CheckButton(int x, int y, int widthIn, int heightIn, Setting<Boolean> setting) {
        super(x, y, widthIn, heightIn);
        this.setting = setting;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        super.drawButton(mc, mouseX, mouseY, partialTicks);
        toggled = setting.getVal();
        boolean hover = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

        if (hover) {
            bg = Theme.buttonHoverBG.getVal();
        } else {
            bg = setting.getVal() ? Theme.buttonEnabledBG.getVal() : Theme.buttonDisabledBG.getVal();
        }

        fg = setting.getVal() ? Theme.buttonEnabledFG.getVal() : Theme.buttonDisabledFG.getVal();

        Gui.drawRect(x, y, x+width, y+height, bg.getRGB());
        FontDrawer.drawString(setting.name, x+3, y+3, fg);
    }

    @Override
    public void toggle() {
        setting.setVal(!setting.getVal());
    }

    @Override
    public void openSubmenu() {

    }
}
