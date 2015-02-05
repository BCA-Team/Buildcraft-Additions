package buildcraftAdditions.ModIntegration.imc;

import java.util.List;

import com.google.common.base.Strings;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.event.FMLInterModComms;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.core.Logger;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class IMCHandler {

	public static void handleIMC(List<FMLInterModComms.IMCMessage> messages) {

		for (FMLInterModComms.IMCMessage message : messages) {
			String type = message.key;

			if (Strings.isNullOrEmpty(type))
				continue;

			if (type.equals("addDustingRecipe")) {
				if (!message.isNBTMessage()) {
					logNotNBT(message);
					continue;
				}

				NBTTagCompound tag = message.getNBTValue();
				if (!checkRequiredNBTTags("Dusting", tag, "Input", "Output"))
					continue;

				if (tag.hasKey("Output", Constants.NBT.TAG_COMPOUND)) {
					ItemStack output = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Output"));
					if (output == null)
						continue;
					if (tag.hasKey("Input", Constants.NBT.TAG_STRING)) {
						String oreInput = tag.getString("Input");
						if (!Strings.isNullOrEmpty(oreInput))
							BCARecipeManager.duster.addRecipe(oreInput, output);
					} else if (tag.hasKey("Input", Constants.NBT.TAG_COMPOUND)) {
						ItemStack input = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Input"));
						if (input != null)
							BCARecipeManager.duster.addRecipe(input, output);
					}
				}
			} else if (type.equals("addCoolingTowerRecipe")) {
				if (!message.isNBTMessage()) {
					logNotNBT(message);
					continue;
				}

				NBTTagCompound tag = message.getNBTValue();
				if (!checkRequiredNBTTags("Cooling Tower", tag, "Input", "Output", "Heat"))
					continue;

				if (tag.hasKey("Input", Constants.NBT.TAG_COMPOUND) && tag.hasKey("Output", Constants.NBT.TAG_COMPOUND) && tag.hasKey("Heat", Constants.NBT.TAG_FLOAT)) {
					FluidStack input = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Input"));
					if (input == null)
						continue;
					FluidStack output = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Output"));
					if (output == null)
						continue;
					BCARecipeManager.cooling.addRecipe(input, output, tag.getFloat("Heat"));
				}
			} else if (type.equals("addRefineryRecipe")) {
				if (!message.isNBTMessage()) {
					logNotNBT(message);
					continue;
				}

				NBTTagCompound tag = message.getNBTValue();
				if (!checkRequiredNBTTags("Refinery", tag, "Input", "Output", "RequiredHeat"))
					continue;

				if (tag.hasKey("Input", Constants.NBT.TAG_COMPOUND) && tag.hasKey("Output", Constants.NBT.TAG_COMPOUND) && tag.hasKey("RequiredHeat", Constants.NBT.TAG_INT)) {
					FluidStack input = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Input"));
					if (input == null)
						continue;
					FluidStack output = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Output"));
					if (output == null)
						continue;
					BCARecipeManager.refinery.addRecipe(input, output, tag.getInteger("RequiredHeat"));
				}
			} else if (type.equals("removeCoolingRecipe")) {
				if (message.isNBTMessage()) {
					logNotNBT(message);
					continue;
				}

				NBTTagCompound tag = message.getNBTValue();
				if (checkRequiredNBTTags("Cooling Tower", tag, "Input"))
					continue;

				if (tag.hasKey("Input", Constants.NBT.TAG_COMPOUND)) {
					FluidStack input = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Input"));
					if (input == null)
						continue;
					BCARecipeManager.cooling.removeRecipe(input);
				}
			}
		}
	}

	private static void logNotNBT(FMLInterModComms.IMCMessage message) {
		logInvalidMessage(message, "NBT");
	}

	private static void logNotItemStack(FMLInterModComms.IMCMessage message) {
		logInvalidMessage(message, "ItemStack");
	}

	private static void logNotString(FMLInterModComms.IMCMessage message) {
		logInvalidMessage(message, "String");
	}

	private static void logInvalidMessage(FMLInterModComms.IMCMessage message, String type) {
		Logger.error(String.format("Received and invalid IMC message : '%s' from '%s'. This message is not of the '%s' type!", message.key, message.getSender(), type));
	}

	private static boolean checkRequiredNBTTags(String key, NBTTagCompound tag, String... requiredArgs) {
		boolean fine = true;
		for (String arg : requiredArgs) {
			if (!tag.hasKey(arg)) {
				Logger.error(String.format("%s IMC: Missing required NBT Tag '%s'", key, arg));
				fine = false;
			}
		}

		return fine;
	}
}
