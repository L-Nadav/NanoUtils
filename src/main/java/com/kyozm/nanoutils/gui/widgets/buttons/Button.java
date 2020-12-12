package com.kyozm.nanoutils.gui.widgets.buttons;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.gui.widgets.Widget;
import org.lwjgl.input.Mouse;

public class Button extends Widget {
    public NanoButton button;

    public Button(NanoButton b) {
        button = b;
        screenX = b.x;
        screenY = b.y;
        width = b.width;
        height = b.height;
        b.wrapper = this;
    }

    @Override
    public void render() {
        button.x = screenX;
        button.y = screenY;
        button.width = width;
        button.height = height;
        button.drawButton(mc, Mouse.getEventX() / NanoUtils.sr.getScaleFactor(), (mc.displayHeight - Mouse.getEventY()) / NanoUtils.sr.getScaleFactor(), 1f);
    }


}
