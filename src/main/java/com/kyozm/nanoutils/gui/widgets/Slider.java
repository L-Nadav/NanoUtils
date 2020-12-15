package com.kyozm.nanoutils.gui.widgets;

import com.kyozm.nanoutils.modules.gui.Theme;
import com.kyozm.nanoutils.utils.InputUtils;
import scala.Tuple4;

import java.awt.*;

public class Slider extends Widget {

    public Color bg;
    public Color fg;

    protected Color bgEnabled = Theme.buttonEnabledBG.getVal();
    protected Color fgEnabled = Theme.buttonEnabledFG.getVal();

    public boolean hovering = false;
    public int milliPct;

    public Slider(int milliPct) {
        this.milliPct = milliPct;
    }

    public int getPct() {
        return milliPct / 10;
    }

    @Override
    public void render() {
        Tuple4<Integer, Integer, Integer, Boolean> mouseData = InputUtils.getMouseData();
        hovering = intersects(mouseData._1(), mouseData._2());
        if (mouseData._4() && hovering) {
            milliPct = (int) ((((float) (mouseData._1() - screenX)) / ((float) width)) * 1000);
        }
    }
}
