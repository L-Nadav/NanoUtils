package com.kyozm.nanoutils.modules.render;

import com.kyozm.nanoutils.modules.Module;
import com.kyozm.nanoutils.modules.ModuleCategory;
import org.lwjgl.input.Keyboard;

public class Earthquake extends Module {

    public Earthquake() {
        name = "Earthquake";
        bind = Keyboard.KEY_NONE;
        category = ModuleCategory.RENDER;
        desc = "haha screen go brrrrrr";
    }

    @Override
    public void onRender() {
        if (!enabled)
            return;
        mc.player.cameraYaw += 0.1f;
    }

}
