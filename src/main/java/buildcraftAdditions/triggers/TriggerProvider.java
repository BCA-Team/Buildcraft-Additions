package buildcraftAdditions.triggers;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.ITriggerExternal;
import buildcraft.api.statements.ITriggerInternal;
import buildcraft.api.statements.ITriggerProvider;

import buildcraftAdditions.tileEntities.TileChargingStation;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;
import buildcraftAdditions.reference.TrigersAndActions;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TriggerProvider implements ITriggerProvider {
	@Override
	public Collection<ITriggerInternal> getInternalTriggers(IStatementContainer container) {
		return null;
	}

	@Override
	public Collection<ITriggerExternal> getExternalTriggers(ForgeDirection side, TileEntity tile) {
		Collection<ITriggerExternal> triggers = new ArrayList<ITriggerExternal>();
		if (tile instanceof TileFluidicCompressor) {
			triggers.add(TrigersAndActions.triggerCanAcceptCanister);
			triggers.add(TrigersAndActions.triggerHasEmptyCanister);
			triggers.add(TrigersAndActions.triggerhasFullCanister);
		} else if (tile instanceof TileChargingStation) {
			triggers.add(TrigersAndActions.triggerDoneCharging);
			triggers.add(TrigersAndActions.triggerReadyToCharge);
		}
		return triggers;
	}
}
