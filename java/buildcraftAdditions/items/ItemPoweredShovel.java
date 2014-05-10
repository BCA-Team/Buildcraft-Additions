package buildcraftAdditions.items;

import ibxm.Player;
import buildcraftAdditions.core.BuildcraftAdditions;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemPoweredShovel extends Item {
	
	private int x, y, z;
	private World world;
	
	public ItemPoweredShovel(){
		this.maxStackSize = 1;
		this.setCreativeTab(BuildcraftAdditions.bcadditions);
		this.setUnlocalizedName("poweredShovel");
	}
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity){
		decreaseEnergy(stack, block.getBlockHardness(world, x, y, z));
		return true;
	}
	
	public void decreaseEnergy(ItemStack stack, double energy){
		double energyStored = getEnergy(stack);
		energyStored -= energy;
		stack.stackTagCompound.setDouble("energy", Math.round(energyStored));
	}
	
	public void increaseEnergy(ItemStack stack, double energy){
		double energyStored = getEnergy(stack);
		energyStored +=energy;
		stack.stackTagCompound.setDouble("energy", Math.round(energyStored));
	}
	
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta){
		if (getEnergy(stack) >= block.getBlockHardness(world, x, y, z))
			if(block.getHarvestTool(0) == "shovel")
				return 5;
		return 1;
	}
	
	public double getEnergy(ItemStack stack){
		double energy = 0;
		if (stack.stackTagCompound == null){
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setDouble("energy", 0);
		}
		return stack.stackTagCompound.getDouble("energy");
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player){
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = player.worldObj;
		return false;
	}
}
