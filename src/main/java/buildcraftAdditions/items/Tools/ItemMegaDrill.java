package buildcraftAdditions.items.Tools;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import buildcraftAdditions.BuildcraftAdditions;

public class ItemMegaDrill extends ItemPoweredBase{
    public IIcon icon;
	
	public ItemMegaDrill(int maxEnergy){
		this.maxStackSize = 1;
		setUnlocalizedName("drill");
		this.setMaxDamage(maxEnergy);
		this.setHarvestLevel("pickaxe", 3);
	}

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        ItemStack outputStack = new ItemStack (BuildcraftAdditions.kineticTool, 1);
        ItemKineticTool tool = (ItemKineticTool) outputStack.getItem();
        outputStack.stackTagCompound = stack.stackTagCompound;
        tool.installUpgrade("Drill", outputStack);
        tool.writeUpgrades(outputStack);
        tool.readBateries(outputStack, player);
        return outputStack;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        icon = par1IconRegister.registerIcon("bcadditions:Drill");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return icon;
    }

}
