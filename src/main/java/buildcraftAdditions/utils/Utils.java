package buildcraftAdditions.utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import buildcraft.api.transport.IInjectable;

import buildcraftAdditions.api.configurableOutput.EnumPriority;
import buildcraftAdditions.api.configurableOutput.SideConfiguration;
import buildcraftAdditions.reference.enums.EnumMachineUpgrades;
import buildcraftAdditions.tileEntities.interfaces.IUpgradableMachine;

public class Utils {

	public static final String[] COLOR_NAMES = {"white", "orange", "magenta", "lightBlue", "yellow", "lime", "pink", "gray", "lightGray", "cyan", "purple", "blue", "brown", "green", "red", "black"};
	public static final String[] CHATCOLORS = {"white", "gold", "light_purple", "aqua", "yellow", "green", "light_purple", "dark_gray", "gray", "dark_aqua", "dark_purple", "dark_blue", "dark_red", "dark_green", "red", "black"};

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

	public static String getRFInfoTooltip(int energy, int maxEnergy) {
		int percent = maxEnergy > 0 ? (int) ((energy / (double) maxEnergy) * 100) : 0;
		String color = "";
		if (percent >= 75)
			color += EnumChatFormatting.DARK_GREEN;
		else if (percent >= 60)
			color += EnumChatFormatting.GREEN;
		else if (percent >= 45)
			color += EnumChatFormatting.YELLOW;
		else if (percent >= 30)
			color += EnumChatFormatting.GOLD;
		else if (percent >= 15)
			color += EnumChatFormatting.RED;
		else
			color += EnumChatFormatting.DARK_RED;
		return color + localizeFormatted("rf.info", energy, maxEnergy);
	}

	public static String colorText(String text, EnumChatFormatting color) {
		return ("" + color + text).trim();
	}

	public static String decapitalizeFirstChar(String string) {
		return !Strings.isNullOrEmpty(string) ? Character.toLowerCase(string.charAt(0)) + string.substring(1) : null;
	}

