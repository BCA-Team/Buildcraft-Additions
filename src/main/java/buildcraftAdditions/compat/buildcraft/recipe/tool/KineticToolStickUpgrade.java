package buildcraftAdditions.compat.buildcraft.recipe.tool;

import com.google.common.base.Strings;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import buildcraftAdditions.items.Tools.ItemKineticMultiTool;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class KineticToolStickUpgrade implements IKineticToolUpgrade {

	private final String stickName, inputStickOreDictName, prevStickName;

	public KineticToolStickUpgrade(String stickName, String inputStickOreDictName) {
		this(stickName, inputStickOreDictName, null);
	}

	public KineticToolStickUpgrade(String stickName, String inputStickOreDictName, String prevStickName) {
		this.stickName = stickName;
		this.inputStickOreDictName = inputStickOreDictName;
		this.prevStickName = prevStickName;
	}

	@Override
	public boolean canUpgradeBeApplied(ItemStack input) {
		return !ItemKineticMultiTool.isUpgradeInstalled(input, stickName) && (Strings.isNullOrEmpty(prevStickName) || ItemKineticMultiTool.isUpgradeInstalled(input, prevStickName));
	}

	@Override
	public boolean doesExpansionMatch(ItemStack expansion) {
		int[] oreIDs = OreDictionary.getOreIDs(expansion);
		if (oreIDs != null && oreIDs.length > 0) {
			for (int oreID : oreIDs) {
				String oreName = OreDictionary.getOreName(oreID);
				if (!Strings.isNullOrEmpty(oreName)) {
					if (oreName.equals(inputStickOreDictName))
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public void install(ItemStack output) {
		ItemKineticMultiTool.installStick(output, stickName);
	}
}
