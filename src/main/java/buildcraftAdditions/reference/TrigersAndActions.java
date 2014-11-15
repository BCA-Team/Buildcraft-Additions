package buildcraftAdditions.reference;

import buildcraft.api.statements.ITriggerExternal;
import buildcraft.api.statements.StatementManager;

import buildcraftAdditions.triggers.TriggerCanisterRequested;
import buildcraftAdditions.triggers.TriggerDoneCharging;
import buildcraftAdditions.triggers.TriggerHasEmptyCanister;
import buildcraftAdditions.triggers.TriggerHasFullCanister;
import buildcraftAdditions.triggers.TriggerProvider;
import buildcraftAdditions.triggers.TriggerReadyToCharge;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public final class TrigersAndActions {
	public static ITriggerExternal triggerCanAcceptCanister = new TriggerCanisterRequested();
	public static ITriggerExternal triggerHasEmptyCanister = new TriggerHasEmptyCanister();
	public static ITriggerExternal triggerhasFullCanister = new TriggerHasFullCanister();
	public static ITriggerExternal triggerDoneCharging = new TriggerDoneCharging();
	public static ITriggerExternal triggerReadyToCharge = new TriggerReadyToCharge();

	public static void register() {
		StatementManager.registerTriggerProvider(new TriggerProvider());
		StatementManager.registerStatement(triggerCanAcceptCanister);
		StatementManager.registerStatement(triggerHasEmptyCanister);
		StatementManager.registerStatement(triggerhasFullCanister);
		StatementManager.registerStatement(triggerDoneCharging);
		StatementManager.registerStatement(triggerReadyToCharge);
	}
}
