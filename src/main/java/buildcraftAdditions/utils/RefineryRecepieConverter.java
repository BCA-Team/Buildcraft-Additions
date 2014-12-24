package buildcraftAdditions.utils;

import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.CraftingResult;
import buildcraft.api.recipes.IFlexibleRecipe;

import buildcraftAdditions.blocks.FluidBlockBase;
import buildcraftAdditions.core.Logger;
import buildcraftAdditions.items.Tools.ItemTextureDuplicate;
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
			//fluid.setGaseous(true);
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
			ItemStack stack = null;
			FluidContainerRegistry.FluidContainerData dataSet[] = FluidContainerRegistry.getRegisteredFluidContainerData();
			for (FluidContainerRegistry.FluidContainerData data : dataSet) {
				if (data.fluid.getFluid() == inputs[t].getFluid() && data.emptyContainer.getItem() == Items.bucket)
					stack = new ItemStack(new ItemTextureDuplicate(data.filledContainer.getUnlocalizedName(), data.filledContainer.getItem().getIconFromDamage(0), fluid));
			}
			boolean test = false;
			if (stack != null)
				test = FluidContainerRegistry.registerFluidContainer(fluid, stack, new ItemStack(Items.bucket));
			if (test)
				Logger.info("FALSE");
			else Logger.info("TRUE");
			BuildcraftRecipeRegistry.refinery.removeRecipe(results[t].recipe);
			BuildcraftRecipeRegistry.refinery.addRecipe(results[t].recipe.getId() + "_GAS", new FluidStack(inputs[t].getFluid(), 1000 - inputs[t].amount), new FluidStack(fluid, outputs[t].amount), results[t].energyCost, 0);
			Logger.info("Recepie replaced");
		}
	}
}
