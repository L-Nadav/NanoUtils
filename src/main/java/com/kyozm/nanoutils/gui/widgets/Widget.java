package com.kyozm.nanoutils.gui.widgets;

import com.kyozm.nanoutils.NanoUtils;
import net.minecraft.client.Minecraft;

import java.util.Optional;

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
        return mX >= screenX && mY >= screenY && mX <= screenX + width && mY <= screenY + height;
    }

    public int width() {
        return width;
    }

    public void setAllSubWidgets() {

    }

    public Optional<Widget> getChildFromDepth() {
        return NanoUtils.gui.depth.stream().filter(w -> w.parent == this).findAny();
    }
}
