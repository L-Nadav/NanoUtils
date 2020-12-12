package com.kyozm.nanoutils.gui.widgets.modules;

import com.kyozm.nanoutils.gui.widgets.Draggable;
import com.kyozm.nanoutils.modules.ModuleManager;
import com.kyozm.nanoutils.modules.render.MapPreview;
import com.kyozm.nanoutils.utils.ChromaSync;
import com.kyozm.nanoutils.utils.MapUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;

public class MapPreviewWidget extends Draggable {

    public MapPreviewWidget(int x, int y, int xx, int yy) {
        super(x, y, xx, yy);
        clearable = false;
    }

    @Override
    public void render() {
        ItemStack offhand = mc.player.inventory.offHandInventory.get(0);
        MapPreview mod = (MapPreview) ModuleManager.getModule(MapPreview.class).get();

        if (!mod.isEnabled()) {
            canDrag = false;
            return;
        } else {
            canDrag = true;
        }

        if (mod.offHandDisplay.getVal() && offhand.getItem() instanceof ItemMap) {
            if (mc.gameSettings.thirdPersonView == 0 && mod.offHandDisplayThirdPersonOnly.getVal())
                return;
            if (mod.offHandDisplayBorder.getVal()) {
                int borderColor = mod.offHandDisplayBorderRGB.getVal() ? ChromaSync.current.getRGB() : 0xaaffffff;
                Gui.drawRect(screenX - 1, screenY - 1, screenX + 64 + 1, screenY + 64 + 1, borderColor);
            }
            MapUtils.renderMapFromStack(offhand, screenX, screenY, 1f, MapPreview.cache.getVal());
            canDrag = true;
        }
    }
}
