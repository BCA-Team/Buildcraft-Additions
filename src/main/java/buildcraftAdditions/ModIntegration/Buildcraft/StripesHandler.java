package buildcraftAdditions.ModIntegration.Buildcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.transport.IStripesActivator;
import buildcraft.api.transport.IStripesHandler;
import buildcraft.api.transport.PipeManager;

import buildcraftAdditions.items.itemBlocks.ItemBlockKEB;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class StripesHandler {

	public static void register() {
		PipeManager.registerStripesHandler(new StripesKEBBreakHandler());
		PipeManager.registerStripesHandler(new StripesKEBPlaceHandler());
	}

	public static class StripesKEBBreakHandler implements IStripesHandler {

		@Override
		public StripesHandlerType getType() {
			return StripesHandlerType.BLOCK_BREAK;
		}

		@Override
		public boolean shouldHandle(ItemStack stack) {
			return stack != null && stack.getItem() != null && stack.getItem() instanceof ItemBlockKEB;
		}

		@Override
		public boolean handle(World world, int x, int y, int z, ForgeDirection direction, ItemStack stack, EntityPlayer player, IStripesActivator activator) {
			NBTTagCompound tag = new NBTTagCompound();
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile != null)
				tile.writeToNBT(tag);
			ItemStack stack1 = new ItemStack(ItemsAndBlocks.kebT1, 1, world.getBlockMetadata(x, y, z));
			tag.removeTag("x");
			tag.removeTag("y");
			tag.removeTag("z");
			tag.removeTag("id");
			stack1.stackTagCompound = tag;
			activator.sendItem(stack1, direction.getOpposite());
			world.setBlockToAir(x, y, z);
			return true;
		}

	}

	public static class StripesKEBPlaceHandler implements IStripesHandler {

		@Override
		public StripesHandlerType getType() {
			return StripesHandlerType.ITEM_USE;
		}

		@Override
		public boolean shouldHandle(ItemStack stack) {
			return stack != null && stack.getItem() != null && stack.getItem() instanceof ItemBlockKEB && stack.stackSize > 0;
		}

		@Override
		public boolean handle(World world, int x, int y, int z, ForgeDirection direction, ItemStack stack, EntityPlayer player, IStripesActivator activator) {
			if (world.isAirBlock(x, y, z)) {
				stack.stackSize--;
				world.setBlock(x, y, z, ItemsAndBlocks.kebT1, stack.getItemDamage(), 3);
				TileEntity tile = world.getTileEntity(x, y, z);
				if (tile != null && tile instanceof TileKineticEnergyBufferBase && stack.stackTagCompound != null) {
					NBTBase nbtBase = stack.stackTagCompound.copy();
					if (nbtBase instanceof NBTTagCompound) {
						NBTTagCompound tag = (NBTTagCompound) nbtBase;
						tag.setInteger("x", x);
						tag.setInteger("y", y);
						tag.setInteger("z", z);
						tile.readFromNBT(tag);
						return true;
					}
				}
			}
			return false;
		}

	}
}
