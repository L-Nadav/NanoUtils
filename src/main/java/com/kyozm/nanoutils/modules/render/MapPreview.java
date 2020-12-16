package com.kyozm.nanoutils.modules.render;

import com.kyozm.nanoutils.gui.widgets.modules.MapPreviewWidget;
import com.kyozm.nanoutils.modules.Module;
import com.kyozm.nanoutils.modules.ModuleCategory;
import com.kyozm.nanoutils.settings.NestedSetting;
import com.kyozm.nanoutils.settings.Setting;
import com.kyozm.nanoutils.utils.ChromaSync;
import com.kyozm.nanoutils.utils.FontDrawer;
import com.kyozm.nanoutils.utils.NanoColor;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

import java.awt.*;


public class MapPreview extends Module {

    public MapPreview() {
        name = "Map Preview";
        category = ModuleCategory.RENDER;
        bind = Keyboard.KEY_NONE;
        desc = "Renders map data on tooltip and screen.";

        saveablePositions.put("OffhandDisplay", new MapPreviewWidget( 100, 100, 65, 65 ));

        tooltipDisplay.register(tooltipBorder);
        tooltipDisplay.register(tooltipScale);
        tooltipDisplay.register(drawStackName);
        registerSetting(MapPreview.class, tooltipDisplay);

        itemStackDisplay.register(drawStackQuantity);
        itemStackDisplay.register(revealItemOnHover);
        itemStackDisplay.register(hideStackDisplay);
        registerSetting(MapPreview.class, itemStackDisplay);

        offHandDisplay.register(offHandDisplayBorderColor);
        offHandDisplay.register(offHandDisplayThirdPersonOnly);
        offHandDisplay.register(offHandDisplayScale);
        offHandDisplay.register(offHandDisplayBorderSize);
        registerSetting(MapPreview.class, offHandDisplay);

        registerSetting(MapPreview.class, cache);

        ChromaSync.updateables.add(offHandDisplayBorderColor);
        ChromaSync.updateables.add(tooltipBorder);
    }

    public static NestedSetting tooltipDisplay = new NestedSetting()
            .setName("Tooltip Display")
            .setConfigName("MapPreviewTooltipDisplay")
            .setDefaultVal(false)
            .withType(NestedSetting.class);

    public static NestedSetting itemStackDisplay = new NestedSetting()
            .setName("Item Stack Display")
            .setConfigName("MapPreviewItemStackDisplay")
            .setDefaultVal(false)
            .withType(NestedSetting.class);

    public static Setting<Boolean> cache = new Setting<Boolean>()
            .setName("Cache Maps")
            .setConfigName("MapPreviewCache")
            .setDefaultVal(false)
            .withType(Boolean.class);

    public static NestedSetting offHandDisplay = new NestedSetting()
            .setName("Off Hand Display")
            .setConfigName("MapPreviewOffHandDisplay")
            .setExtraWidth(10)
            .setDefaultVal(false)
            .withType(NestedSetting.class);

    public static Setting<Boolean> offHandDisplayThirdPersonOnly = new Setting<Boolean>()
            .setName("Third Person Only")
            .setConfigName("MapPreviewOffHandDisplayThirdPerson")
            .setDefaultVal(false)
            .withType(Boolean.class);

    public static Setting<NanoColor> offHandDisplayBorderColor = new Setting<NanoColor>()
            .setName("Border")
            .setConfigName("MapPreviewOffHandDisplayBorderColor")
            .setDefaultVal(NanoColor.fromColor(Color.WHITE))
            .withType(NanoColor.class);

    public static Setting<Float> offHandDisplayScale = new Setting<Float>()
            .setName("Scale")
            .setConfigName("MapPreviewOffHandDisplayScale")
            .setDefaultVal(1f)
            .setMinVal(0.1f)
            .setMaxVal(6f)
            .withStep(0.05f)
            .setExtraWidth(80)
            .withType(Float.class);

    public static Setting<Integer> offHandDisplayBorderSize = new Setting<Integer>()
            .setName("Border Size")
            .setConfigName("MapPreviewOffHandDisplayBorderSize")
            .setDefaultVal(2)
            .setMinVal(0)
            .setMaxVal(10)
            .withStep(1)
            .withType(Integer.class);

    public static Setting<Boolean> drawStackQuantity = new Setting<Boolean>()
            .setName("Draw Stack Quantity")
            .setConfigName("MapPreviewStackQuantity")
            .setDefaultVal(false)
            .withType(Boolean.class);

    public static Setting<Boolean> revealItemOnHover = new Setting<Boolean>()
            .setName("Reveal on Hover")
            .setConfigName("MapPreviewStackHover")
            .setDefaultVal(false)
            .withType(Boolean.class);

    public static Setting<NanoColor> tooltipBorder = new Setting<NanoColor>()
            .setName("Border")
            .setConfigName("MapPreviewTooltipBorderColor")
            .setDefaultVal(NanoColor.fromColor(Color.WHITE))
            .withType(NanoColor.class);

    public static Setting<Boolean> drawStackName = new Setting<Boolean>()
            .setName("Draw Stack Name")
            .setConfigName("MapPreviewTooltipName")
            .setDefaultVal(true)
            .withType(Boolean.class);

    public static Setting<Float> tooltipScale = new Setting<Float>()
            .setName("Scale")
            .setConfigName("MapPreviewTooltipScale")
            .setDefaultVal(1f)
            .setMinVal(0.1f)
            .setMaxVal(6f)
            .withStep(0.05f)
            .setExtraWidth(80)
            .withType(Float.class);

    public static Setting<KeyBinding> hideStackDisplay = new Setting<KeyBinding>()
            .setName("Hide Stack Display")
            .setConfigName("MapPreviewStackHide")
            .setDefaultVal(new KeyBinding("Hide Stack Display", Keyboard.KEY_NONE, "NanoUtils"))
            .setExtraWidth(50)
            .withType(KeyBinding.class);

    public static int tooltipX;
    public static int tooltipY;
    public static boolean activeTooltip = false;
    public static ItemStack tooltipStack;

}
