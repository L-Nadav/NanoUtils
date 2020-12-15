package com.kyozm.nanoutils.gui.widgets;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.gui.widgets.buttons.Button;
import com.kyozm.nanoutils.gui.widgets.buttons.ToggleButton;
import com.kyozm.nanoutils.modules.gui.Theme;
import com.kyozm.nanoutils.settings.Setting;
import com.kyozm.nanoutils.utils.ChromaSync;
import com.kyozm.nanoutils.utils.FontDrawer;
import com.kyozm.nanoutils.utils.MathUtil;
import com.kyozm.nanoutils.utils.NanoColor;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;

public class ColorPicker extends Widget {

    private SaturationBrightnessGrid saturationBrightnessGrid;
    private ColorSlider hueSlider;
    private ColorSlider alphaSlider;
    private Button toggleRGB;
    private Setting<NanoColor> set = null;

    public NanoColor nanoColor;

    public ColorPicker(int x, int y) {
        screenX = x + 1;
        screenY = y + 1;
        width = 100;
        height = 135;
        saturationBrightnessGrid = new SaturationBrightnessGrid(screenX, screenY, 100, 100);
        hueSlider = new ColorSlider(screenX - 1, screenY + 102, 102, 10, 10, true);
        alphaSlider = new ColorSlider(screenX - 1, screenY + 113, 102, 10, 100, false);
        toggleRGB = new Button(new ToggleButton(screenX - 1, screenY + height - 11, 50, 10, "RGB"));
    }

    public ColorPicker(int x, int y, Setting<NanoColor> set) {
        screenX = x + 1;
        this.set = set;
        screenY = y + 1;
        width = 100;
        height = 135;
        nanoColor = set.getVal();
        //setAllSubWidgets();
    }

    @Override
    public void render() {
        Gui.drawRect(screenX - 2, screenY - 2, screenX + width + 2, screenY + height, Theme.submenuBG.getVal().getRGB());
        Gui.drawRect(screenX - 1, screenY - 1, screenX + width + 1, screenY + 101, Color.LIGHT_GRAY.getRGB());
        saturationBrightnessGrid.currentHUE = ((ToggleButton) toggleRGB.button).toggled ? getHue(ChromaSync.current) : (float) hueSlider.milliPct / 1000f;
        alphaSlider.currentColor = saturationBrightnessGrid.currentColor;
        toggleRGB.render();
        saturationBrightnessGrid.render();
        hueSlider.render();
        alphaSlider.render();
        nanoColor = NanoColor.fromColor( saturationBrightnessGrid.currentColor );
        nanoColor = new NanoColor(nanoColor.getRed(), nanoColor.getGreen(), nanoColor.getBlue(), (int) MathUtil.scale(0, 1, 0, 255, ((float) alphaSlider.milliPct / 1000f)));
        nanoColor.isChroma = ((ToggleButton) toggleRGB.button).toggled;
        if (set != null)
            set.setVal(nanoColor);
        Gui.drawRect(screenX + 50, screenY + height - 11, screenX + width + 1, screenY + height - 1, nanoColor.getRGB());
        FontDrawer.drawString(String.format("#%02x%02x%02x", nanoColor.getRed(), nanoColor.getGreen(), nanoColor.getBlue()), screenX + 55, screenY + height - 8, getBrightness(nanoColor) < 0.5 ? Color.WHITE : Color.BLACK);
    }

    @Override
    public void setAllSubWidgets() {
        screenY += 1;
        screenX += 1;
        saturationBrightnessGrid = new SaturationBrightnessGrid(screenX, screenY, 100, 100);
        saturationBrightnessGrid.currentHUE = Math.round(MathUtil.scale(0, 1, 0, 100, getHue(nanoColor)));
        saturationBrightnessGrid.currentSaturation = Math.round(MathUtil.scale(0, 1, 0, 100, getSaturation(nanoColor)));
        saturationBrightnessGrid.currentBrightness = 100 - Math.round(MathUtil.scale(0, 1, 0, 100, getBrightness(nanoColor)));
        saturationBrightnessGrid.currentColor = nanoColor;
        hueSlider = new ColorSlider(screenX - 1, screenY + 102, 102, 10, (int) MathUtil.scale(0, 1, 0, 100, getHue(nanoColor)), true);
        alphaSlider = new ColorSlider(screenX - 1, screenY + 113, 102, 10, Math.round(MathUtil.scale(0, 255, 0, 100, nanoColor.getAlpha())), false);
        toggleRGB = new Button(new ToggleButton(screenX - 1, screenY + height - 11, 50, 10, "RGB"));
        ((ToggleButton) toggleRGB.button).toggled = set.getVal().isChroma;
    }

    public static float getHue(Color c) {
        float[] hsv = new float[3];
        Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsv);
        return hsv[0];
    }

    public static float getBrightness(Color c) {
        float[] hsv = new float[3];
        Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsv);
        return hsv[2];
    }

    public static float getSaturation(Color c) {
        float[] hsv = new float[3];
        Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsv);
        return hsv[1];
    }


    public void depthCalc() {
        ArrayList<Widget> depth = new ArrayList<>();
        depth.add(this);
        Widget p = this.parent;
        while (p != null) {
            depth.add(p);
            p = p.parent;
        }
        if (depth != NanoUtils.gui.depth)
            NanoUtils.gui.updateDepth(depth);
    }

}
