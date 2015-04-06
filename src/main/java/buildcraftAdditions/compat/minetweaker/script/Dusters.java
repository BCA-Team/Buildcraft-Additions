package buildcraftAdditions.compat.minetweaker.script;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.api.recipe.duster.IDusterRecipe;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@ZenClass("mods.bcadditions.dusters")
@ModOnly("bcadditions")
public class Dusters {

	public static void register() {
		MineTweakerAPI.registerClass(Dusters.class);
	}

	@ZenMethod
	public static void addDusting(IItemStack input, IItemStack output) {
		MineTweakerAPI.apply(new AddRecipeAction(input, output));
	}

	@ZenMethod
	public static void addDusting(IOreDictEntry input, IItemStack output) {
		MineTweakerAPI.apply(new AddRecipeOreDictAction(input, output));
	}

	@ZenMethod
	public static void removeDusting(IItemStack input) {
		MineTweakerAPI.apply(new RemoveRecipeAction(input));
	}

	@ZenMethod
	public static void removeDusting(IOreDictEntry input) {
		MineTweakerAPI.apply(new RemoveRecipeOreDictAction(input));
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
			return String.format("Adding BCA Duster recipe for %s -> %s", input, output);
		}

		@Override
		public String describeUndo() {
			return String.format("Undoing \"Adding BCA Duster recipe\": Removing BCA Duster recipe for %s -> %s", input, output);
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	private static class RemoveRecipeAction implements IUndoableAction {
		public final IItemStack input;
		public IDusterRecipe dusterRecipe;

		public RemoveRecipeAction(IItemStack input) {
			this.input = input;
		}

		@Override
		public void apply() {
			ItemStack inputStack = MineTweakerMC.getItemStack(input);
			IDusterRecipe recipe = BCARecipeManager.duster.getRecipe(inputStack);
			if (recipe != null) {
				dusterRecipe = recipe;
				BCARecipeManager.duster.removeRecipe(inputStack);
			}
		}

		@Override
		public boolean canUndo() {
			return dusterRecipe != null;
		}

		@Override
		public void undo() {
			BCARecipeManager.duster.addRecipe(dusterRecipe);
		}

		@Override
		public String describe() {
			ItemStack inputStack = MineTweakerMC.getItemStack(input);
			IDusterRecipe recipe = BCARecipeManager.duster.getRecipe(inputStack);
			return String.format("Removing BCA Duster recipe for %s -> %s", input, recipe != null ? recipe.getOutput(inputStack) : "?");
		}

		@Override
		public String describeUndo() {
			return String.format("Undoing \"Removing BCA Duster recipe\": Adding BCA Duster recipe for %s (%s)", input, dusterRecipe);
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	private static class AddRecipeOreDictAction implements IUndoableAction {
		public final IOreDictEntry input;
		public final IItemStack output;

		public AddRecipeOreDictAction(IOreDictEntry input, IItemStack output) {
			this.input = input;
			this.output = output;
		}

		@Override
		public void apply() {
			BCARecipeManager.duster.addRecipe(input.getName(), MineTweakerMC.getItemStack(output));
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for (IItemStack inputStack : input.getItems())
				BCARecipeManager.duster.removeRecipe(MineTweakerMC.getItemStack(inputStack));
		}

		@Override
		public String describe() {
			return String.format("Adding BCA Duster recipe for %s -> %s", input, output);
		}

		@Override
		public String describeUndo() {
			return String.format("Undoing \"Adding BCA Duster recipe\": Removing BCA Duster recipe for %s -> %s", input, output);
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	private static class RemoveRecipeOreDictAction implements IUndoableAction {
		public final IOreDictEntry input;
		public final List<IDusterRecipe> recipes;

		public RemoveRecipeOreDictAction(IOreDictEntry input) {
			this.input = input;
			recipes = new ArrayList<IDusterRecipe>();
		}

		@Override
		public void apply() {
			for (IItemStack inputStack : input.getItems()) {
				ItemStack inputItemStack = MineTweakerMC.getItemStack(inputStack);
				IDusterRecipe recipe = BCARecipeManager.duster.getRecipe(inputItemStack);
				if (recipe != null) {
					recipes.add(recipe);
					BCARecipeManager.duster.removeRecipe(inputItemStack);
				}
			}
		}

		@Override
		public boolean canUndo() {
			return !recipes.isEmpty();
		}

		@Override
		public void undo() {
			for (IDusterRecipe recipe : recipes)
				BCARecipeManager.duster.addRecipe(recipe);
		}

		@Override
		public String describe() {
			ItemStack inputStack = MineTweakerMC.getItemStack(input);
			IDusterRecipe recipe = BCARecipeManager.duster.getRecipe(inputStack);
			return String.format("Removing BCA Duster recipe for %s -> %s", input, recipe != null ? recipe.getOutput(inputStack) : "?");
		}

		@Override
		public String describeUndo() {
			return String.format("Undoing \"Removing BCA Duster recipe\": Adding BCA Duster recipe for %s (%s)", input, recipes);
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
