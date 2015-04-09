package buildcraftAdditions.compat.minetweaker.script;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.api.recipe.refinery.IRefineryRecipe;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.bcadditions.refinery")
@ModOnly("bcadditions")
public class Refinery {

	public static void register() {
		MineTweakerAPI.registerClass(Refinery.class);
	}

	@ZenMethod
	public static void addRefineryRecipe(ILiquidStack input, ILiquidStack output, int requiredHeat) {
		MineTweakerAPI.apply(new AddRecipeAction(input, output, requiredHeat));
	}

	@ZenMethod
	public static void removeRefineryRecipe(ILiquidStack input) {
		MineTweakerAPI.apply(new RemoveRecipeAction(input));
	}

	private static class AddRecipeAction implements IUndoableAction {
		private final ILiquidStack input;
		private final ILiquidStack output;
		private final int requiredHeat;

		public AddRecipeAction(ILiquidStack input, ILiquidStack output, int requiredHeat) {
			this.input = input;
			this.output = output;
			this.requiredHeat = requiredHeat;
		}

		@Override
		public void apply() {
			BCARecipeManager.refinery.addRecipe(MineTweakerMC.getLiquidStack(input), MineTweakerMC.getLiquidStack(output), requiredHeat);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BCARecipeManager.refinery.removeRecipe(MineTweakerMC.getLiquidStack(input));
		}

		@Override
		public String describe() {
			return String.format("Adding BCA Refinery recipe for %s -> %s : %s", input, output, requiredHeat);
		}

		@Override
		public String describeUndo() {
			return String.format("Undoing \"Adding BCA Refinery recipe\": Removing BCA Refinery recipe for %s -> %s : %s", input, output, requiredHeat);
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	private static class RemoveRecipeAction implements IUndoableAction {
		private final ILiquidStack input;
		private IRefineryRecipe refineryRecipe;

		public RemoveRecipeAction(ILiquidStack input) {
			this.input = input;
		}

		@Override
		public void apply() {
			BCARecipeManager.refinery.removeRecipe(MineTweakerMC.getLiquidStack(input));
		}

		@Override
		public boolean canUndo() {
			return refineryRecipe != null;
		}

		@Override
		public void undo() {
			BCARecipeManager.refinery.addRecipe(refineryRecipe);
		}

		@Override
		public String describe() {
			IRefineryRecipe recipe = BCARecipeManager.refinery.getRecipe(MineTweakerMC.getLiquidStack(input));
			return String.format("Removing BCA Refinery recipe for %s -> %s mB of %s : %s", input, recipe != null ? recipe.getOutput().amount : "?", recipe != null ? recipe.getOutput().getUnlocalizedName() : "?", recipe.getRequiredHeat());
		}

		@Override
		public String describeUndo() {
			return String.format("Undoing \"Removing BCA Refinery recipe\": Adding BCA Refinery recipe for %s (%s)", input, refineryRecipe);
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
