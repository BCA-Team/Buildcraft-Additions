package buildcraftAdditions.utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import java.util.Set;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import buildcraft.api.transport.IInjectable;

import buildcraftAdditions.api.configurableOutput.EnumPriority;
import buildcraftAdditions.api.configurableOutput.SideConfiguration;

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
		if (strings != null) {
			for (int i = 0; i < objects.length; i++) {
				String string = strings[i];
				if (string != null)
					objects[i] = localize(string);
			}
		}
		return localizeFormatted(key, objects);
	}

	public static String decapitalizeFirstChar(String string) {
		return !Strings.isNullOrEmpty(string) ? Character.toLowerCase(string.charAt(0)) + string.substring(1) : null;
	}

	public static void dropInventory(World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof IInventory) {
			IInventory inventory = (IInventory) tile;
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				dropItemstack(world, x, y, z, inventory.getStackInSlot(i));
				inventory.setInventorySlotContents(i, null);
			}
		}
	}

	public static void dropItemstack(World world, int x, int y, int z, ItemStack stack) {
		if (!world.isRemote && stack != null && stack.stackSize > 0 && stack.getItem() != null) {
			float rx = world.rand.nextFloat() * 0.8F + 0.1F;
			float ry = world.rand.nextFloat() * 0.8F + 0.1F;
			float rz = world.rand.nextFloat() * 0.8F + 0.1F;
			EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, stack.copy());
			float factor = 0.05F;
			entityItem.motionX = (world.rand.nextGaussian() * factor);
			entityItem.motionY = (world.rand.nextGaussian() * factor + 0.2000000029802322D);
			entityItem.motionZ = (world.rand.nextGaussian() * factor);
			world.spawnEntityInWorld(entityItem);
		}
	}

	public static void dropItemstackAtEntity(Entity entity, ItemStack stack) {
		if (!entity.worldObj.isRemote) {
			EntityItem entityItem = new EntityItem(entity.worldObj, entity.posX, entity.posY + entity.getEyeHeight() / 2.0F, entity.posZ, stack.copy());
			entity.worldObj.spawnEntityInWorld(entityItem);
		}
	}

	public static void addToPlayerInv(EntityPlayer player, ItemStack stack) {
		if (stack != null && stack.stackSize > 0 && stack.getItem() != null && !player.inventory.addItemStackToInventory(stack.copy()))
			player.dropPlayerItemWithRandomChoice(stack.copy(), false);
	}

	public static void setGLColorFromInt(int color) {
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;
		GL11.glColor4f(red, green, blue, 1.0F);
	}

	public static boolean areItemStacksEqualRecipe(ItemStack stack1, ItemStack stack2) {
		return stack1 != null && stack2 != null && stack1.getItem() == stack2.getItem() && (stack1.getItemDamage() == stack2.getItemDamage() || stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack1.getItem().isDamageable());
	}

	public static boolean areItemStacksMergeable(ItemStack stack1, ItemStack stack2) {
		return stack1 == null && stack2 == null || (stack1 != null && stack2 != null && stack1.isItemEqual(stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2));
	}

	public static ItemStack outputStack(Location from, ItemStack output, SideConfiguration configuration) {
		if (from == null || output == null || output.getItem() == null || output.stackSize <= 0 || configuration == null)
			return output;
		for (EnumPriority priority : EnumPriority.values()) {
			for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
				Location location = from.copy();
				if (configuration.getPriority(direction) != priority)
					continue;
				if (!configuration.canSend(direction))
					continue;
				location.move(direction);
				TileEntity tile = location.getTileEntity();
				if (tile instanceof IInjectable) {
					IInjectable injectable = (IInjectable) tile;
					if (output != null && injectable.canInjectItems(direction.getOpposite())) {
						int leftOver = injectable.injectItem(output.copy(), true, direction.getOpposite(), null);
						output.stackSize -= leftOver;
						if (output.stackSize <= 0)
							output = null;
					}
				}
			}
			for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
				Location location = from.copy();
				if (configuration.getPriority(direction) != priority)
					continue;
				if (!configuration.canSend(direction))
					continue;
				location.move(direction);
				TileEntity tile = location.getTileEntity();
				if (tile != null && tile instanceof IInventory) {
					IInventory outputInventory = (IInventory) tile;
					ISidedInventory sidedInventory = null;
					if (tile instanceof ISidedInventory)
						sidedInventory = (ISidedInventory) tile;
					Set<Integer> allowedSlots = null;
					if (sidedInventory != null) {
						allowedSlots = Sets.newHashSet();
						int[] accessibleSlots = sidedInventory.getAccessibleSlotsFromSide(direction.getOpposite().ordinal());
						if (accessibleSlots != null)
							for (int slot : accessibleSlots)
								allowedSlots.add(slot);
					}
					int stackLimit = outputInventory.getInventoryStackLimit();
					for (int slot = 0; slot < outputInventory.getSizeInventory(); slot++) {
						if (output == null || output.getItem() == null || output.stackSize <= 0 || (allowedSlots != null && !allowedSlots.contains(slot)) || (sidedInventory != null && !sidedInventory.canInsertItem(slot, output, direction.getOpposite().ordinal())))
							continue;
						ItemStack stack = outputInventory.getStackInSlot(slot);
						if (areItemStacksMergeable(stack, output)) {
							int toMove;
							if (stack == null) {
								toMove = stackLimit;
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
							if (output.stackSize <= 0)
								output = null;
						}
					}
				}
			}
		}
		return output;
	}
}
