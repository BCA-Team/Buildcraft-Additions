package buildcraftAdditions.items.Tools;

import buildcraft.BuildCraftCore;
import buildcraft.api.recipes.IIntegrationRecipeManager;
import buildcraft.silicon.ItemRedstoneChipset;
import buildcraftAdditions.BuildcraftAdditions;
import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ToolCoreRecepie implements IIntegrationRecipeManager.IIntegrationRecipe {
    @Override
    public double getEnergyCost() {
        return 3000;
    }

    @Override
    public boolean isValidInputA(ItemStack inputA) {
        return inputA != null && inputA.getItem() == BuildCraftCore.goldGearItem;
    }

    @Override
    public boolean isValidInputB(ItemStack inputB) {
        return inputB != null && inputB.getItem() == ItemRedstoneChipset.Chipset.DIAMOND.getStack().getItem();
    }

    @Override
    public ItemStack getOutputForInputs(ItemStack inputA, ItemStack inputB, ItemStack[] components) {
        if (!isValidInputA(inputA)) {
            return null;
        }

        if (!isValidInputB(inputB)) {
            return null;
        }
        return new ItemStack(BuildcraftAdditions.toolCore, 1);
    }

    @Override
    public ItemStack[] getComponents() {
        return new ItemStack[0];
    }

    @Override
    public ItemStack[] getExampleInputsA() {
        return new ItemStack[0];
    }

    @Override
    public ItemStack[] getExampleInputsB() {
        return new ItemStack[0];
    }
}
