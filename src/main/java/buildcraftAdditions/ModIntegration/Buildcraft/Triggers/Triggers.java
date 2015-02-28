package buildcraftAdditions.ModIntegration.Buildcraft.Triggers;

import buildcraft.api.statements.ITriggerExternal;
import buildcraft.api.statements.StatementManager;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class Triggers {
	public static ITriggerExternal triggerCanAcceptCanister = new TriggerCanisterRequested();
	public static ITriggerExternal triggerHasEmptyCanister = new TriggerHasEmptyCanister();
	public static ITriggerExternal triggerhasFullCanister = new TriggerHasFullCanister();
	public static ITriggerExternal triggerDoneCharging = new TriggerDoneCharging();
	public static ITriggerExternal triggerReadyToCharge = new TriggerReadyToCharge();

	public static ITriggerExternal KEBCharged = new TriggerKEBCharged();
	public static ITriggerExternal KEBUnder100 = new TriggerKEBUnder100();
	public static ITriggerExternal KEBUnder75 = new TriggerKEBUnder75();
	public static ITriggerExternal KEBUnder50 = new TriggerKEBUnder50();
	public static ITriggerExternal KEBUnder25 = new TriggerKEBUnder25();
	public static ITriggerExternal KEBEmpty = new TriggerKEBEmpty();
	public static ITriggerExternal KEBEngineControl = new TriggerKEBEngineControl();

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
