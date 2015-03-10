package buildcraftAdditions.compat.minetweaker.script;

import buildcraftAdditions.api.recipe.BCARecipeManager;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@ZenClass("mods.bcadditions.Cooling")
@ModOnly("bcadditions")
public class Cooling {

	public static void register() {
		MineTweakerAPI.registerClass(Cooling.class);
	}

	@ZenMethod
	public static void addCoolingRecipe(ILiquidStack input, ILiquidStack output, float heat) {
		MineTweakerAPI.apply(new AddRecipeAction(input, output, heat));
	}

	@ZenMethod
	public static void removeCoolingRecipe(ILiquidStack input) {
		MineTweakerAPI.apply(new RemoveRecipeAction(input));
	}

	private static class AddRecipeAction implements IUndoableAction {
		private final ILiquidStack input;
		private final ILiquidStack output;
		private final float heat;

		public AddRecipeAction(ILiquidStack input, ILiquidStack output, float heat) {
			this.input = input;
			this.output = output;
			this.heat = heat;
		}

		@Override
		public void apply() {
			BCARecipeManager.cooling.addRecipe(MineTweakerMC.getLiquidStack(input), MineTweakerMC.getLiquidStack(output), heat);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BCARecipeManager.cooling.removeRecipe(MineTweakerMC.getLiquidStack(input));
		}

		@Override
		public String describe() {
			return String.format("Adding BCA Cooling Tower recipe for %s -> %s", input, output);
		}

		@Override
		public String describeUndo() {
			return String.format("Removing BCA Cooling Tower recipe for %s -> %s", input, output);
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	private static class RemoveRecipeAction implements IUndoableAction {
		private final ILiquidStack input;

		public RemoveRecipeAction(ILiquidStack input) {
			this.input = input;
		}

		@Override
		public void apply() {
			BCARecipeManager.cooling.removeRecipe(MineTweakerMC.getLiquidStack(input));
		}

		@Override
		public boolean canUndo() {
			return false;
		}

		@Override
		public void undo() {

		}

		@Override
		public String describe() {
			return String.format("Removing BCA Cooling Tower recipe for %s -> %s", input, BCARecipeManager.cooling.getRecipe(MineTweakerMC.getLiquidStack(input)).getOutput().getLocalizedName());
		}

		@Override
		public String describeUndo() {
			return null;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
