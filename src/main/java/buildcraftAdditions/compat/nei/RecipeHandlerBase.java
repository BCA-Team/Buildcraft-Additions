package buildcraftAdditions.compat.nei;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidContainerItem;

import buildcraftAdditions.utils.RenderUtils;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIClientConfig;
import codechicken.nei.PositionedStack;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class RecipeHandlerBase extends TemplateRecipeHandler implements ICraftingHandler, IUsageHandler {

	public abstract String getRecipeID();

	public String getRecipeSubName() {
		return null;
	}

	public void changeToGuiTexture() {
		GuiDraw.changeTexture(this.getGuiTexture());
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public void addTransferRect(int x, int y, int width, int height) {
		this.transferRects.add(new RecipeTransferRect(new Rectangle(x, y, width, height), this.getRecipeID()));
	}

	@Override
	public void drawForeground(int recipe) {
		super.drawForeground(recipe);
		this.drawFluidTanks(recipe);
		if (recipe % this.recipiesPerPage() == 0 && this.getRecipeSubName() != null) {
			GuiDraw.drawStringC(this.getRecipeSubName(), 83, -2, 0x404040, false);
		}
		this.changeToGuiTexture();
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("liquid"))
			this.loadCraftingRecipes((FluidStack) results[0]);
		else if (outputId.equals(this.getRecipeID()))
			this.loadAllRecipes();
		else
			super.loadCraftingRecipes(outputId, results);
	}

	public void loadAllRecipes() {

	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		FluidStack fluid = getFluidStackFromItemStack(result);
		if (fluid != null)
			this.loadCraftingRecipes(fluid);
	}

	public void loadCraftingRecipes(FluidStack result) {

	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId.equals("liquid"))
			this.loadUsageRecipes((FluidStack) ingredients[0]);
		else
			super.loadUsageRecipes(inputId, ingredients);
	}

	@Override
	public void loadUsageRecipes(ItemStack ingred) {
		FluidStack fluid = getFluidStackFromItemStack(ingred);
		if (fluid != null)
			this.loadUsageRecipes(fluid);
	}

	public void loadUsageRecipes(FluidStack ingredient) {

	}

	protected boolean transferFluidTank(GuiRecipe guiRecipe, int recipe, boolean usage) {
		CachedBaseRecipe crecipe = (CachedBaseRecipe) this.arecipes.get(recipe);
		Point mousepos = GuiDraw.getMousePosition();
		Point offset = guiRecipe.getRecipePosition(recipe);
		Point relMouse = new Point(mousepos.x - (guiRecipe.width - 176) / 2 - offset.x, mousepos.y - (guiRecipe.height - 166) / 2 - offset.y);

		if (crecipe.getFluidTanks() != null) {
			for (PositionedFluidTank tank : crecipe.getFluidTanks()) {
				if (tank.position.contains(relMouse))
					return tank.transfer(usage);
			}
		}

		return false;
	}

	public void drawFluidTanks(int recipe) {
		CachedBaseRecipe crecipe = (CachedBaseRecipe) this.arecipes.get(recipe);
		if (crecipe.getFluidTanks() != null) {
			crecipe.drawUpdate();
			for (PositionedFluidTank fluidTank : crecipe.getFluidTanks())
				fluidTank.draw();
		}
	}

	@Override
	public List<String> handleTooltip(GuiRecipe guiRecipe, List<String> currenttip, int recipe) {
		super.handleTooltip(guiRecipe, currenttip, recipe);
		CachedBaseRecipe crecipe = (CachedBaseRecipe) this.arecipes.get(recipe);
		if (GuiContainerManager.shouldShowTooltip(guiRecipe)) {
			Point mouse = GuiDraw.getMousePosition();
			Point offset = guiRecipe.getRecipePosition(recipe);
			Point relMouse = new Point(mouse.x - (guiRecipe.width - 176) / 2 - offset.x, mouse.y - (guiRecipe.height - 166) / 2 - offset.y);

			currenttip = this.provideTooltip(guiRecipe, currenttip, crecipe, relMouse);
		}
		return currenttip;
	}

	@Override
	public List<String> handleItemTooltip(GuiRecipe guiRecipe, ItemStack itemStack, List<String> currenttip, int recipe) {
		super.handleItemTooltip(guiRecipe, itemStack, currenttip, recipe);
		CachedBaseRecipe crecipe = (CachedBaseRecipe) this.arecipes.get(recipe);
		Point mouse = GuiDraw.getMousePosition();
		Point offset = guiRecipe.getRecipePosition(recipe);
		Point relMouse = new Point(mouse.x - (guiRecipe.width - 176) / 2 - offset.x, mouse.y - (guiRecipe.height - 166) / 2 - offset.y);

		currenttip = this.provideItemTooltip(guiRecipe, itemStack, currenttip, crecipe, relMouse);
		return currenttip;
	}

	public List<String> provideTooltip(GuiRecipe guiRecipe, List<String> currenttip, CachedBaseRecipe crecipe, Point relMouse) {
		if (crecipe.getFluidTanks() != null) {
			for (PositionedFluidTank tank : crecipe.getFluidTanks()) {
				if (tank.position.contains(relMouse)) {
					tank.handleTooltip(currenttip);
				}
			}
		}
		return currenttip;
	}

	public List<String> provideItemTooltip(GuiRecipe guiRecipe, ItemStack itemStack, List<String> currenttip, CachedBaseRecipe crecipe, Point relMouse) {
		for (PositionedStack stack : crecipe.getIngredients()) {
			if (stack instanceof PositionedStackAdv && ((PositionedStackAdv) stack).getRect().contains(relMouse))
				currenttip = ((PositionedStackAdv) stack).handleTooltip(guiRecipe, currenttip);
		}
		for (PositionedStack stack : crecipe.getOtherStacks()) {
			if (stack instanceof PositionedStackAdv && ((PositionedStackAdv) stack).getRect().contains(relMouse))
				currenttip = ((PositionedStackAdv) stack).handleTooltip(guiRecipe, currenttip);
		}
		PositionedStack stack = crecipe.getResult();
		if (stack instanceof PositionedStackAdv && ((PositionedStackAdv) stack).getRect().contains(relMouse))
			currenttip = ((PositionedStackAdv) stack).handleTooltip(guiRecipe, currenttip);
		return currenttip;
	}

	@Override
	public boolean keyTyped(GuiRecipe gui, char keyChar, int keyCode, int recipe) {
		if (keyCode == NEIClientConfig.getKeyBinding("gui.recipe")) {
			if (this.transferFluidTank(gui, recipe, false))
				return true;
		} else if (keyCode == NEIClientConfig.getKeyBinding("gui.usage")) {
			if (this.transferFluidTank(gui, recipe, true))
				return true;
		}
		return super.keyTyped(gui, keyChar, keyCode, recipe);
	}

	@Override
	public boolean mouseClicked(GuiRecipe gui, int button, int recipe) {
		if (button == 0) {
			if (this.transferFluidTank(gui, recipe, false))
				return true;
		} else if (button == 1) {
			if (this.transferFluidTank(gui, recipe, true))
				return true;
		}
		return super.mouseClicked(gui, button, recipe);
	}

	public static FluidStack getFluidStackFromItemStack(ItemStack stack) {
		if (stack != null) {
			if (stack.getItem() instanceof IFluidContainerItem) {
				IFluidContainerItem ctr = (IFluidContainerItem) stack.getItem();
				return ctr.getFluid(stack);
			} else if (FluidContainerRegistry.isFilledContainer(stack))
				return FluidContainerRegistry.getFluidForFilledItem(stack);
			else if (stack.getItem() instanceof ItemBlock) {
				Block b = Block.getBlockFromItem(stack.getItem());
				if (b != null && b instanceof IFluidBlock && ((IFluidBlock) b).getFluid() != null)
					return new FluidStack(((IFluidBlock) b).getFluid(), 1000);
			}
		}
		return null;
	}

	public abstract class CachedBaseRecipe extends CachedRecipe {

		public List<PositionedFluidTank> getFluidTanks() {
			List<PositionedFluidTank> tanks = new ArrayList<PositionedFluidTank>();
			PositionedFluidTank tank = this.getFluidTank();
			if (tank != null)
				tanks.add(tank);
			return tanks;
		}

		public PositionedFluidTank getFluidTank() {
			return null;
		}

		public void drawUpdate() {

		}
	}

	public class PositionedStackAdv extends PositionedStack {

		private List<String> tooltip = new ArrayList<String>();

		public PositionedStackAdv(Object object, int x, int y) {
			super(object, x, y);
		}

		public PositionedStackAdv(Object object, int x, int y, List<String> tooltip) {
			super(object, x, y);
			this.addToTooltip(tooltip);
		}

		public Rectangle getRect() {
			return new Rectangle(this.relx - 1, this.rely - 1, 18, 18);
		}

		public List<String> handleTooltip(GuiRecipe guiRecipe, List<String> currenttip) {
			if (!this.tooltip.isEmpty()) {
				for (String tip : this.tooltip)
					currenttip.add(tip);
			}
			return currenttip;
		}

		public PositionedStackAdv addToTooltip(List<String> lines) {
			for (String tip : lines)
				this.tooltip.add(tip);
			return this;
		}

		public PositionedStackAdv addToTooltip(String line) {
			this.tooltip.add(line);
			return this;
		}
	}

	public class PositionedFluidTank {

		public FluidTank tank;
		public Rectangle position;
		public String overlayTexture;
		public Point overlayTexturePos;
		public boolean flowingTexture = false;
		public boolean showAmount = false;
		public boolean perTick = false;

		public PositionedFluidTank(FluidTank tank, Rectangle position, String overlayTexture, Point overlayTexturePos) {
			this.position = position;
			this.tank = tank;
			this.overlayTexture = overlayTexture;
			this.overlayTexturePos = overlayTexturePos;
		}

		public PositionedFluidTank(FluidTank tank, Rectangle position) {
			this(tank, position, null, null);
		}

		public PositionedFluidTank(FluidStack fluid, int capacity, Rectangle position, String overlayTexture, Point overlayTexturePos) {
			this(new FluidTank(fluid != null ? fluid.copy() : null, capacity), position, overlayTexture, overlayTexturePos);
		}

		public PositionedFluidTank(FluidStack fluid, int capacity, Rectangle position) {
			this(fluid, capacity, position, null, null);
		}

		public PositionedFluidTank setUseFlowingTexture(boolean useFlowingTexture) {
			this.flowingTexture = useFlowingTexture;
			return this;
		}

		public PositionedFluidTank setShowAmount(boolean showAmount) {
			this.showAmount = showAmount;
			return this;
		}

		public PositionedFluidTank setPerTick(boolean perTick) {
			this.perTick = perTick;
			return this;
		}

		public List<String> handleTooltip(List<String> currenttip) {
			if (this.tank == null || this.tank.getFluid() == null || this.tank.getFluid().getFluid() == null || this.tank.getFluid().amount <= 0)
				return currenttip;
			currenttip.add(this.tank.getFluid().getLocalizedName());
			if (this.showAmount)
				currenttip.add(EnumChatFormatting.GRAY.toString() + this.tank.getFluid().amount + (this.perTick ? " mB/t" : " mB"));
			return currenttip;
		}

		public boolean transfer(boolean usage) {
			if (this.tank.getFluid() != null && this.tank.getFluid().amount > 0) {
				if (usage) {
					if (!GuiUsageRecipe.openRecipeGui("liquid", new Object[]{this.tank.getFluid()}))
						return false;
				} else {
					if (!GuiCraftingRecipe.openRecipeGui("liquid", new Object[]{this.tank.getFluid()}))
						return false;
				}
				return true;
			}
			return false;
		}

		public void draw() {
			if (this.tank == null || this.tank.getFluid() == null || this.tank.getFluid().getFluid() == null || this.tank.getFluid().amount <= 0)
				return;
			IIcon fluidIcon = null;
			if (this.flowingTexture && this.tank.getFluid().getFluid().getFlowingIcon() != null)
				fluidIcon = this.tank.getFluid().getFluid().getFlowingIcon();
			else if (this.tank.getFluid().getFluid().getStillIcon() != null)
				fluidIcon = this.tank.getFluid().getFluid().getStillIcon();
			else
				return;

			GuiDraw.changeTexture(RenderUtils.MC_BLOCK_SHEET);
			int color = this.tank.getFluid().getFluid().getColor(this.tank.getFluid());
			GL11.glColor3ub((byte) (color >> 16 & 0xFF), (byte) (color >> 8 & 0xFF), (byte) (color & 0xFF));
			GL11.glDisable(GL11.GL_BLEND);

			int amount = Math.max(Math.min(this.position.height, this.tank.getFluid().amount * this.position.height / this.tank.getCapacity()), 1);
			int posY = this.position.y + this.position.height - amount;

			for (int i = 0; i < this.position.width; i += 16) {
				for (int j = 0; j < amount; j += 16) {
					int drawWidth = Math.min(this.position.width - i, 16);
					int drawHeight = Math.min(amount - j, 16);

					int drawX = this.position.x + i;
					int drawY = posY + j;

					double minU = fluidIcon.getMinU();
					double maxU = fluidIcon.getMaxU();
					double minV = fluidIcon.getMinV();
					double maxV = fluidIcon.getMaxV();

					Tessellator tessellator = Tessellator.instance;
					tessellator.startDrawingQuads();
					tessellator.addVertexWithUV(drawX, drawY + drawHeight, 0, minU, minV + (maxV - minV) * drawHeight / 16F);
					tessellator.addVertexWithUV(drawX + drawWidth, drawY + drawHeight, 0, minU + (maxU - minU) * drawWidth / 16F, minV + (maxV - minV) * drawHeight / 16F);
					tessellator.addVertexWithUV(drawX + drawWidth, drawY, 0, minU + (maxU - minU) * drawWidth / 16F, minV);
					tessellator.addVertexWithUV(drawX, drawY, 0, minU, minV);
					tessellator.draw();
				}
			}

			GL11.glEnable(GL11.GL_BLEND);

			if (this.overlayTexture != null && this.overlayTexturePos != null) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GuiDraw.changeTexture(this.overlayTexture);
				GuiDraw.drawTexturedModalRect(this.position.x, this.position.y, this.overlayTexturePos.x, this.overlayTexturePos.y, this.position.width, this.position.height);
			}
		}

	}
}
