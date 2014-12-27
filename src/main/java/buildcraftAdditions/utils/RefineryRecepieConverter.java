package buildcraftAdditions.utils;

import java.util.Collection;
import java.util.HashMap;

import net.minecraft.block.Block;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.CraftingResult;
import buildcraft.api.recipes.IFlexibleRecipe;

import buildcraftAdditions.api.CoolingTowerRecepie;
import buildcraftAdditions.api.CoolingTowerRecepieMananger;
import buildcraftAdditions.blocks.FluidBlockBase;
import buildcraftAdditions.core.Logger;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class RefineryRecepieConverter {
	public static CraftingResult<FluidStack> results[] = new CraftingResult[20];
	public static FluidStack inputs[] = new FluidStack[20];
	public static FluidStack outputs[] = new FluidStack[20];
	public static FluidStack gas[] = new FluidStack[20];
	public static HashMap<Fluid, Fluid> coolingTowerRecepies = new HashMap<Fluid, Fluid>();

	public static void doYourThing() {
		int teller = 0;
		int fluids = FluidRegistry.getRegisteredFluids().size();
		Collection<IFlexibleRecipe<FluidStack>> recepies = BuildcraftRecipeRegistry.refinery.getRecipes();
		for (IFlexibleRecipe<FluidStack> recepie : recepies) {
			DummyFlexibleCrafter dummy = new DummyFlexibleCrafter();
			for (int fluidID = 0; fluidID < fluids; fluidID++) {
				dummy.input.setFluid(new FluidStack(fluidID, 1000));
				dummy.output.setFluid(null);
				CraftingResult<FluidStack> currentResult = recepie.craft(dummy, false);
				if (currentResult != null) {
					results[teller] = currentResult;
					dummy.output.fill(currentResult.crafted.copy(), true);
					outputs[teller] = dummy.output.getFluid();
					inputs[teller] = dummy.input.getFluid();
					teller++;
					Logger.info("Builcraft refinery recepie detected, input: " + dummy.input.getFluid().getLocalizedName() + ", output: " + dummy.output.getFluid().getLocalizedName());
				}
			}
		}
		for (int t = 0; t < teller; t++) {
			Fluid fluid = new Fluid(results[t].crafted.getFluid().getName() + "Gas");
			fluid.setDensity(-50);
			fluid.setGaseous(true);
			fluid.setIcons(results[t].crafted.getFluid().getStillIcon(), results[t].crafted.getFluid().getFlowingIcon());
			fluid.setTemperature(results[t].energyCost);
			fluid.setUnlocalizedName("fluid.gas");
			fluid.setViscosity(5);
			FluidRegistry.registerFluid(fluid);
			gas[t] = new FluidStack(fluid, outputs[t].amount);
			Block fluidblock = new FluidBlockBase(fluid);
			GameRegistry.registerBlock(fluidblock, fluid.getName()+"gasBlock");
			fluid.setBlock(fluidblock);
			if (inputs[t].getFluid().getBlock() == null) {
				fluidblock = new FluidBlockBase(inputs[t].getFluid());
				GameRegistry.registerBlock(fluidblock, fluid.getName()+"Block");
				inputs[t].getFluid().setBlock(fluidblock);
			}
			coolingTowerRecepies.put(gas[t].getFluid(), fluid);
			BuildcraftRecipeRegistry.refinery.removeRecipe(results[t].recipe);
			BuildcraftRecipeRegistry.refinery.addRecipe(results[t].recipe.getId() + "_GAS", new FluidStack(inputs[t].getFluid(), 1000 - inputs[t].amount), new FluidStack(fluid, outputs[t].amount), results[t].energyCost, 0);
			CoolingTowerRecepieMananger.registerRecepie(new CoolingTowerRecepie(fluid, outputs[t].getFluid(), ((float) results[t].energyCost) / 2000));
		}
	}
}
