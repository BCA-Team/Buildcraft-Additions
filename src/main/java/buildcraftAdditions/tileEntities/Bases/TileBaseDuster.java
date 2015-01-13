package buildcraftAdditions.tileEntities.Bases;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;

import buildcraftAdditions.api.DusterRecipes;

import eureka.api.EurekaKnowledge;
import io.netty.buffer.ByteBuf;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileBaseDuster extends TileBase implements ISidedInventory {
	public int progress;
	private String key;

	public TileBaseDuster(String key) {
		this.key = key;
	}

	public void makeProgress(EntityPlayer player) {
		if (getStackInSlot(0) != null && DusterRecipes.dusting().hasDustingResult(getStackInSlot(0))) {
			progress++;
			if (progress == 8) {
				dust();
				progress = 0;
				makeProgress(player, key);
			}
		}
	}

	@Override
	public boolean canUpdate() {
		return false;
	}

	public abstract void dust();

	public void makeProgress(EntityPlayer player, String key) {
		EurekaKnowledge.makeProgress(player, key, 1);
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		return buf;
	}
}
