package buildcraftAdditions.inventories.containers;

import buildcraftAdditions.api.networking.ISynchronizedTile;
import buildcraftAdditions.api.networking.MessageByteBuff;
import buildcraftAdditions.networking.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerMachineConfigurator extends ContainerBase<TileEntity> {

	public ContainerMachineConfigurator(EntityPlayer player, TileEntity tile) {
		super(player, tile);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (inventory instanceof ISynchronizedTile && crafters != null) {
			MessageByteBuff msg = new MessageByteBuff((ISynchronizedTile) inventory);
			for (Object o : crafters)
				if (o != null && o instanceof EntityPlayerMP)
					PacketHandler.instance.sendTo(msg, (EntityPlayerMP) o);
		}
	}

}
