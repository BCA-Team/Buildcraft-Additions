package buildcraftAdditions.items;

import buildcraftAdditions.core.BuildcraftAdditions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class BatteryBase extends Item{
	
	public BatteryBase(){
		this.maxStackSize = 1;
		setCreativeTab(BuildcraftAdditions.bcadditions);
		this.setMaxDamage(1000);
		this.setUnlocalizedName("batteryBase");
	}
	
	public void decreaseEnergy(ItemStack stack, double energy){
		double energyStored = getEnergy(stack);
		energyStored -= energy;
		if (energyStored < 0)
			energyStored=0;
		stack.stackTagCompound.setDouble("energy", Math.floor(energyStored));
		this.setDamage(stack, (int) (getCapacity() - energyStored));
	}
	
	public void increaseEnergy(ItemStack stack, double energy){
		double energyStored = getEnergy(stack);
		energyStored +=energy;
		stack.stackTagCompound.setDouble("energy", Math.round(energyStored));
		this.setDamage(stack, (int) (getCapacity() - energyStored));
	}
	
	public double getEnergy(ItemStack stack){
		if (stack.stackTagCompound == null){
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setDouble("energy", 0);
		}
		return stack.stackTagCompound.getDouble("energy");
	}
	
	public void setEnergy (ItemStack stack, double energy){
		if (stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setDouble("energy", energy);
	}
	
	public int getCapacity(){
		return 1000;
	}
	
	@Override
	public int getDisplayDamage(ItemStack stack){
		return (int) (getCapacity() - getEnergy(stack));
	}

}
