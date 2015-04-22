package buildcraftAdditions.compat.metals;

import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import buildcraftAdditions.api.item.BCAItemManager;
import buildcraftAdditions.api.item.dust.IDust;
import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.compat.CompatModule;
import buildcraftAdditions.items.dust.DustTypes;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@CompatModule(id = "Metals")
public class CompatMetals {

	@CompatModule.Handler
	public void doneLoading(FMLLoadCompleteEvent event) {
		int meta = 4;

		BCAItemManager.dusts.addDust(meta++, "Bronze", 0xAD6726, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Manganese", 0xF3D2D2, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Hepatizon", 0x6B566B, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "DamascusSteel", 0x3D2C1F, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Angmallen", 0xFAFA6C, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Steel", 0x919191, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Eximite", 0x9E83B4, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Meutoite", 0x5F5269, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Desichalkos", 0x742EA8, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Prometheum", 0x5A8156, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "DeepIron", 0x495B69, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Infuscolium", 0xCB6293, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "BlackSteel", 0x395679, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Oureclase", 0xDCA82E, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "AstralSilver", 0xC8D4D5, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Carmot", 0xA99733, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Mithril", 0x08B5C3, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Rubracium", 0x8E2727, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Quicksilver", 0x7CD3C7, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Haderoth", 0xD1531E, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Orichalcum", 0x547A38, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Celenegil", 0x94CC48, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Adamantine", 0xF04040, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Atlarus", 0xF4D603, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Tartarite", 0xFF763C, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Ignatius", 0xE87400, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "ShadowIron", 0x8D7565, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Lemurite", 0xEFEFEF, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Midasium", 0xFFA826, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Vyroxeres", 0x55E001, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Ceruclase", 0x458FAB, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Alduorite", 0xA3DEDE, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Kalendrite", 0xAA5BBD, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Vulcanite", 0xFF8448, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Sanguinite", 0xB90000, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "ShadowSteel", 0x887362, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Inolashite", 0x40AA7D, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Amordrine", 0xA98DB1, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Zinc", 0xDCDFA4, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Brass", 0xD89634, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Electrum", 0xDFD0AA, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Aluminum", 0xEDEDED, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Ardite", 0xF28900, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Manyullyn", 0xAB7EE3, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Cobalt", 0x0064FF, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Copper", 0xBF5E1F, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Lead", 0x808096, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Nickel", 0xBAB0A4, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Platinum", 0xABCDEF, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Silver", 0xB3B3B3, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta, "Tin", 0xF2F2F2, DustTypes.METAL_DUST);
		meta = 61;
		BCAItemManager.dusts.addDust(meta++, "Apatite", 0x1A89C1, DustTypes.GEM_DUST);
		BCAItemManager.dusts.addDust(meta++, "Osmium", 0x92A6B8, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Sulfur", 0xFFDB50, new DustTypes.SimpleDustWithOre(6));
		BCAItemManager.dusts.addDust(meta++, "Saltpeter", 0xDEDEDE, new DustTypes.SimpleDustWithOre(4));
		BCAItemManager.dusts.addDust(meta++, "CertusQuartz", 0xBAC5F2, new DustTypes.SimpleDustWithOre(2));
		BCAItemManager.dusts.addDust(meta++, "AluminumBrass", 0xF0D467, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Alumite", 0xF4CCEC, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "PigIron", 0xF0A8A4, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Invar", 0x959E99, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Signalum", 0xFF5B00, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Lumium", 0xE7D648, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "EnderiumBase", 0x4B7A9A, DustTypes.ENDERIUM_BASE_DUST);
		BCAItemManager.dusts.addDust(meta++, "Enderium", 0x0F7575, DustTypes.ENDERIUM_DUST);
		BCAItemManager.dusts.addDust(meta++, "ElectricalSteel", 0x949494, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "EnergeticAlloy", 0xE47700, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "PhasedGold", 0xB2D44D, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "RedstoneAlloy", 0x942323, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "ConductiveIron", 0x945758, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "PhasedIron", 0x2E6C3D, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "DarkSteel", 0x4B4B4B, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Soularium", 0x654D31, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta, "FzDarkIron", 0x6700BE, DustTypes.METAL_DUST);
		meta = 84;
		BCAItemManager.dusts.addDust(meta++, "Yellorium", 0xD7EF00, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Graphite", 0x515151, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Cyanite", 0x0087EF, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Blutonium", 0x1B00E6, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Ludicrite", 0xEF00EF, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Pyrotheum", 0xF8B33D, DustTypes.ONLY_ORE_DICTIONARY_REGISTRATION);
		BCAItemManager.dusts.addDust(meta++, "Cryotheum", 0x49EFFF, DustTypes.ONLY_ORE_DICTIONARY_REGISTRATION);
		BCAItemManager.dusts.addDust(meta, "Blizz", 0x8BEDFC, DustTypes.ONLY_ORE_DICTIONARY_REGISTRATION);

		addOreDictDusterRecipe("oreApatite", "gemApatite", 6);
		addOreDictDusterRecipe("rodBlizz", "dustBlizz", 4);
		addDustRecipe("Bronze", 4, "dustCopper", "dustCopper", "dustCopper", "dustTin");
		addDustRecipe("AluminumBrass", 4, "dustAluminum", "dustAluminum", "dustAluminum", "dustCopper");
		addDustRecipe("Manyullyn", 1, "dustArdite", "dustCobalt");
		addDustRecipe("Alumite", 4, "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum", "dustIron", "dustIron", "dustObsidian", "dustObsidian");
		addDustRecipe("Invar", 3, "dustIron", "dustIron", "dustNickel");
		addDustRecipe("Signalum", 4, "dustCopper", "dustCopper", "dustCopper", "dustSilver", "blockRedstone", "dustRedstone");
		addDustRecipe("Lumium", 4, "dustTin", "dustTin", "dustTin", "dustSilver", "dustGlowstone", "dustGlowstone", "dustGlowstone", "dustGlowstone");
		addDustRecipe("EnderiumBase", 4, "dustTin", "dustTin", "dustSilver", "dustPlatinum");
		addDustRecipe("Enderium", 4, "dustEnderiumBase", "dustEnderiumBase", "dustPyrotheum", "dustEnderPearl", "dustEnderPearl");
		addDustRecipe("ElectricalSteel", 1, "dustIron", "dustCoal", "itemSilicon");
		addDustRecipe("EnergeticAlloy", 1, "dustRedstone", "dustGold", "dustGlowstone");
		addDustRecipe("PhasedGold", 1, "dustEnergeticAlloy", "dustEnderPearl");
		addDustRecipe("RedstoneAlloy", 1, "dustRedstone", "itemSilicon");
		addDustRecipe("ConductiveIron", 1, "dustRedstone", "dustIron");
		addDustRecipe("PhasedIron", 1, "dustIron", "dustEnderPearl");
		addDustRecipe("DarkSteel", 1, "dustIron", "dustCoal", "dustObsidian");
		addDustRecipe("Soularium", 1, "blockSoulSand", "dustGold");
		addDustRecipe("Cyanite", 1, "blockSand", "dustYellorium");
		addDustRecipe("Pyrotheum", 2, "dustCoal", "dustSulfur", "dustRedstone", "dustBlaze");
		addDustRecipe("Cryotheum", 2, "materialSnowball", "dustSaltpeter", "dustRedstone", "dustBlizz");
		addDustRecipe("Blizz", 1, "materialSnowball", "dustRedstone", "dustRedstone");
		addDustSmelting("Graphite", 1, "dustCoal");
		addDustSmelting("Graphite", 1, "dustCharcoal");
	}

	private void addOreDictDusterRecipe(String input, String output, int outputCount) {
		for (ItemStack stack : OreDictionary.getOres(output)) {
			if (stack != null && stack.getItem() != null) {
				ItemStack out = stack.copy();
				out.stackSize = outputCount;
				BCARecipeManager.duster.addRecipe(input, out);
				return;
			}
		}
	}

	private void addDustRecipe(String output, int outputCount, Object... inputs) {
		IDust dust = BCAItemManager.dusts.getDust(output);
		if (dust != null) {
			ItemStack dustStack = ItemStack.copyItemStack(dust.getDustStack());
			if (dustStack != null && dustStack.getItem() != null) {
				dustStack.stackSize = outputCount;
				GameRegistry.addRecipe(new ShapelessOreRecipe(dustStack, inputs));
			}
		}
	}

	private void addDustSmelting(String output, int outputCount, String input) {
		IDust dust = BCAItemManager.dusts.getDust(output);
		if (dust != null) {
			ItemStack dustStack = ItemStack.copyItemStack(dust.getDustStack());
			if (dustStack != null && dustStack.getItem() != null) {
				dustStack.stackSize = outputCount;
				for (ItemStack stack : OreDictionary.getOres(input))
					if (stack != null && stack.getItem() != null)
						GameRegistry.addSmelting(stack, dustStack, 0);
			}
		}
	}
}
