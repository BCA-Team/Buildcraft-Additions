package buildcraftAdditions.items.Tools;

import buildcraftAdditions.BuildcraftAdditions;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemToolCore extends Item{
    public IIcon icon;

    public ItemToolCore(){
        super();
        this.setUnlocalizedName("toolCore");
        this.setCreativeTab(BuildcraftAdditions.bcadditions);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IIconRegister register){
        icon = register.registerIcon("bcadditions:toolCore");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return icon;
    }
}
