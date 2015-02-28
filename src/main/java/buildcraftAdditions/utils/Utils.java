package buildcraftAdditions.utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import com.google.common.base.Strings;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import buildcraft.api.transport.IPipeTile;

import buildcraftAdditions.api.configurableOutput.EnumPriority;
import buildcraftAdditions.tileEntities.varHelpers.SideConfiguration;

public class Utils {

	public static final String[] COLOR_NAMES = {"white", "orange", "magenta", "lightBlue", "yellow", "lime", "pink", "gray", "lightGray", "cyan", "purple", "blue", "brown", "green", "red", "black"};

	public static FluidStack getFluidStackFromItemStack(ItemStack itemStack) {
		if (itemStack.stackTagCompound == null || !itemStack.getTagCompound().hasKey("Fluid", Constants.NBT.TAG_COMPOUND))
			return null;
		return FluidStack.loadFluidStackFromNBT(itemStack.getTagCompound().getCompoundTag("Fluid"));
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

	public static String localizeAllFormatted(String key, String... strings) {
		Object[] objects = new Object[strings != null ? strings.length : 0];
		for (int i = 0; i < objects.length; i++)
			objects[i] = localize(strings[i]);
		return localizeFormatted(key, objects);
	}

	public static String decapitalizeFirstChar(String string) {
		return !Strings.isNullOrEmpty(string) ? Character.toLowerCase(string.charAt(0)) + string.substring(1) : null;
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

	public static ItemStack outputStack(Location from, ItemStack output, SideConfiguration configuration) {
		for (EnumPriority priority : EnumPriority.values()) {

			//first try to put it intro a pipe
			for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
				Location location = from.copy();
				if (configuration.getPriority(direction) != priority)
					continue;
				if (!configuration.canSend(direction))
					continue;
				location.move(direction);
				TileEntity entity = location.getTileEntity();
				if (entity instanceof IPipeTile) {
					IPipeTile pipe = (IPipeTile) entity;
					if (output != null && pipe.isPipeConnected(direction.getOpposite()) && pipe.getPipeType() == IPipeTile.PipeType.ITEM) {
						int leftOver = pipe.injectItem(output.copy(), true, direction.getOpposite(), null);
						output.stackSize -= leftOver;
						if (output.stackSize == 0)
							output = null;
					}
				}
			}
			//try to put it intro an inventory
			for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
				Location location = from.copy();
				if (configuration.getPriority(direction) != priority)
					continue;
				if (!configuration.canSend(direction))
					continue;
				location.move(direction);
				TileEntity entity = location.getTileEntity();
				if (entity != null && entity instanceof IInventory) {
					IInventory outputInventory = (IInventory) entity;
					for (int slot = 0; slot < outputInventory.getSizeInventory(); slot++) {
						int stackLimit = outputInventory.getInventoryStackLimit();
						ItemStack testStack = outputInventory.getStackInSlot(slot);
						if (output != null &&
								(testStack == null || (testStack.stackSize + output.stackSize <= testStack.getMaxStackSize() && testStack.getItem() == output.getItem() && testStack.getItemDamage() == output.getItemDamage()))) {
							ItemStack stack = outputInventory.getStackInSlot(slot);
							int toMove;
							if (stack == null) {
								toMove = stackLimit - 1;
								stack = output.copy();
								stack.stackSize = 0;
							} else {
								toMove = stackLimit - stack.stackSize;
							}
							if (toMove > output.stackSize)
								toMove = output.stackSize;
							stack.stackSize += toMove;
							output.stackSize -= toMove;
							outputInventory.setInventorySlotContents(slot, stack);
							outputInventory.markDirty();
							if (output.stackSize == 0)
								output = null;
						}
					}
				}
			}
		}
		return output;
	}
}
