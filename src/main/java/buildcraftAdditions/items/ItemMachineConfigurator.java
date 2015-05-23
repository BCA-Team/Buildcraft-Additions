package buildcraftAdditions.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.api.configurableOutput.IConfigurableOutput;
import buildcraftAdditions.items.bases.ItemBase;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.interfaces.IUpgradableMachine;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemMachineConfigurator extends ItemBase {

	public ItemMachineConfigurator() {
		super("machineConfigurator");
		setFull3D();
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
		if (!player.isSneaking())
			return false;
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile != null && (tile instanceof IConfigurableOutput || tile instanceof IUpgradableMachine))
				player.openGui(BuildcraftAdditions.instance, Variables.Gui.MACHINE_CONFIGURATOR.ordinal(), world, x, y, z);
		}
		return true;
	}
}
