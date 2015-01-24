package buildcraftAdditions.ModIntegration.nei;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.api.recipe.duster.IDusterRecipe;
import buildcraftAdditions.utils.Utils;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class DustingRecipeHandler extends TemplateRecipeHandler {

	private static final ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/nei/dusting.png");

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(71, 24, 24, 17), getRecipeID()));
	}

	@Override
	public void drawBackground(int recipe) {
		GL11.glColor4f(1, 1, 1, 1);
		changeTexture(getGuiTexture());
		drawTexturedModalRect(0, 0, 0, 0, 166, 65);
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals(getRecipeID())) {
			for (IDusterRecipe recipe : BCARecipeManager.duster.getRecipes()) {
				for (ItemStack stack : recipe.getInputs())
					arecipes.add(new CachedDustingRecipe(stack, recipe.getOutput(stack)));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (IDusterRecipe recipe : BCARecipeManager.duster.getRecipes()) {
			for (ItemStack stack : recipe.getInputs()) {
				if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getOutput(stack), result)) {
					arecipes.add(new CachedDustingRecipe(stack, recipe.getOutput(stack)));
				}
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		for (IDusterRecipe recipe : BCARecipeManager.duster.getRecipes()) {
			for (ItemStack stack : recipe.getInputs()) {
				if (NEIServerUtils.areStacksSameTypeCrafting(stack, ingredient)) {
					CachedDustingRecipe cRecipe = new CachedDustingRecipe(stack, recipe.getOutput(stack));
					cRecipe.setIngredientPermutation(Collections.singletonList(cRecipe.input), ingredient);
					arecipes.add(cRecipe);
				}
			}
		}
	}

	@Override
	public String getGuiTexture() {
		return texture.toString();
	}

	@Override
	public String getRecipeName() {
		return Utils.localize("gui.nei.dusting");
	}

	public String getRecipeID() {
		return "bcadditions:dusting";
	}

	public class CachedDustingRecipe extends CachedRecipe {

		PositionedStack input, output;

		public CachedDustingRecipe(ItemStack ingred, ItemStack result) {
			ingred.stackSize = 1;
			input = new PositionedStack(ingred, 28, 24);
			output = new PositionedStack(result, 122, 24);
		}

		@Override
		public PositionedStack getResult() {
			return output;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(input));
		}
	}
}
