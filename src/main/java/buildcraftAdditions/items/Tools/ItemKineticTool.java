package buildcraftAdditions.items.Tools;

import buildcraftAdditions.core.BuildcraftAdditions;
import buildcraftAdditions.core.Utils;
import buildcraftAdditions.core.Variables;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

public class ItemKineticTool extends ItemPoweredBase {
    public boolean chainsaw, digger, drill, hoe, goldRod, diamondRod, emeraldRod;
    public int upgradesAllowed;

    public ItemKineticTool(){
        this.setUnlocalizedName("kineticMultiTool");
        this.setCreativeTab(BuildcraftAdditions.bcadditions);
        this.setMaxStackSize(1);
        chainsaw = false;
        digger = false;
        drill = false;
        hoe = false;
        upgradesAllowed = 1;
        goldRod = false;
        diamondRod = false;
        emeraldRod = false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if (stack.getTagCompound() == null)
            stack.setTagCompound(new NBTTagCompound());
        if (player.isSneaking()) {
            if (!world.isRemote)
                player.openGui(BuildcraftAdditions.instance, Variables.GuiKineticTool, world, x, y, z);
        }
        readUpgrades(stack);
        readBateries(stack, player);
        showDurabilityBar(stack);
        return stack;
    }

    public boolean isRodInstalled (ItemStack stack, String rod){
        readUpgrades(stack);
        if (rod.equals("goldRod"))
            return goldRod;
        if (rod.equals("diamondRod"))
            return diamondRod;
        if (rod.equals("emeraldRod"))
            return emeraldRod;
        return false;
    }

    public void installRod(ItemStack stack, String rod){
        readUpgrades(stack);
        if (!isRodInstalled(stack, rod)){
            if (rod.equals("goldRod"))
                goldRod = true;
            if (rod.equals("diamondRod"))
                diamondRod = true;
            if (rod.equals("emeraldRod"))
                emeraldRod = true;
        }
        upgradesAllowed++;
        writeUpgrades(stack);
    }

    public boolean isUpgradeInstalled(ItemStack stack, String upgrade){
        readUpgrades(stack);
        if (upgrade.equals("Chainsaw"))
            return chainsaw;
        if (upgrade.equals("Digger"))
            return digger;
        if (upgrade.equals("Drill"))
            return drill;
        if (upgrade.equals("Hoe"))
            return hoe;
        return false;
    }

    public void readUpgrades (ItemStack stack){
        if (stack.stackTagCompound == null || !stack.stackTagCompound.hasKey("chainsaw")) {
            stack.stackTagCompound = new NBTTagCompound();
            stack.stackTagCompound.setBoolean("chainsaw", false);
            stack.stackTagCompound.setBoolean("digger", false);
            stack.stackTagCompound.setBoolean("drill", false);
            stack.stackTagCompound.setBoolean("hoe", false);
            stack.stackTagCompound.setInteger("upgradesAllowed", 1);
            stack.stackTagCompound.setBoolean("goldRod", false);
            stack.stackTagCompound.setBoolean("diamondRod", false);
            stack.stackTagCompound.setBoolean("emeraldRod", false);
        }
        chainsaw = stack.stackTagCompound.getBoolean("chainsaw");
        digger = stack.stackTagCompound.getBoolean("digger");
        drill = stack.stackTagCompound.getBoolean("drill");
        hoe = stack.stackTagCompound.getBoolean("hoe");
        upgradesAllowed = stack.stackTagCompound.getInteger("upgradesAllowed");
        goldRod = stack.stackTagCompound.getBoolean("goldRod");
        diamondRod = stack.stackTagCompound.getBoolean("diamondRod");
        emeraldRod = stack.stackTagCompound.getBoolean("emeraldRod");
    }

    public void writeUpgrades (ItemStack stack){
        stack.stackTagCompound.setBoolean("chainsaw", chainsaw);
        stack.stackTagCompound.setBoolean("digger", digger);
        stack.stackTagCompound.setBoolean("drill", drill);
        stack.stackTagCompound.setBoolean("hoe", hoe);
        stack.stackTagCompound.setInteger("upgradesAllowed", upgradesAllowed);
        stack.stackTagCompound.setBoolean("goldRod", goldRod);
        stack.stackTagCompound.setBoolean("diamondRod", diamondRod);
        stack.stackTagCompound.setBoolean("emeraldRod", emeraldRod);
    }

    public boolean canInstallUpgrade(ItemStack stack){
        readUpgrades(stack);
        return upgradesAllowed != 0;
    }

    public void installUpgrade(String upgrade, ItemStack stack){
        readUpgrades(stack);
        if (!isUpgradeInstalled(stack, upgrade)){
            if (upgrade.equals("Drill"))
                drill = true;
            if (upgrade.equals("Chainsaw"))
                chainsaw = true;
            if (upgrade.equals("Digger"))
                digger = true;
            if (upgrade.equals("Hoe"))
                hoe = true;
            upgradesAllowed--;
        }
        writeUpgrades(stack);
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        readUpgrades(stack);
        if (getEnergy() >= block.getBlockHardness(world, x, y, z)) {
            if (chainsaw && (block.getHarvestTool(0) == "axe" || block.getMaterial() == Material.leaves || block.getMaterial() == Material.wood || block.getMaterial() == Material.vine)) {
                stack.getItem().setHarvestLevel("axe", 3);
                return 30;
            }
            if (digger && (block.getHarvestTool(0) == "shovel" || block.getMaterial() == Material.clay || block.getMaterial() == Material.grass || block.getMaterial() == Material.ground || block.getMaterial() == Material.snow || block.getMaterial() == Material.sand || block.getMaterial() == Material.craftedSnow)) {
                stack.getItem().setHarvestLevel("shovel", 3);
                return 10;
            }
            if(drill && (block.getHarvestTool(0) == "pickaxe" || block.getMaterial() == Material.iron || block.getMaterial() == Material.rock)) {
                stack.getItem().setHarvestLevel("pickaxe", 3);
                return 40;
            }
        }
            return 1;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int geenIdee, float hitX, float hitY, float hitZ){
        readUpgrades(stack);
        if (!hoe)
            return false;
        boolean tilted = false;
        for (int i = x-1; i <= x+1; i++){
            for (int j = z-1; j <= z+1; j++){
                if ((world.getBlock(i, y, j) == Blocks.dirt || world.getBlock(i, y, j) == Blocks.grass) && getEnergy() >=5) {
                    world.setBlock(i, y, j, Blocks.farmland);
                    decreaseEnergy(stack, 5, player);
                    tilted = true;
                }
            }
        }
        return tilted;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean visible) {
        readBateries(stack, player);
        readUpgrades(stack);
        list.add(Integer.toString((int) getEnergy()) + "/" + Integer.toString(getCapacity()) + " MJ");
        if (chainsaw)
                list.add(Utils.localize("item.toolUpgradeChainsaw.name") + " " + Utils.localize("tooltip.installed"));
        if (digger)
                list.add(Utils.localize("item.toolUpgradeDigger.name") + " " + Utils.localize("tooltip.installed"));
        if (drill)
                list.add(Utils.localize("item.toolUpgradeDrill.name") + " " + Utils.localize("tooltip.installed"));
        if (hoe)
                list.add(Utils.localize("item.toolUpgradeHoe.name") + " " + Utils.localize("tooltip.installed"));
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }
}
