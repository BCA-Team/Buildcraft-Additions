package buildcraftAdditions.items;

import java.util.List;

import javax.swing.text.html.parser.Entity;

import buildcraft.BuildCraftCore;
import buildcraft.core.DefaultProps;
import buildcraft.core.inventory.SimpleInventory;
import buildcraftAdditions.core.InventoryTool;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
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
	public ItemStack stack;
	
	public ItemPoweredBase(){
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
	
	public int getCapacity(){
		return 6000;
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
	public int getDisplayDamage(ItemStack stack){
		return (int) (getCapacity() - getEnergy(stack));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean visible) {
		list.add(Integer.toString((int) getEnergy(stack)) + "/" + Integer.toString(getCapacity()) + " MJ");
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

}
