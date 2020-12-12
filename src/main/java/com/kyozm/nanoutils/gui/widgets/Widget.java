package com.kyozm.nanoutils.gui.widgets;

import net.minecraft.client.Minecraft;

public class Widget {

    protected static Minecraft mc = Minecraft.getMinecraft();

    public int screenX;
    public int screenY;
    public int width;
    public int height;
    public boolean clearable = true;
    public Widget parent = null;

    public void render() {

    }

    public boolean intersects(int mX, int mY) {
        return mX > screenX && mY > screenY && mX < screenX + width && mY < screenY + height;
    }

    public int width() {
        return width;
    }
}
