package buildcraftAdditions.compat.buildcraft.recipe;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class UpgradeRecipeUpgrade {

	/*private final String upgradeName;

	public UpgradeRecipeUpgrade(String upgradeName) {
		this.upgradeName = upgradeName;
		setContents(Variables.MOD.ID + ":" + upgradeName + "Upgrade", ItemsAndBlocks.itemKineticMultiTool, 10000, 600);
	}

	@Override
	public boolean isValidInputA(ItemStack inputA) {
		return inputA != null && inputA.getItem() instanceof ItemKineticMultiTool && ItemKineticMultiTool.canInstallUpgrade(inputA, upgradeName) && !ItemKineticMultiTool.isUpgradeInstalled(inputA, upgradeName);
	}

	@Override
	public boolean isValidInputB(ItemStack inputB) {
		return inputB != null && inputB.getItem() instanceof ItemToolUpgrade && upgradeName.equalsIgnoreCase(((ItemToolUpgrade) inputB.getItem()).getType());
	}

	@Override
	public CraftingResult<ItemStack> craft(TileIntegrationTable crafter, boolean preview, ItemStack inputA, ItemStack inputB) {
		CraftingResult<ItemStack> result = super.craft(crafter, preview, inputA, inputB);
		ItemStack outputStack = inputA.copy();
		ItemKineticMultiTool.installUpgrade(upgradeName, outputStack);
		result.crafted = outputStack;
		return result;
	}
	*/
}
