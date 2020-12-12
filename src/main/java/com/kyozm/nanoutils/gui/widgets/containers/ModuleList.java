package com.kyozm.nanoutils.gui.widgets.containers;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.gui.widgets.Widget;
import com.kyozm.nanoutils.gui.widgets.buttons.Button;
import com.kyozm.nanoutils.gui.widgets.buttons.ModuleButton;
import com.kyozm.nanoutils.modules.Module;
import com.kyozm.nanoutils.modules.ModuleCategory;
import com.kyozm.nanoutils.modules.ModuleManager;
import com.kyozm.nanoutils.utils.FontDrawer;

import java.util.List;
import java.util.stream.Collectors;

public class ModuleList extends Stacked {

    public ModuleList(Widget parent, List<Widget> widgets) {
        super(parent, widgets);
    }

    public static ModuleList fromCategory(Widget parent, ModuleCategory category) {
        List<Module> mods =  ModuleManager.findByCategory(category);
        List<Widget> buttons = mods.stream().map(module -> new Button(new ModuleButton(0, 0, FontDrawer.getStringWidth(module.name), 10, module))).collect(Collectors.toList());
        return new ModuleList(parent, buttons);
    }

}
