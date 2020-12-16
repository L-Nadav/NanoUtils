package com.kyozm.nanoutils.gui.widgets.containers;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.gui.widgets.Widget;
import com.kyozm.nanoutils.gui.widgets.buttons.Button;
import com.kyozm.nanoutils.gui.widgets.buttons.TopBarButton;
import com.kyozm.nanoutils.modules.gui.NanoGuiModule;
import com.kyozm.nanoutils.modules.gui.Theme;
import com.kyozm.nanoutils.utils.NanoColor;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;
import java.util.List;

public class Submenu extends Widget {

    public List<Widget> plainElements = new ArrayList<>();
    public NanoColor bg = Theme.submenuBG.getVal();

    public Submenu(Widget parent) {
        this.parent = parent;
        setPositionBasedOnParent();
        depthCalc();
    }

    public void depthCalc() {
        ArrayList<Widget> depth = new ArrayList<>();
        depth.add(this);
        Widget p = parent;
        while (p != null) {
            depth.add(p);
            p = p.parent;
        }
        if (depth != NanoUtils.gui.depth)
            NanoUtils.gui.updateDepth(depth);
    }

    public void setPositionBasedOnParent() {
        this.screenX = parent.screenX + parent.width + 1;
        this.screenY = parent.screenY;

        if (Button.class.isAssignableFrom(parent.getClass())) {
            if (TopBarButton.class.isAssignableFrom(((Button) parent).button.getClass())) {
                this.screenY += 13;
                this.screenX = parent.screenX + 1;
            }
        }
    }

    public void drawBackdrop() {
        bg = Theme.submenuBG.getVal();
        Gui.drawRect(screenX - 1, screenY - 1, screenX + width + 1, screenY + height, bg.getRGB());
    }

    @Override
    public void render() {
        drawBackdrop();
    }
}
