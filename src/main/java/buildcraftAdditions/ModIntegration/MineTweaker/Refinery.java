package buildcraftAdditions.ModIntegration.MineTweaker;

import buildcraftAdditions.api.recipe.BCARecipeManager;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.bcadditions.Refinery")
@ModOnly("bcadditions")
public class Refinery {

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
            return String.format("Adding BCA refinery recipe for %s -> %s", input, output);
        }

        @Override
        public String describeUndo() {
            return String.format("Removing BCA refinery recipe for %S -> %s", input, output);
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
            BCARecipeManager.refinery.removeRecipe(MineTweakerMC.getLiquidStack(input));
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
            return String.format("Removing BCA refinery recipe for %S -> %s", input, BCARecipeManager.refinery.getRecipe(MineTweakerMC.getLiquidStack(input)).getOutput().getLocalizedName());
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
