package buildcraftAdditions.compat.buildcraft.triggers;

import buildcraft.api.statements.ITriggerExternal;
import buildcraft.api.statements.StatementManager;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class Triggers {
	public static final ITriggerExternal triggerCanAcceptCanister = new TriggerCanisterRequested();
	public static final ITriggerExternal triggerHasEmptyCanister = new TriggerHasEmptyCanister();
	public static final ITriggerExternal triggerhasFullCanister = new TriggerHasFullCanister();
	public static final ITriggerExternal triggerDoneCharging = new TriggerDoneCharging();
	public static final ITriggerExternal triggerReadyToCharge = new TriggerReadyToCharge();

	public static final ITriggerExternal KEBCharged = new TriggerKEBCharged();
	public static final ITriggerExternal KEBUnder100 = new TriggerKEBUnder100();
	public static final ITriggerExternal KEBUnder75 = new TriggerKEBUnder75();
	public static final ITriggerExternal KEBUnder50 = new TriggerKEBUnder50();
	public static final ITriggerExternal KEBUnder25 = new TriggerKEBUnder25();
	public static final ITriggerExternal KEBEmpty = new TriggerKEBEmpty();
	public static final ITriggerExternal KEBEngineControl = new TriggerKEBEngineControl();

	public static void register() {
		StatementManager.registerTriggerProvider(new TriggerProvider());
		StatementManager.registerStatement(triggerCanAcceptCanister);
		StatementManager.registerStatement(triggerHasEmptyCanister);
		StatementManager.registerStatement(triggerhasFullCanister);
		StatementManager.registerStatement(triggerDoneCharging);
		StatementManager.registerStatement(triggerReadyToCharge);
		StatementManager.registerStatement(KEBCharged);
		StatementManager.registerStatement(KEBUnder100);
		StatementManager.registerStatement(KEBUnder75);
		StatementManager.registerStatement(KEBUnder50);
		StatementManager.registerStatement(KEBUnder25);
		StatementManager.registerStatement(KEBEmpty);
		StatementManager.registerStatement(KEBEngineControl);
	}
}
