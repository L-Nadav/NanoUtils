package com.kyozm.nanoutils.gui.widgets;

import com.kyozm.nanoutils.NanoUtils;

public class Draggable  extends Widget {
    public boolean canDrag;
    public boolean dragging;
    public int dragX;
    public int dragY;

    public Draggable(int x, int y, int xx, int yy) {
        this.screenX = x;
        this.screenY = y;
        this.width = xx;
        this.height = yy;
        canDrag = false;
        dragging = false;
    }

    public void setPos(int x, int y) {
        this.screenX = x;
        this.screenY = y;
    }

    @Override
    public void render() {
        if (screenX > NanoUtils.sr.getScaledWidth()) {
            setPos(screenX  - width, screenY);
        }

        if (screenY > NanoUtils.sr.getScaledHeight()) {
            setPos(screenX, screenY - height);
        }
    }
}
