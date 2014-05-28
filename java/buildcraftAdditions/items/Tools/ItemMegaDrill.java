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
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import buildcraftAdditions.core.BuildcraftAdditions;
import buildcraftAdditions.core.Variables;

public class ItemMegaDrill extends ItemPoweredBase{
    public IIcon icon;
	
	public ItemMegaDrill(int maxEnergy){
		this.maxStackSize = 1;
		setCreativeTab(BuildcraftAdditions.bcadditions);
		setUnlocalizedName("drill");
		this.setMaxDamage(maxEnergy);
		this.setHarvestLevel("pickaxe", 3);
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta){
		if (getEnergy(stack) >= block.getBlockHardness(world, x, y, z))
			if(block.getHarvestTool(0) == "pickaxe" || block.getMaterial() == Material.iron || block.getMaterial() == Material.rock)
				return 40;
		return 1;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if (stack.getTagCompound() == null)
			stack.setTagCompound(new NBTTagCompound());
		if (player.isSneaking() && !world.isRemote)
			player.openGui(BuildcraftAdditions.instance, Variables.GuiDrill, world, x, y, z);
		return stack;
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
