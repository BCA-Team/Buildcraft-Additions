package buildcraftAdditions.inventories.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import buildcraftAdditions.api.networking.MessageByteBuff;
import buildcraftAdditions.inventories.slots.SlotFake;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.utils.PlayerUtils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerKEB extends ContainerBase<TileKineticEnergyBufferBase> {

	private int energy;

	public ContainerKEB(EntityPlayer player, TileKineticEnergyBufferBase tile) {
		super(player.inventory, tile);
		if (PlayerUtils.playerMatches(tile, player))
			tile.destroyer = player;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (energy != inventory.energy && crafters != null) {
			MessageByteBuff msg = new MessageByteBuff(inventory);
			for (Object o : crafters)
				if (o != null && o instanceof EntityPlayerMP)
					PacketHandler.instance.sendTo(msg, (EntityPlayerMP) o);
		}
		energy = inventory.energy;
	}

	@Override
	protected void addPlayerInventory(int x, int y) {

	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		return null;
	}

	@Override
	public ItemStack slotClick(int slotNum, int mouseButton, int modifier, EntityPlayer player) {
		return null;
	}

	@Override
	protected Slot addSlotToContainer(Slot slot) {
		return null;
	}

	@Override
	public Slot getSlot(int id) {
		return new SlotFake(null, -5, -5, 0);
	}

}
