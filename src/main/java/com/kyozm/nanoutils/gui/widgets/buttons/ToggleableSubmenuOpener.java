package com.kyozm.nanoutils.gui.widgets.buttons;

import com.kyozm.nanoutils.gui.widgets.containers.Submenu;
import com.kyozm.nanoutils.modules.gui.Theme;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class ToggleableSubmenuOpener extends SubmenuOpener {

    public Color bg;
    public Color fg;

    protected Color bgEnabled = Theme.buttonEnabledBG.getVal();
    protected Color bgDisabled = Theme.buttonDisabledBG.getVal();
    protected Color bgHover = Theme.buttonHoverBG.getVal();

    protected Color fgEnabled = Theme.buttonEnabledFG.getVal();
    protected Color fgDisabled = Theme.buttonDisabledFG.getVal();

    public boolean toggled = false;

    public ToggleableSubmenuOpener(int x, int y, int widthIn, int heightIn) {
        super(x, y, widthIn, heightIn);
    }

    @Override
    public void onPress(int mouseButton) {
        if (mouseButton == 0) {
            toggle();
        } else if (mouseButton == 1) {
            openSubmenu();
        }
    }

    public void toggle() {
        toggled = !toggled;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        boolean hover = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

        if (hover) {
            ((Submenu) getWrapper().parent).depthCalc();
        }
    }
}
