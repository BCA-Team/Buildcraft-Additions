package buildcraftAdditions.utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class Utils {

	public static FluidStack getFluidStackFromItemStack(ItemStack itemStack) {
		if (itemStack.stackTagCompound == null || !itemStack.getTagCompound().hasKey("Fluid"))
			return null;

		NBTTagCompound fluidTag = itemStack.getTagCompound().getCompoundTag("Fluid");

		return FluidStack.loadFluidStackFromNBT(fluidTag);
	}

	public static ForgeDirection get2dOrientation(EntityLivingBase entityliving) {
		ForgeDirection[] orientationTable = {ForgeDirection.SOUTH,
				ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.EAST};
		int orientationIndex = MathHelper.floor_double((entityliving.rotationYaw + 45.0) / 90.0) & 3;
		return orientationTable[orientationIndex];
	}

	public static ForgeDirection get3dOrientation(EntityLivingBase entity) {
		if (entity.rotationPitch < -45.5F)
			return ForgeDirection.UP;
		else if (entity.rotationPitch > 45.5F)
			return ForgeDirection.DOWN;
		return get2dOrientation(entity);
	}

	public static int[] createSlotArray(int first, int count) {
		int[] slots = new int[count];
		for (int k = first; k < first + count; k++) {
			slots[k - first] = k;
		}
		return slots;
	}

	public static String localize(String key) {
		return ("" + StatCollector.translateToLocal(key)).trim();
	}

	public static String localizeFormatted(String key, Object... objects) {
		return ("" + StatCollector.translateToLocalFormatted(key, objects)).trim();
	}

	public static void dropItemstack(World world, int x, int y, int z, ItemStack stack) {
		float f1 = 0.7F;
		double d = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
		double d1 = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
		double d2 = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
		EntityItem itemToDrop = new EntityItem(world, x + d, y + d1, z + d2, stack);
		itemToDrop.delayBeforeCanPickup = 10;
		if (!world.isRemote)
			world.spawnEntityInWorld(itemToDrop);
	}

	public static void setGLColorFromInt(int color) {
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;
		GL11.glColor4f(red, green, blue, 1.0F);
	}

	public static boolean areItemStacksEqualItem(ItemStack stack1, ItemStack stack2) {
		return stack1 != null && stack2 != null && stack1.getItem() == stack2.getItem() && (stack1.getItemDamage() == stack2.getItemDamage() || stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack1.getItem().isDamageable());
	}

}
