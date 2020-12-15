package com.kyozm.nanoutils.gui.widgets.buttons;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.gui.NanoGui;
import com.kyozm.nanoutils.gui.widgets.Widget;
import com.kyozm.nanoutils.gui.widgets.containers.ModuleList;
import com.kyozm.nanoutils.modules.ModuleCategory;
import com.kyozm.nanoutils.modules.gui.NanoGuiModule;
import com.kyozm.nanoutils.modules.gui.Theme;
import com.kyozm.nanoutils.utils.FontDrawer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TopBarButton extends ToggleableSubmenuOpener {

    ModuleCategory category;

    public TopBarButton(int x, int y, int widthIn, int heightIn, ModuleCategory category) {
        super(x, y, widthIn, heightIn);
        this.category = category;
        bgDisabled = new Color(0x00000000, true);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        //super.drawButton(mc, mouseX, mouseY, partialTicks);

        boolean hover = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

        bg = toggled ? Theme.topBarButtonEnabled.getVal() : new Color(0x00000000, true);
        if (hover) {
            if (NanoGuiModule.hoverOpen.getVal())
                enable();
            bg = Theme.topBarButtonHover.getVal();
        }


        fg = Theme.topBarButtonFG.getVal();


        Gui.drawRect(x, y, x+width, y+height, bg.getRGB());
        FontDrawer.drawString(category.name(), x+4, y+4, fg);
    }

    private void enable() {
        if (!toggled) {
            ModuleList mList = ModuleList.fromCategory(this.wrapper, category);
            NanoUtils.gui.queue.add(mList);
            toggled = true;
        }
    }

    @Override
    public void onPress(int mouseButton) {
        toggle();
        if (toggled) {
            ModuleList mList = ModuleList.fromCategory(this.wrapper, category);
            NanoUtils.gui.queue.add(mList);
        } else {
            NanoUtils.gui.updateDepth(new ArrayList<>());
        }
    }

    public void disable() {
        toggled = false;
    }
}
