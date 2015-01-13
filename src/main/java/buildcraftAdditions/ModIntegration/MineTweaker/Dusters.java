package buildcraftAdditions.ModIntegration.MineTweaker;

import net.minecraft.item.ItemStack;

import buildcraftAdditions.api.recipe.BCARecipeManager;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
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
		MineTweakerAPI.apply(new RemoveRecipeAction( input));
	}

	public static ItemStack toStack(IItemStack iStack) {
		if (iStack == null)
			return null;
		else {
			Object internal = iStack.getInternal();
			return (ItemStack) internal;
		}
	}

	private static class AddRecipeAction implements IUndoableAction {
		public IItemStack input;
		public IItemStack output;

		public AddRecipeAction(IItemStack input, IItemStack output) {
			this.input = input;
			this.output = output;
		}

		@Override
		public void apply() {
			BCARecipeManager.duster.addRecipe(toStack(input), toStack(output));
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BCARecipeManager.duster.removeRecipe(toStack(input));
		}

		@Override
		public String describe() {
			return "Adding duster recipe for " + input.getDisplayName() + "with an output of " + output.getDisplayName() + "* " + output.getAmount();
		}

		@Override
		public String describeUndo() {
			return "Removing duster recipe with as input " + input.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	private static class RemoveRecipeAction implements IUndoableAction {
		IItemStack input;

		public RemoveRecipeAction(IItemStack input) {
			this.input = input;
		}

		@Override
		public void apply() {
			BCARecipeManager.duster.removeRecipe(toStack(input));
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
			return "Removing dusting recipe with input " + input.getDisplayName();
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
