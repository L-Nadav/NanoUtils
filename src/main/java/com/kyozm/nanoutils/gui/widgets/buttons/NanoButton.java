package com.kyozm.nanoutils.gui.widgets.buttons;

import com.kyozm.nanoutils.gui.widgets.Widget;
import net.minecraft.client.gui.GuiButton;

public class NanoButton extends GuiButton {
    Button wrapper;

    public NanoButton(int x, int y, int widthIn, int heightIn) {
        super(0, x, y, widthIn, heightIn, "");
    }

    public void onPress(int mouseButton) {

    }

    public Widget getWrapper() {
        return wrapper;
    }
}
