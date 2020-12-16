package com.kyozm.nanoutils.gui.widgets.buttons;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.gui.widgets.Widget;
import com.kyozm.nanoutils.utils.InputUtils;
import org.lwjgl.input.Mouse;
import scala.Tuple4;

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
        Tuple4<Integer, Integer, Integer, Boolean> mouseData = InputUtils.getMouseData();
        button.drawButton(mc, mouseData._1(), mouseData._2(), 1f);
    }


}
