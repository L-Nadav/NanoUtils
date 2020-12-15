package com.kyozm.nanoutils.gui.widgets;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.utils.ChromaSync;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ColorSlider extends Slider {

    private static final ResourceLocation HUE_SLIDER_TEXTURE = new ResourceLocation(NanoUtils.MODID, "textures/hue.png");
    private static final ResourceLocation TRANSP_SLIDER_TEXTURE = new ResourceLocation(NanoUtils.MODID, "textures/transp_slider.png");
    public boolean isHue;
    public Color currentColor;

    public ColorSlider(int x, int y, int w, int h, int def, boolean isHue) {
        super(def * 10);
        this.screenX = x;
        this.screenY = y;
        this.width = w;
        this.height = h;
        this.isHue = isHue;
    }

    @Override
    public void render() {
        super.render();
        GlStateManager.color(1f, 1f, 1f, 1f);
        if (isHue) {
            mc.getTextureManager().bindTexture(HUE_SLIDER_TEXTURE);
            Gui.drawModalRectWithCustomSizedTexture(screenX, screenY, 0, 0, width, height, 100, 10);
        } else {
            GlStateManager.pushMatrix();
                GlStateManager.translate(screenX, screenY + height, 0.0);
                GL11.glRotated(270,0, 0, 1);
                mc.getTextureManager().bindTexture(TRANSP_SLIDER_TEXTURE);
                Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, height, width, 100, 10);
                GuiUtils.drawGradientRect(100, 0, height, height, width, currentColor.getRGB() & 0x00FFFFFF, (currentColor.getRGB() | 0xff000000));
            GlStateManager.popMatrix();
        }
        Gui.drawRect(screenX + (int) ((float) width * (float) getPct() / 100f), screenY, screenX + (int) ((float) width * (float) getPct() / 100f) +1, screenY+height, Color.BLACK.getRGB());
    }
}
