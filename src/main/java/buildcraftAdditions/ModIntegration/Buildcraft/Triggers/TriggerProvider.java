package buildcraftAdditions.ModIntegration.Buildcraft.Triggers;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.ITriggerExternal;
import buildcraft.api.statements.ITriggerInternal;
import buildcraft.api.statements.ITriggerProvider;

import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.tileEntities.TileChargingStation;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;
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
			triggers.add(Triggers.triggerCanAcceptCanister);
			triggers.add(Triggers.triggerHasEmptyCanister);
			triggers.add(Triggers.triggerhasFullCanister);
		} else if (tile instanceof TileChargingStation) {
			triggers.add(Triggers.triggerDoneCharging);
			triggers.add(Triggers.triggerReadyToCharge);
		} else if (tile instanceof TileKineticEnergyBufferBase) {
			triggers.add(Triggers.KEBCharged);
			triggers.add(Triggers.KEBUnder100);
			triggers.add(Triggers.KEBUnder75);
			triggers.add(Triggers.KEBUnder50);
			triggers.add(Triggers.KEBUnder25);
			triggers.add(Triggers.KEBEmpty);
			triggers.add(Triggers.KEBEngineControl);
		}
		return triggers;
	}
}
