package buildcraftAdditions.items.Tools;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftAdditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftAdditions.wordpress.com/wiki/licensing-stuff/
 */

import buildcraftAdditions.api.IKineticCapsule;
import buildcraftAdditions.config.ConfigurationHandeler;
import buildcraftAdditions.core.InventoryTool;
import buildcraftAdditions.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.List;

public class ItemPoweredBase extends ItemSword {

	public int x, y, z;
	public World world;
	public int storageB1, storageB2, storageB3;
	public double energyB1, energyB2, energyB3;
	EntityPlayer player;
	
	public ItemPoweredBase(){
        super(ToolMaterial.EMERALD);
	}

    @Override
    public boolean isItemTool(ItemStack stack){
        return true;
    }
	
	public void decreaseEnergy(ItemStack stack, double energy, EntityPlayer player){
		if (energyB1 - energy <= 0){
			energy -= energyB1;
			energyB1=0;
		}
		if (energyB1 > energy){
			energyB1 -= energy;
			energy = 0;
		}
		if (energyB2 - energy <= 0){
			energy -= energyB1;
			energyB2=0;
		}
		if (energyB2 > energy){
			energyB2 -= energy;
			energy = 0;
		}
		if (energyB3 - energy <= 0){
			energy -= energyB3;
			energyB3=0;
		}
		if (energyB3 > energy){
			energyB3 -= energy;
		}
		writeBateries(stack, player);
		readBateries(stack, player);
		
	}
	
	
	public double getEnergy(){
		return energyB1 + energyB2 + energyB3;
	}
	
	
	public int getCapacity(){
		return storageB1 + storageB2 + storageB3;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player){
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = player.worldObj;
		this.player = player;
		readBateries(stack, player);
		if (getEnergy() <= world.getBlock(x, y, z).getBlockHardness(world, x, y, z)) {
			player.addChatComponentMessage(new ChatComponentText(Utils.localize("kineticTool.outOfPower")));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity){
		decreaseEnergy(stack, (block.getBlockHardness(world, x, y, z) * (ConfigurationHandeler.powerDifficultyModifiers[world.difficultySetting.getDifficultyId()]) * ConfigurationHandeler.basePowerModifier), player);

		return false;
	}
	
	
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean visible) {
		readBateries(stack, player);
		list.add("I'M BROKEN, PLEASE RIGHT CLICK ME TO FIX ME");
	}

	
	public static IInventory getInventory(EntityPlayer player) {
		ItemStack tool;
		IInventory toolInventory = null;
		tool = player.getCurrentEquippedItem();

		if (tool != null && tool.getItem() instanceof ItemPoweredBase) {
			toolInventory = new InventoryTool(player, tool);
		}

		return toolInventory;
	}
	
	public static IInventory getInventory(EntityPlayer player, ItemStack stack) {
		IInventory toolInventory = null;

		if (stack != null && stack.getItem() instanceof ItemPoweredBase)
		{
			toolInventory = new InventoryTool(player, stack);
		}

		return toolInventory;
	}
	
	public void readBateries(ItemStack stack, EntityPlayer player){
		IInventory inventory = getInventory(player, stack);
		inventory.openInventory();
		IKineticCapsule battery = null;
		ItemStack batteryStack = inventory.getStackInSlot(0);
		storageB1 = 0;
		storageB2 = 0;
		storageB3 = 0;
		energyB1 = 0;
		energyB2 = 0;
		energyB3 = 0;
		if (batteryStack != null){
			battery = (IKineticCapsule) batteryStack.getItem();
			storageB1 = battery.getCapacity();
			energyB1 = battery.getEnergy(batteryStack);
			}
		batteryStack = inventory.getStackInSlot(1);
		if (batteryStack != null){
			battery = (IKineticCapsule) batteryStack.getItem();
			storageB2 += battery.getCapacity();
			energyB2 = battery.getEnergy(batteryStack);
			}
		batteryStack = inventory.getStackInSlot(2);
		if (batteryStack != null){
			battery = (IKineticCapsule) batteryStack.getItem();
			storageB3 = battery.getCapacity();
			energyB3 = battery.getEnergy(batteryStack);
			}
		stack.getItem().setMaxDamage(storageB1 + storageB2 + storageB3);
        stack.getItem().setDamage(stack, (int) (storageB1 + storageB2 + storageB3 - energyB1 - energyB2 - energyB3));
	}
	
	public void writeBateries(ItemStack stack, EntityPlayer player){
		IInventory inventory = getInventory(player, stack);
		inventory.openInventory();
		IKineticCapsule battery = null;
		ItemStack batteryStack = inventory.getStackInSlot(0);
		if (batteryStack != null){
			battery = (IKineticCapsule) batteryStack.getItem();
			battery.setEnergy(batteryStack, energyB1); 
			}
		batteryStack = inventory.getStackInSlot(1);
		if (batteryStack != null){
			battery = (IKineticCapsule) batteryStack.getItem();
			battery.setEnergy(batteryStack, energyB2);
			}
		batteryStack = inventory.getStackInSlot(2);
		if (batteryStack != null){
			battery = (IKineticCapsule) batteryStack.getItem();
			battery.setEnergy(batteryStack, energyB3);
			}
		}
	

}
