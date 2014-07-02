package buildcraftAdditions.entities.Bases;

import buildcraftAdditions.api.IEurekaTileEntity;
import buildcraftAdditions.utils.Eureka;
import buildcraftAdditions.api.DusterRecepies;
import buildcraftAdditions.variables.Variables;
import net.minecraft.entity.item.EntityItem;
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

    public void makeProgress(EntityPlayer player){
        if (getStackInSlot(0) != null && getDust(getStackInSlot(0)) != null) {
            progress++;
            if (progress == 5) {
                dust();
                progress = 0;
                makeProgress(player, Variables.DustT1Key);
            }
        }
    }

    public void dust(){
        float f1 = 0.7F;
        double d = (worldObj.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
        double d1 = (worldObj.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
        double d2 = (worldObj.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
        EntityItem itemToDrop = new EntityItem(worldObj, xCoord + d, yCoord + d1, zCoord + d2, getDust(getStackInSlot(0)));
        itemToDrop.delayBeforeCanPickup = 10;
        if (!worldObj.isRemote)
            worldObj.spawnEntityInWorld(itemToDrop);
        setInventorySlotContents(0, null);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public ItemStack getDust(ItemStack stack){
        return DusterRecepies.getOutput(stack);
    }

    @Override
    public void makeProgress(EntityPlayer player, String key) {
        Eureka.makeProgress(player, key);
    }
}