	public static void dropInventory(World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			if (tile instanceof IInventory) {
				IInventory inventory = (IInventory) tile;
				for (int i = 0; i < inventory.getSizeInventory(); i++) {
					dropItemstack(world, x, y, z, inventory.getStackInSlot(i));
					inventory.setInventorySlotContents(i, null);
				}
			}
			if (tile instanceof IUpgradableMachine) {
				IUpgradableMachine machine = (IUpgradableMachine) tile;
				Set<EnumMachineUpgrades> upgrades = machine.getInstalledUpgrades();
				for (int i = 0; i < (upgrades != null ? upgrades.size() : 0); i++)
					machine.removeUpgrade();
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

	public static void dropItemstacksAtEntity(Entity entity, List<ItemStack> stacks) {
		if (!stacks.isEmpty())
			for (ItemStack stack : stacks)
				dropItemstackAtEntity(entity, stack);
	}

	public static void dropItemstackAtEntity(Entity entity, ItemStack stack) {
		if (!entity.worldObj.isRemote) {
			EntityItem entityItem = new EntityItem(entity.worldObj, entity.posX, entity.posY + entity.getEyeHeight() / 2.0F, entity.posZ, stack.copy());
			entity.worldObj.spawnEntityInWorld(entityItem);
			if (entity instanceof EntityPlayer)
				entityItem.onCollideWithPlayer((EntityPlayer) entity);
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

	public static boolean areItemStacksMergeable(ItemStack existingStack, ItemStack stackToAdd) {
		return existingStack == null || stackToAdd == null || (existingStack != null && stackToAdd != null && existingStack.isItemEqual(stackToAdd) && ItemStack.areItemStackTagsEqual(existingStack, stackToAdd));
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
				} else if (tile instanceof IInventory) {
					IInventory outputInventory = (IInventory) tile;
					ISidedInventory sidedInventory = null;
					if (tile instanceof ISidedInventory)
						sidedInventory = (ISidedInventory) tile;
					Set<Integer> allowedSlots = Sets.newHashSet();
					for (int slot = 0; slot < outputInventory.getSizeInventory(); slot++)
						allowedSlots.add(slot);
					if (sidedInventory != null) {
						Set<Integer> accessibleSlotsSet = Sets.newHashSet();
						int[] accessibleSlots = sidedInventory.getAccessibleSlotsFromSide(direction.getOpposite().ordinal());
						if (accessibleSlots != null)
							for (int slot : accessibleSlots)
								accessibleSlotsSet.add(slot);
						allowedSlots.retainAll(accessibleSlotsSet);
					}
					int stackLimit = outputInventory.getInventoryStackLimit();
					for (int slot : allowedSlots) {
						if (output == null || output.getItem() == null || output.stackSize <= 0 || (sidedInventory != null && !sidedInventory.canInsertItem(slot, output, direction.getOpposite().ordinal())))
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

	public static boolean harvestBlock(World world, int x, int y, int z, EntityPlayer player) {
		if (world.isAirBlock(x, y, z))
			return false;
		EntityPlayerMP playerMP = null;
		if (player instanceof EntityPlayerMP)
			playerMP = (EntityPlayerMP) player;
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if (!ForgeHooks.canHarvestBlock(block, player, meta))
			return false;
		if (playerMP != null) {
			BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, playerMP.theItemInWorldManager.getGameType(), playerMP, x, y, z);
			if (event.isCanceled())
				return false;
		}
		if (player.capabilities.isCreativeMode) {
			if (!world.isRemote)
				block.onBlockHarvested(world, x, y, z, meta, player);
			else
				world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) | (meta << 12));
			if (block.removedByPlayer(world, player, x, y, z, false))
				block.onBlockDestroyedByPlayer(world, x, y, z, meta);
			if (!world.isRemote && playerMP != null)
				playerMP.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
			else
				Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(2, x, y, z, Minecraft.getMinecraft().objectMouseOver.sideHit));
			return true;
		}

		if (!world.isRemote) {
			block.onBlockHarvested(world, x, y, z, meta, player);
			if (block.removedByPlayer(world, player, x, y, z, true)) {
				block.onBlockDestroyedByPlayer(world, x, y, z, meta);
				block.harvestBlock(world, player, x, y, z, meta);
			}
			if (playerMP != null)
				playerMP.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
		} else {
			world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) | (meta << 12));
			if (block.removedByPlayer(world, player, x, y, z, true))
				block.onBlockDestroyedByPlayer(world, x, y, z, meta);
			Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(2, x, y, z, Minecraft.getMinecraft().objectMouseOver.sideHit));
		}
		return true;
	}

	public static List<ItemStack> getDropsForCapsule(int tier) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		switch (tier) {
			case 3:
				list.add(new ItemStack(Items.diamond));
				list.add(new ItemStack(Items.diamond));
				list.add(new ItemStack(Items.diamond));
				list.add(new ItemStack(Items.diamond));
				list.add(new ItemStack(Items.diamond));
				list.add(new ItemStack(Items.diamond));
				list.add(new ItemStack(Items.emerald));
				list.add(new ItemStack(Items.emerald));
			case 2:
				list.add(new ItemStack(Items.gold_ingot));
				list.add(new ItemStack(Items.gold_ingot));
				list.add(new ItemStack(Items.gold_ingot));
				list.add(new ItemStack(Items.gold_ingot));
				list.add(new ItemStack(Items.gold_ingot));
				list.add(new ItemStack(Items.gold_ingot));
				list.add(new ItemStack(Items.diamond));
				list.add(new ItemStack(Items.diamond));
			case 1:
				list.add(new ItemStack(Items.gold_ingot));
				list.add(new ItemStack(Items.gold_ingot));
				list.add(new ItemStack(Items.iron_ingot));
				list.add(new ItemStack(Items.iron_ingot));
				list.add(new ItemStack(Items.iron_ingot));
				list.add(new ItemStack(Items.iron_ingot));
				list.add(new ItemStack(Items.iron_ingot));
				list.add(new ItemStack(Items.iron_ingot));
				list.add(new ItemStack(Blocks.redstone_block));
		}
		Random random = new Random();
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		if (!list.isEmpty())
			for (ItemStack stack : list) {
				if (random.nextBoolean())
					drops.add(stack);
			}
		return drops;
	}
}
