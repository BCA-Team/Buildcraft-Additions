package buildcraftAdditions.ModIntegration.imc;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.event.FMLInterModComms;

import net.minecraftforge.common.util.Constants;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.core.Logger;

import com.google.common.base.Strings;

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

			if (type.equals("addDusting")) {
				if (!message.isNBTMessage()) {
					logNotNBT(message);
					continue;
				}

				NBTTagCompound tag = message.getNBTValue();
				if (!checkRequiredNBTTags("Dusting", tag, "Input", "Output"))
					continue;

				ItemStack output = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Output"));
				if (output == null)
					return;

				if (tag.getCompoundTag("Input").hasKey("id", Constants.NBT.TAG_SHORT)) {
					ItemStack input = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Input"));
					if (input != null)
						BCARecipeManager.duster.addRecipe(input, output);
				} else if (tag.hasKey("Input", Constants.NBT.TAG_STRING)) {
					if (!Strings.isNullOrEmpty(tag.getString("Input")))
						BCARecipeManager.duster.addRecipe(tag.getString("Input"), output);
				}
			}
		}
	}

	protected static void logNotNBT(FMLInterModComms.IMCMessage message) {
		logInvalidMessage(message, "NBT");
	}

	protected static void logNotItemStack(FMLInterModComms.IMCMessage message) {
		logInvalidMessage(message, "ItemStack");
	}

	protected static void logNotString(FMLInterModComms.IMCMessage message) {
		logInvalidMessage(message, "String");
	}

	protected static void logInvalidMessage(FMLInterModComms.IMCMessage message, String type) {
		Logger.error(String.format("Received and invalid IMC message : '%s' from '%s'. This message is not of the '%s' type!", message.key, message.getSender(), type));
	}

	protected static boolean checkRequiredNBTTags(String key, NBTTagCompound tag, String... requiredArgs) {
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
