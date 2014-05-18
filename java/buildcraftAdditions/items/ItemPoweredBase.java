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
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class ItemPoweredBase extends Item implements IInventory{
	
	public int x, y, z;
	public World world;
	protected ItemStack[] storage;
	public ItemStack stack;
	
	public ItemPoweredBase(){
		storage = new ItemStack[3];
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
		return storage.length;
	}
	@Override
	public ItemStack getStackInSlot(int slot) {
		return storage[slot];
	}
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = getStackInSlot(slot);
        if (stack != null)
        {
            if (stack.stackSize <= amount)
            {
                setInventorySlotContents(slot, null);
            }
            else
            {
                stack = stack.splitStack(amount);
                if (stack.stackSize == 0)
                {
                    setInventorySlotContents(slot, null);
                }
            }
        }

        return stack;
	}
	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (storage[slot] != null)
        {
            ItemStack stack = storage[slot];
            storage[slot] = null;
            return stack;
        }
        else
        {
            return null;
        }
	}
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		storage[slot] = stack;
	}
	@Override
	public String getInventoryName() {
		return "BatteryStorage";
	}
	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}
	@Override
	public int getInventoryStackLimit() {
		return 1;
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
	
	public void readFromNBT(NBTTagCompound nbtTagCompound){
        if (nbtTagCompound != null && nbtTagCompound.hasKey("Items")){
            if (nbtTagCompound.hasKey("Items")){
                NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
                storage = new ItemStack[this.getSizeInventory()];
                for (int i = 0; i < tagList.tagCount(); ++i){
                    NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
                    byte slotIndex = tagCompound.getByte("Slot");
                    if (slotIndex >= 0 && slotIndex < storage.length){
                        storage[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
                    }
                }
            }
        }
    }

    public void writeToNBT(NBTTagCompound nbtTagCompound){
        NBTTagList tagList = new NBTTagList();
        for (int index = 0; index < storage.length; ++index){
            if (storage[index] != null){
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) index);
                storage[index].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbtTagCompound.setTag("Items", tagList);
    }
    
    public void save(){
    	NBTTagCompound tag = stack.getTagCompound();
    	writeToNBT(tag);
    	stack.setTagCompound(tag);
    }

}
