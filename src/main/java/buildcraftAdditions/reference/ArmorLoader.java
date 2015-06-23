package buildcraftAdditions.reference;

import buildcraftAdditions.armour.ItemKineticBackpack;
import buildcraftAdditions.armour.ItemRocketPants;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ArmorLoader {
	public static ItemArmor kineticBackpack;
	public static ItemArmor rocketPants;
	//public static ItemArmor hoverBoots;

	public static void loadArmor() {
		kineticBackpack = new ItemKineticBackpack();
		rocketPants = new ItemRocketPants();
		//hoverBoots = new ItemHoverBoots();

	}

	public static void addRecipes() {
		GameRegistry.addRecipe(new ItemStack(kineticBackpack), "PLP", "PPP", "PPP", 'P', ItemLoader.conductivePlate, 'L', Items.leather);
		GameRegistry.addRecipe(new ItemStack(BlockLoader.backpackStand), "III", " I ", "III", 'I', Items.iron_ingot);
		GameRegistry.addRecipe(new ItemStack(rocketPants), "P P", "ITI", "T T", 'P', ItemLoader.lightPlating, 'I', Items.iron_ingot, 'T', ItemLoader.thruster);
		//GameRegistry.addRecipe(new ItemStack(hoverBoots), "P P", "I I", "T T", 'P', ItemLoader.lightPlating, 'I', Items.iron_ingot, 'T', ItemLoader.thruster);
	}
}
