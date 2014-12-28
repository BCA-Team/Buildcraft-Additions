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
public class CoolingTowerRecepieMananger {
	private static ArrayList<CoolingTowerRecepie> recepies = new ArrayList<CoolingTowerRecepie>();

	public static boolean registerRecepie(CoolingTowerRecepie recepie) {
		if (recepies.contains(recepie))
			return false;
		recepies.add(recepie);
		return true;
	}

	public static CoolingTowerRecepie getRecepie(Fluid input) {
		for (CoolingTowerRecepie recepie : recepies) {
			if (recepie.input == input)
				return recepie;
		}
		return null;
	}
}
