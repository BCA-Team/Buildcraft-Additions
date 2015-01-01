package buildcraftAdditions.api;

import net.minecraftforge.fluids.Fluid;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class CoolingTowerRecepie {
	private Fluid input, output;
	private double heat;

	public CoolingTowerRecepie(Fluid input, Fluid output, double heat) {
		this.input = input;
		this.output = output;
		this.heat = heat;
	}

	public Fluid getInput() {
		return input;
	}

	public Fluid getOutput() {
		return output;
	}

	public double getHeat() {
		return heat;
	}
}
