package buildcraftAdditions.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemPoweredBase extends Item{
	
	private int x, y, z;
	private World world;
	
	public ItemPoweredBase(){
		
	}
	
	public void decreaseEnergy(ItemStack stack, double energy){
		double energyStored = getEnergy(stack);
		energyStored -= energy;
		if (energyStored < 0)
			energyStored=0;
		stack.stackTagCompound.setDouble("energy", Math.floor(energyStored));
		this.setDamage(stack, (int) (getCapacity(stack.getUnlocalizedName()) - energyStored));
	}
	
	public void increaseEnergy(ItemStack stack, double energy){
		double energyStored = getEnergy(stack);
		energyStored +=energy;
		stack.stackTagCompound.setDouble("energy", Math.round(energyStored));
		this.setDamage(stack, (int) (getCapacity(stack.getUnlocalizedName()) - energyStored));
	}
	
	public double getEnergy(ItemStack stack){
		double energy = 0;
		if (stack.stackTagCompound == null){
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setDouble("energy", 0);
		}
		return stack.stackTagCompound.getDouble("energy");
	}
	
	public int getCapacity(String name){
		switch(name){
		case "item.poweredShovel": return 4000;
		}
		return 0;
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player){
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = player.worldObj;
		return false;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity){
		decreaseEnergy(stack, block.getBlockHardness(world, x, y, z) * ((world.difficultySetting.getDifficultyId()+40)/2));
		return true;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack){
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean visible) {
		list.add(Double.toString(getEnergy(stack)) + "/" + Integer.toString(getCapacity(stack.getUnlocalizedName())) + " MJ");
	}

}
