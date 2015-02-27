package buildcraftAdditions.ModIntegration.imc;

import java.util.List;

import com.google.common.base.Strings;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.event.FMLInterModComms;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.api.item.BCAItemManager;
import buildcraftAdditions.api.item.dust.IDustType;
import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.core.Logger;
import buildcraftAdditions.items.dust.DustTypes;

/**
 * Copyright (c) 2014-2015, AEnterprise
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

			if (type.equalsIgnoreCase("addDustingRecipe")) {
				if (!message.isNBTMessage()) {
					logNotNBT(message);
					continue;
				}

				NBTTagCompound tag = message.getNBTValue();
				if (!checkRequiredNBTTags(message, tag, "Input", "Output", Constants.NBT.TAG_COMPOUND))
					continue;

				ItemStack output = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Output"));
				if (output == null) {
					logWrongNBT(message);
					continue;
				}
				if (tag.hasKey("Input", Constants.NBT.TAG_STRING)) {
					String oreInput = tag.getString("Input");
					if (!Strings.isNullOrEmpty(oreInput))
						BCARecipeManager.duster.addRecipe(oreInput, output);
					else {
						logWrongNBT(message);
						continue;
					}
				} else if (tag.hasKey("Input", Constants.NBT.TAG_COMPOUND)) {
					ItemStack input = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Input"));
					if (input != null)
						BCARecipeManager.duster.addRecipe(input, output);
					else {
						logWrongNBT(message);
						continue;
					}
				} else {
					logError(message, "Missing required NBT Tag 'Input' of type STRING or TAG_COMPOUND!");
					continue;
				}
			} else if (type.equalsIgnoreCase("addCoolingTowerRecipe")) {
				if (!message.isNBTMessage()) {
					logNotNBT(message);
					continue;
				}

				NBTTagCompound tag = message.getNBTValue();
				if (!checkRequiredNBTTags(message, tag, "Input", Constants.NBT.TAG_COMPOUND, "Output", Constants.NBT.TAG_COMPOUND, "Heat", Constants.NBT.TAG_FLOAT))
					continue;

				FluidStack input = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Input"));
				if (input == null) {
					logNotNBT(message);
					continue;
				}
				FluidStack output = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Output"));
				if (output == null) {
					logNotNBT(message);
					continue;
				}
				BCARecipeManager.cooling.addRecipe(input, output, tag.getFloat("Heat"));
			} else if (type.equalsIgnoreCase("addRefineryRecipe")) {
				if (!message.isNBTMessage()) {
					logNotNBT(message);
					continue;
				}

				NBTTagCompound tag = message.getNBTValue();
				if (!checkRequiredNBTTags(message, tag, "Input", Constants.NBT.TAG_COMPOUND, "Output", Constants.NBT.TAG_COMPOUND, "RequiredHeat", Constants.NBT.TAG_INT))
					continue;

				FluidStack input = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Input"));
				if (input == null) {
					logNotNBT(message);
					continue;
				}
				FluidStack output = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Output"));
				if (output == null) {
					logNotNBT(message);
					continue;
				}
				BCARecipeManager.refinery.addRecipe(input, output, tag.getInteger("RequiredHeat"));
			} else if (type.equalsIgnoreCase("removeDustingRecipe")) {
				if (!message.isNBTMessage()) {
					logNotNBT(message);
					continue;
				}

				NBTTagCompound tag = message.getNBTValue();
				if (!checkRequiredNBTTags(message, tag, "Input", Constants.NBT.TAG_COMPOUND))
					continue;

				ItemStack input = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Input"));
				if (input == null) {
					logNotNBT(message);
					continue;
				}
				BCARecipeManager.duster.removeRecipe(input);
			} else if (type.equalsIgnoreCase("removeCoolingRecipe")) {
				if (!message.isNBTMessage()) {
					logNotNBT(message);
					continue;
				}

				NBTTagCompound tag = message.getNBTValue();
				if (!checkRequiredNBTTags(message, tag, "Input", Constants.NBT.TAG_COMPOUND))
					continue;

				FluidStack input = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Input"));
				if (input == null) {
					logNotNBT(message);
					continue;
				}
				BCARecipeManager.cooling.removeRecipe(input);
			} else if (type.equalsIgnoreCase("removeRefineryRecipe")) {
				if (!message.isNBTMessage()) {
					logNotNBT(message);
					continue;
				}

				NBTTagCompound tag = message.getNBTValue();
				if (!checkRequiredNBTTags(message, tag, "Input", Constants.NBT.TAG_COMPOUND))
					continue;

				FluidStack input = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Input"));
				if (input == null) {
					logNotNBT(message);
					continue;
				}
				BCARecipeManager.refinery.removeRecipe(input);
			} else if (type.equalsIgnoreCase("addMetalDust")) {
				if (!message.isNBTMessage()) {
					logNotNBT(message);
					continue;
				}

				NBTTagCompound tag = message.getNBTValue();
				if (!checkRequiredNBTTags(message, tag, "Meta", Constants.NBT.TAG_INT, "Name", Constants.NBT.TAG_STRING, "ColorMultiplier", Constants.NBT.TAG_INT))
					continue;

				BCAItemManager.dusts.addDust(tag.getInteger("Meta"), tag.getString("Name"), tag.getInteger("ColorMultiplier"), DustTypes.METAL_DUST);
			} else if (type.equalsIgnoreCase("addGemDust")) {
				if (!message.isNBTMessage()) {
					logNotNBT(message);
					continue;
				}

				NBTTagCompound tag = message.getNBTValue();
				if (!checkRequiredNBTTags(message, tag, "Meta", Constants.NBT.TAG_INT, "Name", Constants.NBT.TAG_STRING, "ColorMultiplier", Constants.NBT.TAG_INT))
					continue;

				BCAItemManager.dusts.addDust(tag.getInteger("Meta"), tag.getString("Name"), tag.getInteger("ColorMultiplier"), DustTypes.GEM_DUST);
			} else if (type.equalsIgnoreCase("addDust")) {
				if (!message.isNBTMessage()) {
					logNotNBT(message);
					continue;
				}

				NBTTagCompound tag = message.getNBTValue();
				if (!checkRequiredNBTTags(message, tag, "Meta", Constants.NBT.TAG_INT, "Name", Constants.NBT.TAG_STRING, "ColorMultiplier", Constants.NBT.TAG_INT, "DustType", Constants.NBT.TAG_STRING))
					continue;

				String className = tag.getString("DustType");
				if (Strings.isNullOrEmpty(className)) {
					logWrongNBT(message);
					continue;
				}
				IDustType iDustType;
				try {
					iDustType = (IDustType) Class.forName(className).newInstance();
				} catch (Throwable t) {
					logWrongNBT(message);
					continue;
				}
				BCAItemManager.dusts.addDust(tag.getInteger("Meta"), tag.getString("Name"), tag.getInteger("ColorMultiplier"), iDustType);
			} else if (type.equalsIgnoreCase("removeDust")) {
				if (!message.isStringMessage()) {
					logNotString(message);
					continue;
				}

				String string = message.getStringValue();
				if (Strings.isNullOrEmpty(string)) {
					logError(message, "The string is null or empty!");
					continue;
				}
				try {
					BCAItemManager.dusts.removeDust(Integer.valueOf(string));
				} catch (NumberFormatException e) {
					BCAItemManager.dusts.removeDust(string);
				}
			} else {
				logError(message, "Message type not supported!");
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
		logError(message, "This message is not of the type '%s'!", type);
	}

	private static void logWrongNBT(FMLInterModComms.IMCMessage message) {
		logError(message, "This message contains (a/an) wrong/corrupt/incomplete NBT tag(s)!");
	}

	private static void logError(FMLInterModComms.IMCMessage message, String string) {
		Logger.error(String.format("Received an invalid IMC message: '%s' from '%s'. " + string, message.key, message.getSender()));
	}

	private static void logError(FMLInterModComms.IMCMessage message, String string, Object... args) {
		Object[] formatArgs = new Object[(args != null ? args.length : 0) + 2];
		formatArgs[0] = message.key;
		formatArgs[1] = message.getSender();
		for (int i = 2; i < formatArgs.length; i++)
			formatArgs[i] = args[i - 2];
		logError(message, String.format(string, formatArgs));
	}

	private static boolean checkRequiredNBTTags(FMLInterModComms.IMCMessage message, NBTTagCompound tag, Object... requiredArgs) {
		boolean fine = true;
		for (int i = 0; i < requiredArgs.length; i++) {
			Object o = requiredArgs[i];
			if (o == null || !(o instanceof String))
				continue;
			String tagKey = (String) o;
			Object tagType = null;
			if (i < requiredArgs.length - 1)
				tagType = requiredArgs[i + 1];
			if (tagType != null && tagType instanceof Integer) {
				int tagTypeCode = (Integer) tagType;
				if (!tag.hasKey(tagKey, tagTypeCode)) {
					logError(message, "Missing required NBT Tag '%s' of type %d!", tagKey, tagTypeCode);
					fine = false;
				}
			} else {
				if (!tag.hasKey(tagKey)) {
					logError(message, "Missing required NBT Tag '%s'!", tagKey);
					fine = false;
				}
			}
		}

		return fine;
	}

}
