package com.kyozm.nanoutils.gui.widgets.buttons.settings;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.gui.NanoGui;
import com.kyozm.nanoutils.gui.widgets.ColorPicker;
import com.kyozm.nanoutils.gui.widgets.Widget;
import com.kyozm.nanoutils.gui.widgets.buttons.SubmenuOpener;
import com.kyozm.nanoutils.gui.widgets.containers.Stacked;
import com.kyozm.nanoutils.modules.gui.NanoGuiModule;
import com.kyozm.nanoutils.modules.gui.Theme;
import com.kyozm.nanoutils.settings.Setting;
import com.kyozm.nanoutils.utils.FontDrawer;
import com.kyozm.nanoutils.utils.NanoColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ColorSetting extends SubmenuOpener {

    Setting<NanoColor> setting;
    public Color bg;
    public Color fg;

    protected Color bgDisabled = Theme.buttonDisabledBG.getVal();
    protected Color fgEnabled = Theme.buttonEnabledFG.getVal();

    public ColorSetting(int x, int y, int widthIn, int heightIn, Setting<NanoColor> setting) {
        super(x, y, widthIn, heightIn);
        this.setting = setting;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        //super.drawButton(mc, mouseX, mouseY, partialTicks);
        boolean hover = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

        if (hover) {
            if (NanoGuiModule.hoverOpen.getVal() && !getWrapper().getChildFromDepth().isPresent())
                onPress(1);
        }
        bg = Theme.buttonDisabledBG.getVal();

        fg = Theme.buttonEnabledFG.getVal();

        if (getWrapper().getChildFromDepth().isPresent()) {
            bg = Theme.nestedSettingOpenMenu.getVal();
        }


        Gui.drawRect(x, y, x+width, y+height, bg.getRGB());
        FontDrawer.drawString(setting.name, x+3, y+3, fg);
        Gui.drawRect(x + width - (height - 1), y + 1, x + width - 1, y + height - 1, setting.getVal().getRGB());
    }

    @Override
    public void openSubmenu() {
        Stacked colorPicker = new Stacked(getWrapper(), Arrays.asList(new ColorPicker(0, 0, setting)));
        NanoUtils.gui.queue.add(colorPicker);
    }

    @Override
    public void onPress(int mouseButton) {
        openSubmenu();
    }

    private void depthCalc() {
        ArrayList<Widget> depth = new ArrayList<>();
        depth.add(getWrapper());
        Widget p = getWrapper().parent;
        while (p != null) {
            depth.add(p);
            p = p.parent;
        }
        if (depth != NanoUtils.gui.depth)
            NanoUtils.gui.updateDepth(depth);
    }
}
