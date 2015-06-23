package buildcraftAdditions.compat.imc;

import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.event.FMLInterModComms;

import buildcraftAdditions.reference.ItemLoader;

public class IMCSender {

	public static void sendMessages() {
		tinkersConstruct();
	}

	private static void tinkersConstruct() {
		FMLInterModComms.sendMessage("TConstruct", "addFluxBattery", new ItemStack(ItemLoader.powerCapsuleTier1));
		FMLInterModComms.sendMessage("TConstruct", "addFluxBattery", new ItemStack(ItemLoader.powerCapsuleTier2));
		FMLInterModComms.sendMessage("TConstruct", "addFluxBattery", new ItemStack(ItemLoader.powerCapsuleTier3));
	}
}
