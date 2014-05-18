package buildcraftAdditions.items;

import java.util.List;



import buildcraft.BuildCraftCore;
import buildcraft.core.DefaultProps;
import buildcraft.core.inventory.SimpleInventory;
import buildcraftAdditions.core.InventoryTool;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class ItemPoweredBase extends Item {
	
	public int x, y, z;
	public World world;
	private int storageB1, storageB2, storageB3;
	private double energyB1, energyB2, energyB3;
	EntityPlayer player;
	
	public ItemPoweredBase(){
	}
	
	public void decreaseEnergy(ItemStack stack, double energy, EntityPlayer player){
		double energyStored = getEnergy(stack);
		energyStored -= energy;
		if (energyStored < 0)
			energyStored=0;
		
		if (energyB1 - energy < 0){
			energy -= energyB1;
			energyB1=0;
		}
		if (energyB1 > energy){
			energyB1 -= energy;
			energy = 0;
		}
		if (energyB2 - energy < 0){
			energy -= energyB1;
			energyB2=0;
		}
		if (energyB2 > energy){
			energyB2 -= energy;
			energy = 0;
		}
		if (energyB3 - energy < 0){
			energy -= energyB3;
			energyB3=0;
		}
		if (energyB3 > energy){
			energyB3 -= energy;
			energy = 0;
		}
		writeBateries(stack, player);
		readBateries(stack, player);
		
	}
	
	
	public double getEnergy(ItemStack stack){
		return energyB1 + energyB2 + energyB3;
	}
	
	
	public int getCapacity(ItemStack stack){
		return storageB1 + storageB2 + storageB3;
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player){
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = player.worldObj;
		this.player = player;
		return false;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity){
		decreaseEnergy(stack, block.getBlockHardness(world, x, y, z) * ((world.difficultySetting.getDifficultyId()+1)*20), player);
		return true;
	}
	
	
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean visible) {
		readBateries(stack, player);
		list.add(Integer.toString((int) getEnergy(stack)) + "/" + Integer.toString(getCapacity(stack)) + " MJ");
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
		BatteryBase battery = null;
		ItemStack batteryStack = inventory.getStackInSlot(0);
		storageB1 = 0;
		storageB2 = 0;
		storageB3 = 0;
		energyB1 = 0;
		energyB2 = 0;
		energyB3 = 0;
		if (batteryStack != null){
			battery = (BatteryBase) batteryStack.getItem();
			storageB1 = battery.getCapacity();
			energyB1 = battery.getEnergy(batteryStack);
			}
		batteryStack = inventory.getStackInSlot(1);
		if (batteryStack != null){
			battery = (BatteryBase) batteryStack.getItem();
			storageB2 += battery.getCapacity();
			energyB2 = battery.getEnergy(batteryStack);
			}
		batteryStack = inventory.getStackInSlot(2);
		if (batteryStack != null){
			battery = (BatteryBase) batteryStack.getItem();
			storageB3 = battery.getCapacity();
			energyB3 = battery.getEnergy(batteryStack);
			}		
		inventory.closeInventory();
	}
	
	public void writeBateries(ItemStack stack, EntityPlayer player){
		IInventory inventory = getInventory(player, stack);
		inventory.openInventory();
		BatteryBase battery = null;
		ItemStack batteryStack = inventory.getStackInSlot(0);
		if (batteryStack != null){
			battery = (BatteryBase) batteryStack.getItem();
			battery.setEnergy(batteryStack, energyB1); 
			}
		batteryStack = inventory.getStackInSlot(1);
		if (batteryStack != null){
			battery = (BatteryBase) batteryStack.getItem();
			battery.setEnergy(batteryStack, energyB2);
			}
		batteryStack = inventory.getStackInSlot(2);
		if (batteryStack != null){
			battery = (BatteryBase) batteryStack.getItem();
			battery.setEnergy(batteryStack, energyB3);
			}			
	}
	

}
