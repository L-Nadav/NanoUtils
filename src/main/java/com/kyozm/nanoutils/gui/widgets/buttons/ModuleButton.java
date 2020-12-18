package com.kyozm.nanoutils.gui.widgets.buttons;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.gui.widgets.containers.SettingsList;
import com.kyozm.nanoutils.modules.Module;
import com.kyozm.nanoutils.modules.ModuleManager;
import com.kyozm.nanoutils.modules.gui.NanoGuiModule;
import com.kyozm.nanoutils.modules.gui.Theme;
import com.kyozm.nanoutils.utils.FontDrawer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class ModuleButton extends ToggleableSubmenuOpener {
    private Module mod;
    public boolean centered = false;

    public ModuleButton(int x, int y, int widthIn, int heightIn, Module mod) {
        super(x, y, widthIn, heightIn);
        this.mod = mod;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        super.drawButton(mc, mouseX, mouseY, partialTicks);
        toggled = mod.isEnabled();
        boolean hover = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

        if (hover) {
            bg = Theme.buttonHoverBG.getVal();
            if (NanoGuiModule.hoverOpen.getVal() && NanoUtils.gui.depth.stream().noneMatch(w -> w.parent == getWrapper()))
                onPress(1);
            if (((NanoGuiModule)ModuleManager.getModule(NanoGuiModule.class).get()).showModuleDescription.getVal())
                FontDrawer.drawString(mod.desc, 4, NanoUtils.sr.getScaledHeight() - mc.fontRenderer.FONT_HEIGHT - 4, new Color(0xFFFFFF));
        } else {
            bg = mod.isEnabled() ? Theme.buttonEnabledBG.getVal() : Theme.buttonDisabledBG.getVal();
        }

        if (getWrapper().getChildFromDepth().isPresent()) {
            bg = Theme.nestedSettingOpenMenu.getVal();
        }

        fg = mod.isEnabled() ? Theme.buttonEnabledFG.getVal() : Theme.buttonDisabledFG.getVal();

        Gui.drawRect(x, y, x+width, y+height, bg.getRGB());
        FontDrawer.drawString(mod.name, x+3, y+3, fg);
    }

    @Override
    public void toggle() {
        mod.toggle();
    }

    @Override
    public void openSubmenu() {
        SettingsList sList = SettingsList.fromModule(getWrapper(), mod.getClass());
        if (sList != null)
            NanoUtils.gui.queue.add(sList);
    }
}
