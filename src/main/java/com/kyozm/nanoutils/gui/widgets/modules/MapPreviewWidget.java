package com.kyozm.nanoutils.gui.widgets.modules;

import com.kyozm.nanoutils.gui.widgets.Draggable;
import com.kyozm.nanoutils.modules.ModuleManager;
import com.kyozm.nanoutils.modules.render.MapPreview;
import com.kyozm.nanoutils.utils.InputUtils;
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
        ItemStack selectedMap = mc.player.inventory.offHandInventory.get(0);

        if (InputUtils.wasKeybindJustPressed(MapPreview.mapDisplaySetMap.getVal())) {
            MapPreview.displayStack = mc.player.inventory.getCurrentItem().getItem() instanceof ItemMap ? mc.player.inventory.getCurrentItem() : null;
        }

        if (!(MapPreview.mapDisplayAutoOffhand.getVal() && selectedMap.getItem() instanceof ItemMap)) {
            selectedMap = MapPreview.displayStack;
            if (selectedMap == null)
                return;
        }

        if (!ModuleManager.isActive(MapPreview.class) || !MapPreview.mapDisplay.getVal()) {
            canDrag = false;
            return;
        } else {
            canDrag = true;
        }

        if (MapPreview.mapDisplay.getVal() && selectedMap.getItem() instanceof ItemMap) {
            int borderWidth = MapPreview.mapDisplayBorderSize.getVal();
            if (mc.gameSettings.thirdPersonView == 0 && MapPreview.mapDisplayThirdPerson.getVal())
                return;
            int borderColor = MapPreview.mapDisplayBorderColor.getVal().getRGB();
            this.width = (int) (64 * MapPreview.mapDisplayScale.getVal());
            this.height = (int) (64 * MapPreview.mapDisplayScale.getVal());
            Gui.drawRect(
                    screenX - borderWidth,
                    screenY - borderWidth,
                    (int) (screenX + (64 * MapPreview.mapDisplayScale.getVal()) + borderWidth),
                    (int) (screenY + (64 * MapPreview.mapDisplayScale.getVal())) + borderWidth, borderColor);
            MapUtils.renderMapFromStack(selectedMap, screenX, screenY, MapPreview.mapDisplayScale.getVal(), MapPreview.cache.getVal());
            canDrag = true;
        }
    }
}
