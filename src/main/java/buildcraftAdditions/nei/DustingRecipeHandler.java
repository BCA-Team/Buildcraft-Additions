package buildcraftAdditions.nei;

import buildcraftAdditions.api.DusterRecipes;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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
            HashMap<ItemStack, ItemStack> recipes = DusterRecipes.dusting().getDustingList();
            for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
                arecipes.add(new CachedDustingRecipe(recipe.getKey(), recipe.getValue()));
        } else
            super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        HashMap<ItemStack, ItemStack> recipes = DusterRecipes.dusting().getDustingList();
        for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getValue(), result))
                arecipes.add(new CachedDustingRecipe(recipe.getKey(), recipe.getValue()));
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        HashMap<ItemStack, ItemStack> recipes = DusterRecipes.dusting().getDustingList();
        for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient)) {
                CachedDustingRecipe cRecipe = new CachedDustingRecipe(recipe.getKey(), recipe.getValue());
                cRecipe.setIngredientPermutation(Arrays.asList(cRecipe.input), ingredient);
                arecipes.add(cRecipe);
            }
    }

    @Override
    public String getGuiTexture() {
        return texture.toString();
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("gui.nei.dusting");
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
