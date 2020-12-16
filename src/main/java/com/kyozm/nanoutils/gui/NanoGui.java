package com.kyozm.nanoutils.gui;

import com.kyozm.nanoutils.NanoUtils;
import com.kyozm.nanoutils.gui.widgets.Draggable;
import com.kyozm.nanoutils.gui.widgets.Widget;
import com.kyozm.nanoutils.gui.widgets.buttons.Button;
import com.kyozm.nanoutils.gui.widgets.buttons.NanoButton;
import com.kyozm.nanoutils.gui.widgets.buttons.TopBarButton;
import com.kyozm.nanoutils.gui.widgets.buttons.settings.KeyBindButton;
import com.kyozm.nanoutils.gui.widgets.containers.Submenu;
import com.kyozm.nanoutils.modules.ModuleCategory;
import com.kyozm.nanoutils.modules.gui.Theme;
import com.kyozm.nanoutils.settings.Setting;
import com.kyozm.nanoutils.utils.FontDrawer;
import com.kyozm.nanoutils.utils.InputUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NanoGui extends GuiScreen {
    public ArrayList<Widget> widgets = new ArrayList<>();
    public ArrayList<Widget> queue = new ArrayList<>();
    public ArrayList<Widget> delQueue = new ArrayList<>();
    public ArrayList<Widget> depth = new ArrayList<>();

    private Widget keybind = null;

    public void drawGradient(int left, int top, int bottom, int right, int startColor, int endColor) {
        drawGradientRect(left, top, right, bottom, startColor, endColor);
    }

    public void drawTooltip(String s, int x, int y) {
        GuiUtils.drawHoveringText(Arrays.asList(s), x -3 , y + 4, NanoUtils.sr.getScaledWidth(), NanoUtils.sr.getScaledHeight(), 500, FontDrawer.mcFont);
    }

    @Override
    public void initGui() {
        int horizontalPos = 0;
        for (ModuleCategory category : ModuleCategory.values()) {
            TopBarButton topBarButton = new TopBarButton(horizontalPos * 2, 0, FontDrawer.getStringWidth(category.toString()) + 8, 12, category);
            Button bWidget = new Button(topBarButton);
            queue.add(bWidget);
            horizontalPos += (FontDrawer.getStringWidth(category.toString()));
        }
    }


    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.keybind != null) {
            Setting<KeyBinding> binding = ((KeyBindButton)((Button)keybind).button).setting;

            if (keyCode == Keyboard.KEY_ESCAPE) //unset
                binding.getVal().setKeyModifierAndCode(KeyModifier.NONE, Keyboard.KEY_NONE);
            else if (keyCode != 0)
                binding.getVal().setKeyModifierAndCode(KeyModifier.getActiveModifier(), keyCode);
            else if (typedChar > 0)
                binding.getVal().setKeyModifierAndCode(KeyModifier.getActiveModifier(), typedChar + 256);

            if (!KeyModifier.isKeyCodeModifier(keyCode))
                this.keybind = null;
            KeyBinding.resetKeyBindingArrayAndHash();
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        widgets.stream().filter(w -> Draggable.class.isAssignableFrom(w.getClass())).forEach(o -> {
            if (((Draggable) o).dragging) {
                int xMove = mouseX - ((Draggable) o).dragX;
                int yMove = mouseY - ((Draggable) o).dragY;

                ((Draggable) o).setPos(xMove, yMove);
            }
        });

        ScaledResolution scale = NanoUtils.sr;

        Gui.drawRect(0, 0, scale.getScaledWidth(), scale.getScaledHeight(), Theme.guiBG.getVal().getRGB());

        //Top Bar BG
        Gui.drawRect(0, 0, scale.getScaledWidth(), 12, Theme.topbarBG.getVal().getRGB());

        for (Widget widget : widgets)
            widget.render();

        updateQueues();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) {
            widgets.stream().filter(w -> Draggable.class.isAssignableFrom(w.getClass())).forEach(o -> ((Draggable) o).dragging = false);
            InputUtils.leftHeld = false;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        Optional<Widget> clicked =  widgets.stream().filter(o -> o.intersects(mouseX, mouseY) && Draggable.class.isAssignableFrom(o.getClass())).findAny();
        clicked.ifPresent(w -> {
            Draggable widget = (Draggable) w;
            if (mouseButton == 0) {
                if (widget.canDrag) {
                    widget.dragging = true;

                    widget.dragX = mouseX - widget.screenX;
                    widget.dragY = mouseY - widget.screenY;
                }
            }
        });

        if (mouseButton == 0) {
            InputUtils.leftHeld = true;
        }

        for (GuiButton button : this.buttonList) {
            if (button.mousePressed(this.mc, mouseX, mouseY)) {
                actionPerformed(button);
            }
        }

        widgets.stream().filter(w -> Submenu.class.isAssignableFrom(w.getClass())).forEach(w -> {
            for (Widget widget : ((Submenu) w).plainElements) {
                if (Button.class.isAssignableFrom(widget.getClass())) {
                    if (((Button) widget).button.mousePressed(mc, mouseX, mouseY)) {
                        actionPerformed(((Button) widget).button);
                    }
                }
            }
        });

        widgets.stream().filter(w -> Button.class.isAssignableFrom(w.getClass())).forEach(w -> {
            if (((Button) w).button.mousePressed(mc, mouseX, mouseY)) {
                actionPerformed(((Button) w).button);
            }
        });

        updateQueues();
    }

    @Override
    public void onGuiClosed() {
        List<Widget> clear = widgets.stream()
                .filter(w -> w.clearable)
                .collect(Collectors.toList());
        widgets.removeAll(clear);
        super.onGuiClosed();
        keybind = null;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button instanceof NanoButton) {
            ((NanoButton) button).onPress(Mouse.getEventButton());
        }
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    public void updateDepth(ArrayList<Widget> depth) {
        this.depth = depth;
        delQueue.addAll(
                widgets.stream()
                        .filter(w -> Submenu.class.isAssignableFrom(w.getClass()) && !depth.contains(w))
                        .collect(Collectors.toList())
        );

        widgets.stream()
                .filter(b -> Button.class.isAssignableFrom(b.getClass()))
                .filter(b -> !depth.contains(b))
                .filter(b -> TopBarButton.class.isAssignableFrom(((Button) b).button.getClass()))
                .forEach(b -> ((TopBarButton)((Button) b).button).disable());
    }

    public void updateQueues() {
        widgets.addAll(queue);
        widgets.removeAll(delQueue);
        queue.clear();
        delQueue.clear();
    }

    public void tryBind(Widget wrapper) {
        keybind = wrapper;
    }

    public boolean isBinding(Widget wrapper) {
        return keybind == wrapper;
    }
}
