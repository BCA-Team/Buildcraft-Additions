package buildcraftAdditions.items;

import java.util.List;

import javax.swing.text.html.parser.Entity;

import buildcraft.BuildCraftCore;
import buildcraft.core.DefaultProps;
import buildcraft.core.inventory.SimpleInventory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemPoweredBase extends Item implements IInventory{
	
	public int x, y, z;
	public World world;
	private final SimpleInventory inventory = new SimpleInventory(3, "BatteryStorage", 1);
	
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

	@Override
	public int getSizeInventory() {
		return inventory.getSizeInventory();
	}
	@Override
	public ItemStack getStackInSlot(int var1) {
		return inventory.getStackInSlot(var1);
	}
	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		return inventory.decrStackSize(var1, var2);
	}
	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return inventory.getStackInSlotOnClosing(var1);
	}
	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		inventory.setInventorySlotContents(var1, var2);
	}
	@Override
	public String getInventoryName() {
		return inventory.getInventoryName();
	}
	@Override
	public boolean hasCustomInventoryName() {
		return inventory.hasCustomInventoryName();
	}
	@Override
	public int getInventoryStackLimit() {
		return inventory.getInventoryStackLimit();
	}
	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}
	@Override
	public void openInventory() {
		
	}
	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack.getItem() instanceof BatteryBase;
	}

	@Override
	public void markDirty() {
		
	}

}
