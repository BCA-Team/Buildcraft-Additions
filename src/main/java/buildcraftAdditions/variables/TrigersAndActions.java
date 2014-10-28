package buildcraftAdditions.variables;

import buildcraft.api.gates.ITrigger;

import buildcraftAdditions.triggers.TriggerCanisterRequested;
import buildcraftAdditions.triggers.TriggerDoneCharging;
import buildcraftAdditions.triggers.TriggerHasEmptyCanister;
import buildcraftAdditions.triggers.TriggerHasFullCanister;
import buildcraftAdditions.triggers.TriggerReadyToCharge;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public final class TrigersAndActions {
	public static ITrigger triggerCanAcceptCanister = new TriggerCanisterRequested();
	public static ITrigger triggerHasEmptyCanister = new TriggerHasEmptyCanister();
	public static ITrigger triggerhasFullCanister = new TriggerHasFullCanister();
	public static ITrigger triggerDoneCharging = new TriggerDoneCharging();
	public static ITrigger triggerReadyToCharge = new TriggerReadyToCharge();
}
