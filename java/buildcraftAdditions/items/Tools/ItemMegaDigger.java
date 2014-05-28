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
import buildcraftAdditions.core.BuildcraftAdditions;
import buildcraftAdditions.core.Variables;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMegaDigger extends ItemPoweredBase {
    public IIcon icon;
	
	public ItemMegaDigger(){
		this.maxStackSize = 1;
		setCreativeTab(BuildcraftAdditions.bcadditions);
		setUnlocalizedName("poweredShovel");
		this.setHarvestLevel("shovel", 3);
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta){
		if (getEnergy(stack) >= block.getBlockHardness(world, x, y, z))
			if(block.getHarvestTool(0) == "shovel" || block.getMaterial() == Material.clay || block.getMaterial() == Material.grass || block.getMaterial() == Material.ground || block.getMaterial() == Material.snow || block.getMaterial() == Material.sand || block.getMaterial() == Material.craftedSnow)
				return 10;
		return 1;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if (stack.getTagCompound() == null)
			stack.setTagCompound(new NBTTagCompound());
		if (player.isSneaking() && !world.isRemote)
			player.openGui(BuildcraftAdditions.instance, Variables.GuiDigger, world, x, y, z);
		return stack;
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        icon = par1IconRegister.registerIcon("bcadditions:Digger");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return icon;
    }
	
	
	
}
