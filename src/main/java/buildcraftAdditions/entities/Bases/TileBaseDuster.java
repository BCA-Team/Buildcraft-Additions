package buildcraftAdditions.entities.Bases;

import buildcraftAdditions.api.IEurekaTileEntity;
import buildcraftAdditions.utils.Eureka;
import buildcraftAdditions.api.DusterRecepies;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileBaseDuster extends TileBase implements IEurekaTileEntity{
    public int progress;
    private String key;

    public TileBaseDuster(String key){
        this.key = key;
    }

    public void makeProgress(EntityPlayer player){
        if (getStackInSlot(0) != null && getDust(getStackInSlot(0)) != null) {
            progress++;
            if (progress == 5) {
                dust();
                progress = 0;
                makeProgress(player, key);
            }
        }
    }

    public abstract void dust();

    public ItemStack getDust(ItemStack stack){
        return DusterRecepies.getOutput(stack);
    }

    @Override
    public void makeProgress(EntityPlayer player, String key) {
        Eureka.makeProgress(player, key);
    }
}
