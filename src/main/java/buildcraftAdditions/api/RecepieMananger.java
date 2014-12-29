package buildcraftAdditions.api;

import java.util.ArrayList;

import net.minecraftforge.fluids.Fluid;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class RecepieMananger {
	private static ArrayList<CoolingTowerRecepie> coolingTowerRecepies = new ArrayList<CoolingTowerRecepie>();
	private static ArrayList<RefineryRecepie> refineryRecepies = new ArrayList<RefineryRecepie>();

	public static boolean registerRecepie(CoolingTowerRecepie recepie) {
		if (coolingTowerRecepies.contains(recepie))
			return false;
		coolingTowerRecepies.add(recepie);
		return true;
	}

	public static boolean registerRecepie(RefineryRecepie recepie) {
		if (refineryRecepies.contains(recepie))
			return false;
		refineryRecepies.add(recepie);
		return true;
	}

	public static CoolingTowerRecepie getCoolingTowerRecepie(Fluid input) {
		for (CoolingTowerRecepie recepie : coolingTowerRecepies) {
			if (recepie.getInput() == input)
				return recepie;
		}
		return null;
	}

	public static RefineryRecepie getRefineryRecepie(Fluid input) {
		for (RefineryRecepie recepie : refineryRecepies) {
			if (recepie.getInput() == input)
				return recepie;
		}
		return null;
	}
}
