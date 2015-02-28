package buildcraftAdditions.ModIntegration.Buildcraft.Triggers;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.IStatementParameter;

import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TriggerKEBEmpty extends BasicTrigger {

	public TriggerKEBEmpty() {
		super("KEBEmpty", "empty");
	}

	@Override
	public boolean isTriggerActive(TileEntity target, ForgeDirection side, IStatementContainer source, IStatementParameter[] parameters) {
		return target instanceof TileKineticEnergyBufferBase && ((TileKineticEnergyBufferBase) target).getEnergyLevel() == 0;
	}
}
