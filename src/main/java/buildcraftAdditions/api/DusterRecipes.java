package buildcraftAdditions.api;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import buildcraftAdditions.api.IStackInfo.ItemData;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class DusterRecipes {

    private static final DusterRecipes dusting = new DusterRecipes();

    private final HashMap<IStackInfo, ItemStack> dustingList = new HashMap<IStackInfo, ItemStack>();

    private DusterRecipes() {
    }

    public static DusterRecipes dusting() {
        return dusting;
    }
    
    public void addDusterRecipe(String oreInput, ItemStack output)
    {
    	ArrayList<ItemStack> inputs = OreDictionary.getOres(oreInput);
    	if(inputs != null && inputs.size() > 0)
    	{
    		for(ItemStack stack : inputs)
    		{
    			addDusterRecipe(stack, output);
    		}
    	}
    }
    /**
     * If you make your own IStackInfoClasses then you can do that but make the comparing match with ItemData,
     * because thats what this recipe list is comparing with. As you can see.
     */
    public void addDusterRecipe(IStackInfo par1, ItemStack output)
    {
    	dustingList.put(par1, output);
    }
    
    public void addDusterRecipe(Block input, ItemStack output)
    {
    	addDusterRecipe(Item.getItemFromBlock(input), output);
    }
    
    public void addDusterRecipe(Item input, ItemStack output)
    {
    	ItemData data = new ItemData(input);
    	dustingList.put(data, output);
    }
    //Reason because if modders want to add recipes it makes codes more cleaner because adds new ItemStacks always is longer code.
    //can be removed.
    public void addDusterRecipe(Item input, int inputMeta, ItemStack output)
    {
    	ItemData data = new ItemData(input, inputMeta);
    	dustingList.put(data, output);
    }

    public void addDusterRecipe(ItemStack input, ItemStack output) 
    {
    	ItemData data = new ItemData(input);
    	dustingList.put(data, output);
    }

    public ItemStack getDustingResult(ItemStack input) {
		ItemStack result = dustingList.get(new ItemData(input));
		if (result == null)
			return null;
		return result.copy();
    }

    public boolean hasDustingResult(ItemStack input) {
        return getDustingResult(input) != null;
    }

    public HashMap<IStackInfo, ItemStack> getDustingList() {
        return (HashMap<IStackInfo, ItemStack>) dustingList.clone();
    }

}
