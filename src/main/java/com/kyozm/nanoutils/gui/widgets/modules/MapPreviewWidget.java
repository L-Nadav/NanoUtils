package com.kyozm.nanoutils.gui.widgets.modules;

import com.kyozm.nanoutils.gui.widgets.Draggable;
import com.kyozm.nanoutils.modules.ModuleManager;
import com.kyozm.nanoutils.modules.render.MapPreview;
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
        super.render();
        ItemStack offhand = mc.player.inventory.offHandInventory.get(0);
        MapPreview mod = (MapPreview) ModuleManager.getModule(MapPreview.class).get();

        if (!mod.isEnabled()) {
            canDrag = false;
            return;
        } else {
            canDrag = true;
        }

        if (mod.offHandDisplay.getVal() && offhand.getItem() instanceof ItemMap) {

            int borderWidth = MapPreview.offHandDisplayBorderSize.getVal();
            if (mc.gameSettings.thirdPersonView == 0 && mod.offHandDisplayThirdPersonOnly.getVal())
                return;
            int borderColor = MapPreview.offHandDisplayBorderColor.getVal().getRGB();

            //if (MapPreview.offHandDisplayBorderColor.getVal().isChroma) {
            //    borderColor = Color.HSBtoRGB(ColorPicker.getHue(ChromaSync.current),
            //            ColorPicker.getSaturation(MapPreview.offHandDisplayBorderColor.getVal()),
            //            ColorPicker.getBrightness(MapPreview.offHandDisplayBorderColor.getVal()));
            //}
            this.width = (int) (64 * MapPreview.offHandDisplayScale.getVal());
            this.height = (int) (64 * MapPreview.offHandDisplayScale.getVal());
            Gui.drawRect(
                    screenX - borderWidth,
                    screenY - borderWidth,
                    (int) (screenX + (64 * MapPreview.offHandDisplayScale.getVal()) + borderWidth),
                    (int) (screenY + (64 * MapPreview.offHandDisplayScale.getVal())) + borderWidth, borderColor);
            MapUtils.renderMapFromStack(offhand, screenX, screenY, MapPreview.offHandDisplayScale.getVal(), MapPreview.cache.getVal());
            canDrag = true;
        }
    }
}
