package com.kyozm.nanoutils.gui.widgets;

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
}
