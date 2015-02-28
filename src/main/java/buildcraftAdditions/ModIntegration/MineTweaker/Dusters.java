package buildcraftAdditions.ModIntegration.MineTweaker;

import buildcraftAdditions.api.recipe.BCARecipeManager;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@ZenClass("mods.bcadditions.dusters")
@ModOnly("bcadditions")
public class Dusters {

	@ZenMethod
	public static void addDusting(IItemStack input, IItemStack output) {
		MineTweakerAPI.apply(new AddRecipeAction(input, output));
	}

	@ZenMethod
	public static void removeDusting(IItemStack input) {
		MineTweakerAPI.apply(new RemoveRecipeAction(input));
	}

	private static class AddRecipeAction implements IUndoableAction {
		public final IItemStack input, output;

		public AddRecipeAction(IItemStack input, IItemStack output) {
			this.input = input;
			this.output = output;
		}

		@Override
		public void apply() {
			BCARecipeManager.duster.addRecipe(MineTweakerMC.getItemStack(input), MineTweakerMC.getItemStack(output));
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BCARecipeManager.duster.removeRecipe(MineTweakerMC.getItemStack(input));
		}

		@Override
		public String describe() {
			return String.format("Adding BCA Duster recipe for %s -> %s * %s", input, output, output.getAmount());
		}

		@Override
		public String describeUndo() {
			return String.format("Removing BCA Duster recipe for %s -> %s", input, BCARecipeManager.duster.getRecipe(MineTweakerMC.getItemStack(input)).getOutput(MineTweakerMC.getItemStack(input)));
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	private static class RemoveRecipeAction implements IUndoableAction {
		public final IItemStack input;

		public RemoveRecipeAction(IItemStack input) {
			this.input = input;
		}

		@Override
		public void apply() {
			BCARecipeManager.duster.removeRecipe(MineTweakerMC.getItemStack(input));
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
			return String.format("Removing BCA Duster recipe for %s -> %s", input, BCARecipeManager.duster.getRecipe(MineTweakerMC.getItemStack(input)).getOutput(MineTweakerMC.getItemStack(input)));
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
