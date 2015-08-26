package buildcraftAdditions.compat.buildcraft.recipe.tool;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import buildcraftAdditions.items.Tools.ItemKineticMultiTool;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class KineticToolUpgrade implements IKineticToolUpgrade {

	private final String upgradeName;
	private final ItemStack upgradeStack;

	public KineticToolUpgrade(String upgradeName, Block upgradeBlock) {
		this(upgradeName, new ItemStack(upgradeBlock));
	}

	public KineticToolUpgrade(String upgradeName, Item upgradeItem) {
		this(upgradeName, new ItemStack(upgradeItem));
	}

	public KineticToolUpgrade(String upgradeName, ItemStack upgradeStack) {
		this.upgradeName = upgradeName;
		this.upgradeStack = upgradeStack;
	}

	@Override
	public boolean canUpgradeBeApplied(ItemStack input) {
		return !ItemKineticMultiTool.isUpgradeInstalled(input, upgradeName) && ItemKineticMultiTool.canInstallUpgrade(input, upgradeName);
	}

	@Override
	public boolean doesExpansionMatch(ItemStack expansion) {
		return Utils.areItemStacksEqualRecipe(upgradeStack, expansion);
	}

	@Override
	public void install(ItemStack output) {
		ItemKineticMultiTool.installUpgrade(output, upgradeName);
	}
}
