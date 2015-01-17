package buildcraftAdditions.utils;

import java.util.Collection;

import net.minecraft.block.Block;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.CraftingResult;
import buildcraft.api.recipes.IFlexibleRecipe;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.blocks.FluidBlockBase;
import buildcraftAdditions.core.Logger;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class RefineryRecipeConverter {
	public static CraftingResult<FluidStack> results[] = new CraftingResult[20];
	public static FluidStack inputs[] = new FluidStack[20];
	public static FluidStack outputs[] = new FluidStack[20];
	public static FluidStack gas[] = new FluidStack[20];

	public static void doYourThing() {
		int teller = 0;
		int fluids = FluidRegistry.getRegisteredFluids().size();
		Collection<IFlexibleRecipe<FluidStack>> recipes = BuildcraftRecipeRegistry.refinery.getRecipes();
		for (IFlexibleRecipe<FluidStack> recipe : recipes) {
			DummyFlexibleCrafter dummy = new DummyFlexibleCrafter();
			for (int fluidID = 0; fluidID < fluids; fluidID++) {
				dummy.input.setFluid(new FluidStack(fluidID, 1000));
				dummy.output.setFluid(null);
				CraftingResult<FluidStack> currentResult = recipe.craft(dummy, false);
				if (currentResult != null) {
					results[teller] = currentResult;
					dummy.output.fill(currentResult.crafted.copy(), true);
					outputs[teller] = dummy.output.getFluid();
					inputs[teller] = new FluidStack(dummy.input.getFluid().fluidID, 1000 - dummy.input.getFluidAmount());
					teller++;
					Logger.info("Buildcraft refinery recipe detected, input: " + dummy.input.getFluid().getLocalizedName() + ", output: " + dummy.output.getFluid().getLocalizedName());
				}
			}
		}
		for (int t = 0; t < teller; t++) {
			BCAFluid fluid = new BCAFluid(results[t].crafted.getFluid().getName() + "Gas");
			fluid.setDensity(-50);
			fluid.setGaseous(true);
			fluid.setIcons(results[t].crafted.getFluid().getStillIcon(), results[t].crafted.getFluid().getFlowingIcon());
			fluid.setTemperature(results[t].energyCost);
			fluid.setUnlocalizedName("fluid.gas");
			fluid.setViscosity(5);
			FluidRegistry.registerFluid(fluid);
			gas[t] = new FluidStack(fluid, outputs[t].amount);
			Block fluidblock = new FluidBlockBase(fluid);
			GameRegistry.registerBlock(fluidblock, fluid.getName() + "gasBlock");
			fluid.setBlock(fluidblock);
			if (inputs[t].getFluid().getBlock() == null) {
				fluidblock = new FluidBlockBase(inputs[t].getFluid());
				GameRegistry.registerBlock(fluidblock, fluid.getName() + "Block");
				inputs[t].getFluid().setBlock(fluidblock);
			}

			BuildcraftRecipeRegistry.refinery.removeRecipe(results[t].recipe);
			BuildcraftRecipeRegistry.refinery.addRecipe(results[t].recipe.getId() + "_GAS", inputs[t], new FluidStack(fluid, outputs[t].amount), results[t].energyCost, 0);
			BCARecipeManager.cooling.addRecipe(new FluidStack(fluid.getID(), 1), new FluidStack(outputs[t].fluidID, 1), ((float) results[t].energyCost) / 2000);
			BCARecipeManager.refinery.addRecipe(inputs[t], new FluidStack(fluid.getID(), outputs[t].amount), results[t].energyCost);
		}
	}

	public static class BCAFluid extends Fluid {

		public BCAFluid(String fluidName) {
			super(fluidName);
		}

		@Override
		public int getColor() {
			return 0xb0b3b6;
		}

		@Override
		public int getColor(FluidStack stack) {
			return getColor();
		}
	}
}
