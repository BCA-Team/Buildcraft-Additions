package buildcraftAdditions.items.Tools;

import buildcraft.api.recipes.IIntegrationRecipeManager;
import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

public class UpgradeRecepie implements IIntegrationRecipeManager.IIntegrationRecipe {
    public ItemStack stack;

    @Override
    public double getEnergyCost() {
        return 1000;
    }

    @Override
    public boolean isValidInputA(ItemStack inputA) {
        stack=inputA;
        return inputA != null && inputA.getItem() instanceof ItemKineticTool;
    }

    @Override
    public boolean isValidInputB(ItemStack inputB) {
        if (stack == null)
            return false;
        if (!(stack.getItem() instanceof ItemKineticTool))
            return false;
        if (inputB == null)
            return false;
        ItemKineticTool tool = (ItemKineticTool) stack.getItem();
        ToolUpgrade upgrade = (ToolUpgrade) inputB.getItem();
        return !tool.isUpgradeInstalled(stack, upgrade.getType());
    }

    @Override
    public ItemStack getOutputForInputs(ItemStack inputA, ItemStack inputB, ItemStack[] components) {
        ItemStack outputStack = new ItemStack(new ItemKineticTool(), 1);
        outputStack.stackTagCompound = inputA.copy().stackTagCompound;
        ItemKineticTool output = (ItemKineticTool) outputStack.getItem();
        ToolUpgrade upgrade = (ToolUpgrade) inputB.getItem();
        output.installUpgrade(upgrade.getType(), outputStack);
        output.writeUpgrades(outputStack);
        return outputStack;
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
