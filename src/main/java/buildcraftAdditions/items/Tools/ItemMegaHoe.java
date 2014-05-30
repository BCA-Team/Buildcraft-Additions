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
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import buildcraftAdditions.core.BuildcraftAdditions;
import buildcraftAdditions.core.Variables;

public class ItemMegaHoe extends ItemPoweredBase{
    public IIcon icon;
	
	public ItemMegaHoe(int maxEnergy){
		this.maxStackSize = 1;
		setCreativeTab(BuildcraftAdditions.bcadditions);
		setUnlocalizedName("megaHoe");
		this.setMaxDamage(maxEnergy);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int geenIdee, float hitX, float hitY, float hitZ){
        boolean tilted = false;
		for (int i = x-1; i <= x+1; i++){
            for (int j = z-1; j <= z+1; j++){
                if ((world.getBlock(i, y, j) == Blocks.dirt || world.getBlock(i, y, j) == Blocks.grass) && getEnergy(stack) >=5) {
                    world.setBlock(i, y, j, Blocks.farmland);
                    decreaseEnergy(stack, 5, player);
                    tilted = true;
                }
            }
        }
        return tilted;
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if (stack.getTagCompound() == null)
			stack.setTagCompound(new NBTTagCompound());
		if (player.isSneaking() && !world.isRemote)
			player.openGui(BuildcraftAdditions.instance, Variables.GuiHoe, world, x, y, z);
		return stack;
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        icon = par1IconRegister.registerIcon("bcadditions:Hoe");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return icon;
    }

}
