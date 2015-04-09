package buildcraftAdditions.inventories.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import buildcraftAdditions.api.networking.MessageByteBuff;
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

}
