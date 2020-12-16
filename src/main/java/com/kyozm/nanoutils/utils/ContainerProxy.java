package com.kyozm.nanoutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.util.text.TextComponentString;

import java.lang.reflect.Method;

public class ContainerProxy extends GuiContainer {
    public ContainerProxy(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }

    public static void drawSlot(Slot s) throws Exception {
        GuiInventory container = new GuiInventory(Minecraft.getMinecraft().player);
        container.drawScreen(s.xPos, s.yPos, 0f);
    }
}
