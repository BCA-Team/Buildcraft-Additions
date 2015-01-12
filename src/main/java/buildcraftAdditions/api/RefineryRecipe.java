package buildcraftAdditions.api;

import net.minecraftforge.fluids.Fluid;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class RefineryRecipe {
	private Fluid input, output;
	private int requiredHeat, inputAmount, outputAmount;

	public RefineryRecipe(Fluid input, int inputAmount, Fluid output, int outputAmount, int requiredHeat) {
		this.input = input;
		this.output = output;
		this.requiredHeat = requiredHeat;
		this.inputAmount = inputAmount;
		this.outputAmount = outputAmount;
	}

	public Fluid getInput() {
		return input;
	}

	public Fluid getOutput() {
		return output;
	}

	public int getRequiredHeat() {
		return requiredHeat;
	}

	public int getInputAmount() {
		return inputAmount;
	}

	public int getOutputAmount() {
		return outputAmount;
	}
}
