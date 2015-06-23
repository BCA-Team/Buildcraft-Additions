package buildcraftAdditions.compat.nei;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;

import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.api.recipe.refinery.IRefineryRecipe;
import buildcraftAdditions.client.gui.GuiRefinery;
import buildcraftAdditions.utils.Utils;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import com.google.common.collect.Lists;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class RecipeHandlerRefinery extends RecipeHandlerBase {

	@Override
	public String getRecipeID() {
		return "bcadditions:refinery";
	}

	@Override
	public String getGuiTexture() {
		return "bcadditions:textures/gui/nei/guiNEIRefinery.png";
	}

	@Override
	public String getRecipeName() {
		return Utils.localize("gui.refinery.name");
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiRefinery.class;
	}

	@Override
	public void drawBackground(int recipe) {
		this.changeToGuiTexture();
		GuiDraw.drawTexturedModalRect(0, 0, -3, 0, 161, 129);
	}

	@Override
	public void loadTransferRects() {
		//TODO: implement
	}

	@Override
	public void loadAllRecipes() {
		for (IRefineryRecipe recipe : BCARecipeManager.refinery.getRecipes())
			this.arecipes.add(new CachedRefineryRecipe(recipe));
	}

	@Override
	public void loadCraftingRecipes(FluidStack result) {
		for (IRefineryRecipe recipe : BCARecipeManager.refinery.getRecipes()) {
			if (recipe.getOutput() != null && result != null && recipe.getOutput().getFluid() == result.getFluid())
				this.arecipes.add(new CachedRefineryRecipe(recipe));
		}
	}

	@Override
	public void loadUsageRecipes(FluidStack ingredient) {
		for (IRefineryRecipe recipe : BCARecipeManager.refinery.getRecipes()) {
			if (recipe.getInput() != null && ingredient != null && recipe.getInput().getFluid() == ingredient.getFluid())
				this.arecipes.add(new CachedRefineryRecipe(recipe));
		}
	}

	@Override
	public int recipiesPerPage() {
		return 1;
	}

	@Override
	public void drawExtras(int recipe) {
		GuiDraw.drawString(Utils.localize("gui.requiredHeat") + ":" + ((CachedRefineryRecipe) this.arecipes.get(recipe)).requiredHeat, 39, 100, 0xFFCC00);
	}

	public class CachedRefineryRecipe extends CachedBaseRecipe {

		public FluidStack input;
		public FluidStack output;
		public int requiredHeat;

		private int recipeAmount = 3000;

		public CachedRefineryRecipe(IRefineryRecipe recipe) {
			this.input = recipe.getInput();
			this.output = recipe.getOutput();
			this.requiredHeat = recipe.getRequiredHeat();
		}

		@Override
		public List<PositionedFluidTank> getFluidTanks() {
			List<PositionedFluidTank> tanks = Lists.newArrayList();
			this.input.amount = 3000 - recipeAmount;
			this.output.amount = recipeAmount;
			tanks.add(new PositionedFluidTank(this.input, 3000, new Rectangle(14, 37, 16, 52), RecipeHandlerRefinery.this.getGuiTexture(), new Point(158, 0)));
			tanks.add(new PositionedFluidTank(this.output, 3000, new Rectangle(134, 37, 16, 52), RecipeHandlerRefinery.this.getGuiTexture(), new Point(158, 0)));
			return tanks;
		}

		@Override
		public void drawUpdate() {
			recipeAmount = (RecipeHandlerRefinery.this.cycleticks % 125) * 25;
		}

		@Override
		public PositionedStack getResult() {
			return null;
		}
	}
}
