package com.kyozm.nanoutils.gui.widgets.buttons.settings;

import com.kyozm.nanoutils.gui.widgets.Slider;
import com.kyozm.nanoutils.modules.gui.Theme;
import com.kyozm.nanoutils.settings.Setting;
import com.kyozm.nanoutils.utils.FontDrawer;
import com.kyozm.nanoutils.utils.MathUtil;
import net.minecraft.client.gui.Gui;

public class FloatSlider extends Slider {

    Setting<Float> setting;

    public FloatSlider(int x, int y, int w, int h, Setting<Float> s) {
        super((int) (MathUtil.percent(s.minVal, s.maxVal, s.getVal()) * 1000f));
        this.screenX = x;
        this.screenY = y;
        this.width = w;
        this.height = h;
        this.setting = s;
    }

    @Override
    public void render() {
        super.render();
        bg = Theme.buttonEnabledBG.getVal();
        fg = Theme.buttonEnabledFG.getVal();
        Gui.drawRect(screenX, screenY, screenX + (int) ( (float) width * (float)getPct() / 100f), screenY + height, bg.getRGB());

        //lines
        if (Theme.drawSliderBorder.getVal()) {
            Gui.drawRect(screenX, screenY, screenX + width, screenY + 1, bg.getRGB());
            Gui.drawRect(screenX, screenY + height - 1, screenX + width, screenY + height, bg.getRGB());
            Gui.drawRect(screenX + width, screenY, screenX + width - 1, screenY + height, bg.getRGB());
            Gui.drawRect(screenX, screenY, screenX + 1, screenY + height, bg.getRGB());
        }
        FontDrawer.drawString(hovering ? String.format("%.2f", setting.getVal()) : setting.name, screenX+3, screenY+3, fg);
        float pct = MathUtil.percent(0, 1000, milliPct);
        if (setting.step == null)
            setting.setVal(MathUtil.fromPercent(setting.minVal, setting.maxVal, pct));
        else
            setting.setVal(MathUtil.round(MathUtil.fromPercent(setting.minVal, setting.maxVal, pct), setting.step));
    }
}
