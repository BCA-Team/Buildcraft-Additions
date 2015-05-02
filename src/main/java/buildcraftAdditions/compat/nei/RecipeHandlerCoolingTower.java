package buildcraftAdditions.compat.nei;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import buildcraft.api.fuels.ICoolant;
import buildcraft.energy.fuels.CoolantManager;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.api.recipe.refinery.ICoolingTowerRecipe;
import buildcraftAdditions.client.gui.GuiCoolingTower;
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
public class RecipeHandlerCoolingTower extends RecipeHandlerBase {

	public static List<Fluid> coolants = Lists.newArrayList();

	@Override
	public String getRecipeID() {
		return "bcadditions:coolingTower";
	}

	@Override
	public String getGuiTexture() {
		return "bcadditions:textures/gui/nei/guiNEICoolingTower.png";
	}

	@Override
	public String getRecipeName() {
		return Utils.localize("gui.coolingTower.name");
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiCoolingTower.class;
	}

	@Override
	public void drawBackground(int recipe) {
		this.changeToGuiTexture();
		GuiDraw.drawTexturedModalRect(0, 0, -3, 0, 161, 157);
	}

	@Override
	public void loadTransferRects() {
		//TODO: implement
	}

	@Override
	public void loadAllRecipes() {
		for (ICoolingTowerRecipe recipe : BCARecipeManager.cooling.getRecipes())
			this.arecipes.add(new CachedCoolingTowerRecipe(recipe));
	}

	@Override
	public void loadCraftingRecipes(FluidStack result) {
		for (ICoolingTowerRecipe recipe : BCARecipeManager.cooling.getRecipes()) {
			if (recipe.getOutput() != null && result != null && recipe.getOutput().getFluid() == result.getFluid())
				this.arecipes.add(new CachedCoolingTowerRecipe(recipe));
		}
	}

	@Override
	public void loadUsageRecipes(FluidStack ingredient) {
		for (ICoolingTowerRecipe recipe : BCARecipeManager.cooling.getRecipes()) {
			if (recipe.getInput() != null && ingredient != null && recipe.getInput().getFluid() == ingredient.getFluid())
				this.arecipes.add(new CachedCoolingTowerRecipe(recipe));
		}
	}

	@Override
	public int recipiesPerPage() {
		return 1;
	}

	public class CachedCoolingTowerRecipe extends CachedBaseRecipe {

		public FluidStack input;
		public FluidStack output;
		public float heat;

		private int coolantI = 0;
		private int coolantAmount = 10000;
		private int recipeAmount = 2000;

		public CachedCoolingTowerRecipe(ICoolingTowerRecipe recipe) {
			this.input = recipe.getInput();
			this.output = recipe.getOutput();
			this.heat = recipe.getHeat();
			convertCoolants();
		}

		public void convertCoolants() {
			if (!coolants.isEmpty())
				return;
			for (ICoolant coolant : CoolantManager.INSTANCE.getCoolants())
				coolants.add(coolant.getFluid());
		}

		@Override
		public List<PositionedFluidTank> getFluidTanks() {
			List<PositionedFluidTank> tanks = Lists.newArrayList();
			this.input.amount = 2000 - recipeAmount;
			this.output.amount = recipeAmount;
			tanks.add(new PositionedFluidTank(this.input, 2000, new Rectangle(21, 27, 16, 52), RecipeHandlerCoolingTower.this.getGuiTexture(), new Point(158, 0)));
			tanks.add(new PositionedFluidTank(this.output, 2000, new Rectangle(127, 27, 16, 52), RecipeHandlerCoolingTower.this.getGuiTexture(), new Point(158, 0)));
			tanks.add(new PositionedFluidTank(new FluidStack(coolants.get(coolantI), coolantAmount), 10000, new Rectangle(74, 60, 16, 52), RecipeHandlerCoolingTower.this.getGuiTexture(), new Point(158, 0)));
			return tanks;
		}

		@Override
		public void drawUpdate() {
			coolantI = RecipeHandlerCoolingTower.this.cycleticks % (40 * coolants.size()) / 40;
			coolantAmount = 10000 - (RecipeHandlerCoolingTower.this.cycleticks % 200) * 50;
			recipeAmount = (RecipeHandlerCoolingTower.this.cycleticks % 100) * 25;
		}

		@Override
		public PositionedStack getResult() {
			return null;
		}
	}
}
