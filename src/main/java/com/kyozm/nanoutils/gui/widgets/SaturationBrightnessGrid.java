package com.kyozm.nanoutils.gui.widgets;

import com.kyozm.nanoutils.utils.InputUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.opengl.GL11;
import scala.Tuple4;

import java.awt.*;

public class SaturationBrightnessGrid extends Widget {

    public float currentHUE = 0;
    public int currentSaturation = 100;
    public int currentBrightness = 100;
    public Color currentColor;

    public SaturationBrightnessGrid(int screenX, int screenY, int width, int height) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.width = width;
        this.height = height;
        this.currentColor = Color.getHSBColor(currentHUE, currentBrightness, currentSaturation);
    }


    @Override
    public void render() {
        Tuple4<Integer, Integer, Integer, Boolean> mouseData = InputUtils.getMouseData();
        if (mouseData._4() && intersects(mouseData._1(), mouseData._2())) {
            currentSaturation = (int) ((((float) (mouseData._1() - screenX)) / ((float) width)) * 100);
            currentBrightness = (int) ((((float) (mouseData._2() - screenY)) / ((float) height)) * 100);

        }

        currentColor = Color.getHSBColor(currentHUE, currentSaturation / 100f, 1f - (currentBrightness / 100f));
        GlStateManager.pushMatrix();
            GlStateManager.translate(screenX, screenY + height, 0.0);
            GL11.glRotated(270,0, 0, 1);
            GuiUtils.drawGradientRect(100, 0, 0, width, height, Color.WHITE.getRGB(), Color.getHSBColor(currentHUE, 1f, 1f).getRGB());
        GlStateManager.popMatrix();

        GuiUtils.drawGradientRect(100, screenX, screenY, width + screenX, height + screenY, 0x00000000, Color.BLACK.getRGB());

        if (currentSaturation + currentBrightness < 98) {
            Gui.drawRect(screenX + (int) ((float) width * (float) currentSaturation / 100f), screenY, screenX + (int) ((float) width * (float) currentSaturation / 100f) +1, screenY+height, Color.BLACK.getRGB());
            Gui.drawRect(screenX, screenY + (int) ((float) height * (float) currentBrightness / 100f), screenX + width, screenY + (int) ((float) height * (float) currentBrightness / 100f) +1, Color.BLACK.getRGB());
        } else {
            Gui.drawRect(screenX + (int) ((float) width * (float) currentSaturation / 100f), screenY, screenX + (int) ((float) width * (float) currentSaturation / 100f) +1, screenY+height, Color.WHITE.getRGB());
            Gui.drawRect(screenX, screenY + (int) ((float) height * (float) currentBrightness / 100f), screenX + width, screenY + (int) ((float) height * (float) currentBrightness / 100f) +1, Color.WHITE.getRGB());

        }

    }
}
