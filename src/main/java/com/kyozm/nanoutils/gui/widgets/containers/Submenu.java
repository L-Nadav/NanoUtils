package com.kyozm.nanoutils.gui.widgets.containers;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.gui.widgets.Widget;
import com.kyozm.nanoutils.gui.widgets.buttons.Button;
import com.kyozm.nanoutils.gui.widgets.buttons.TopBarButton;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Submenu extends Widget {

    public List<Widget> plainElements = new ArrayList<>();
    public Color bg = new Color(0x000000);

    public Submenu(Widget parent) {
        this.parent = parent;
        setPositionBasedOnParent();
        depthCalc();
    }

    private void depthCalc() {
        ArrayList<Widget> depth = new ArrayList<>();
        depth.add(this);
        Widget p = parent;
        while (p != null) {
            depth.add(p);
            p = p.parent;
        }
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
        Gui.drawRect(screenX - 1, screenY - 1, screenX + width + 1, screenY + height, bg.getRGB());
    }

    @Override
    public void render() {
        drawBackdrop();
    }
}
