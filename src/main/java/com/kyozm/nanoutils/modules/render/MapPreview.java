package com.kyozm.nanoutils.modules.render;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.gui.widgets.modules.MapPreviewWidget;
import com.kyozm.nanoutils.modules.Module;
import com.kyozm.nanoutils.modules.ModuleCategory;
import com.kyozm.nanoutils.settings.NestedSetting;
import com.kyozm.nanoutils.settings.Setting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.MapData;
import org.lwjgl.input.Keyboard;


public class MapPreview extends Module {

    public MapPreview() {
        name = "Map Preview";
        category = ModuleCategory.RENDER;
        bind = Keyboard.KEY_NONE;
        desc = "Renders map data on tooltip and screen.";

        guiObject = new MapPreviewWidget( 100, 100, 65, 65 );
        NanoUtils.gui.queue.add(guiObject);

        registerSetting(MapPreview.class, tooltipDisplay);
        registerSetting(MapPreview.class, itemStackDisplay);

        offHandDisplay.register(offHandDisplayBorder);
        offHandDisplay.register(offHandDisplayBorderRGB);
        offHandDisplay.register(offHandDisplayThirdPersonOnly);
        registerSetting(MapPreview.class, offHandDisplay);

        registerSetting(MapPreview.class, cache);

    }

    public static Setting<Boolean> tooltipDisplay = new Setting<Boolean>()
            .setName("Tooltip Display")
            .setConfigName("MapPreviewTooltipDisplay")
            .setDefaultVal(false)
            .withType(Boolean.class);

    public static Setting<Boolean> itemStackDisplay = new Setting<Boolean>()
            .setName("Item Stack Display")
            .setConfigName("MapPreviewItemStackDisplay")
            .setDefaultVal(false)
            .withType(Boolean.class);

    public static Setting<Boolean> cache = new Setting<Boolean>()
            .setName("Cache Maps")
            .setConfigName("MapPreviewCache")
            .setDefaultVal(false)
            .withType(Boolean.class);

    public static NestedSetting offHandDisplay = new NestedSetting()
            .setName("Off Hand Display")
            .setConfigName("MapPreviewOffHandDisplay")
            .setDefaultVal(false)
            .withType(NestedSetting.class);

    public static Setting<Boolean> offHandDisplayThirdPersonOnly = new Setting<Boolean>()
            .setName("Third Person Only")
            .setConfigName("MapPreviewOffHandDisplayThirdPerson")
            .setDefaultVal(false)
            .withType(Boolean.class);

    public static Setting<Boolean> offHandDisplayBorder = new Setting<Boolean>()
            .setName("Border")
            .setConfigName("MapPreviewOffHandDisplayBorder")
            .setDefaultVal(false)
            .withType(Boolean.class);

    public static Setting<Boolean> offHandDisplayBorderRGB = new Setting<Boolean>()
            .setName("Border RGB")
            .setConfigName("MapPreviewOffHandDisplayBorderRGB")
            .setDefaultVal(false)
            .withType(Boolean.class);

    public MapPreviewWidget guiObject;

    public static int tooltipX;
    public static int tooltipY;
    public static boolean activeTooltip = false;
    public static ItemStack tooltipStack;


}
