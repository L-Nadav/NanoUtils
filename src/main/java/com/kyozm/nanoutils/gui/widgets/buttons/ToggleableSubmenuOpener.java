package com.kyozm.nanoutils.gui.widgets.buttons;

import java.awt.*;

public class ToggleableSubmenuOpener extends SubmenuOpener {

    public Color bg;
    public Color fg;

    protected Color bgEnabled = new Color(0xAA737373, true);
    protected Color bgDisabled = new Color(0xAA282828, true);
    protected Color bgHover = new Color(0xAA565656, true);

    protected Color fgEnabled = new Color(0xFFFFFF);
    protected Color fgDisabled = new Color(0x969696);

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

    public void openSubmenu() {

    }
}
