package buildcraftAdditions.ModIntegration.imc;

import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.event.FMLInterModComms;

import buildcraftAdditions.reference.ItemsAndBlocks;

public class IMCSender {

	public static void sendMessages() {
		tinkersConstruct();
	}

	protected static void tinkersConstruct() {
		FMLInterModComms.sendMessage("TConstruct", "addFluxBattery", new ItemStack(ItemsAndBlocks.powerCapsuleTier1));
		FMLInterModComms.sendMessage("TConstruct", "addFluxBattery", new ItemStack(ItemsAndBlocks.powerCapsuleTier2));
		FMLInterModComms.sendMessage("TConstruct", "addFluxBattery", new ItemStack(ItemsAndBlocks.powerCapsuleTier3));
	}
}
