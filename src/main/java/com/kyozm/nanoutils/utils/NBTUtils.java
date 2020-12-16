package com.kyozm.nanoutils.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;

public class NBTUtils {

    public static NBTTagCompound getBlockEntityTag(ItemStack stack) {
        NBTTagCompound tags = stack.getTagCompound();
        if (tags != null && tags.hasKey("BlockEntityTag", 10)) {
            return tags.getCompoundTag("BlockEntityTag");
        }
        return null;
    }

    public static NonNullList<ItemStack> getShulkerItems(ItemStack stack) {
        NBTTagCompound blockEntity = getBlockEntityTag(stack);
        if (blockEntity == null || !blockEntity.hasKey("Items", 9)) return null;
        return loadFromTag(blockEntity, 27);
    }

    public static NonNullList<ItemStack> loadFromTag(NBTTagCompound tag, int len) {
        NonNullList<ItemStack> buf = NonNullList.withSize(len, ItemStack.EMPTY);
        NBTTagList items = tag.getTagList("Items", 10);

        for (NBTBase item : items) {
            NBTTagCompound itemC = (NBTTagCompound) item;
            int i = itemC.getByte("Slot") & 255;
            if (i >= 0 && i < buf.size()) {
                buf.set(i, new ItemStack(itemC));
            }
        }

        return buf;
    }

}
