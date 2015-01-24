package buildcraftAdditions.utils;

import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.CraftingResult;
import buildcraft.api.recipes.IFlexibleRecipe;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.blocks.FluidBlockBase;
import buildcraftAdditions.core.BucketHandler;
import buildcraftAdditions.core.Logger;
import buildcraftAdditions.items.ItemBucketBCA;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class RefineryRecipeConverter {
	private static CraftingResult<FluidStack>[] results;
	public static FluidStack[] inputs, outputs, gasses;

	public static void doYourThing() {
		int teller = 0;
		int fluids = FluidRegistry.getRegisteredFluids().size();

		results = new CraftingResult[fluids];
		inputs = new FluidStack[fluids];
		outputs = new FluidStack[fluids];
		gasses = new FluidStack[fluids];

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
			BCAGasFluid fluid = new BCAGasFluid(results[t].crafted.getFluid().getName() + "Gas", results[t].crafted.getFluid());
			fluid.setDensity(-50);
			fluid.setGaseous(true);
			fluid.setIcons(results[t].crafted.getFluid().getStillIcon(), results[t].crafted.getFluid().getFlowingIcon());
			fluid.setTemperature(results[t].energyCost);
			fluid.setUnlocalizedName(results[t].crafted.getFluid().getUnlocalizedName().replaceFirst("fluid\\.", "") + ".gas");
			fluid.setViscosity(5);
			FluidRegistry.registerFluid(fluid);
			gasses[t] = new FluidStack(fluid, outputs[t].amount);
			Block fluidBlock = new FluidBlockBase(fluid);
			GameRegistry.registerBlock(fluidBlock, fluid.getName() + "Block");
			fluid.setBlock(fluidBlock);
			Item bucket = new ItemBucketBCA(fluid);
			GameRegistry.registerItem(bucket, fluid.getName() + "Bucket");
			BucketHandler.registerBucket(fluid, new ItemStack(bucket));
			if (inputs[t].getFluid().getBlock() == null) {
				fluidBlock = new FluidBlockBase(inputs[t].getFluid());
				GameRegistry.registerBlock(fluidBlock, inputs[t].getFluid().getName() + "Block");
			}

			BuildcraftRecipeRegistry.refinery.removeRecipe(results[t].recipe);
			BuildcraftRecipeRegistry.refinery.addRecipe(results[t].recipe.getId() + "_GAS", inputs[t], new FluidStack(fluid, outputs[t].amount), results[t].energyCost, 0);
			BCARecipeManager.cooling.addRecipe(new FluidStack(fluid.getID(), 1), new FluidStack(outputs[t].fluidID, 1), ((float) results[t].energyCost) / 2000);
			BCARecipeManager.refinery.addRecipe(inputs[t], new FluidStack(fluid.getID(), outputs[t].amount), results[t].energyCost);
		}
	}

	public static class BCAGasFluid extends Fluid {

		private final FluidStack fluid;

		public BCAGasFluid(String fluidName, Fluid fluid) {
			super(fluidName);
			this.fluid = new FluidStack(fluid.getID(), FluidContainerRegistry.BUCKET_VOLUME);
		}

		@Override
		public int getColor() {
			return 0xb0b3b6;
		}

		@Override
		public int getColor(FluidStack stack) {
			return getColor();
		}

		@Override
		public String getLocalizedName(FluidStack stack) {
			return Utils.localizeFormatted("fluid.gas.name", fluid.getLocalizedName());
		}
	}
}
