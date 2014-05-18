package buildcraftAdditions.core;

import buildcraftAdditions.items.BatteryBase;
import buildcraftAdditions.items.ItemPoweredBase;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryTool extends InventoryBasic {
	
	EntityPlayer player;
	ItemStack tool;
	boolean reading;

	public InventoryTool(EntityPlayer player, ItemStack stack) {
		super("Tool Inventory", false, 3);
		this.player = player;
		tool = stack;
		if (!hasInventory()){
			createInventory();			
		}
	}
	
	private boolean hasInventory() {
		if (tool.stackTagCompound == null)
			return false;
		return tool.stackTagCompound.getTag("Inventory") != null;
	}
	
	private void createInventory() {
		writeToNBT();
	}
	
	protected void writeToNBT() {
		NBTTagList itemList = new NBTTagList();
		for (int t = 0; t < getSizeInventory(); t++) {
			if (getStackInSlot(t) != null)
			{
				NBTTagCompound slotContent = new NBTTagCompound();
				slotContent.setByte("Slot", (byte) t);
				getStackInSlot(t).writeToNBT(slotContent);
				itemList.appendTag(slotContent);
			}
		}
		
		NBTTagCompound inventory = new NBTTagCompound();
		inventory.setTag("Items", itemList);
		if (tool.stackTagCompound == null)
			tool.stackTagCompound = new NBTTagCompound();
		tool.stackTagCompound.setTag("Inventory", inventory);
	}
	
	@Override
	public void markDirty() {
		super.markDirty();
		if (!reading) {
			saveInventory();
		}
		
	}
	
	public static boolean isInventoryEmpty(IInventory inventory) {
		for (int slot=0; slot<inventory.getSizeInventory(); slot++) {
			if (inventory.getStackInSlot(slot)!=null) {
				return false;
			}
		}
		return true;
	}
	
	public void saveInventory() {
		writeToNBT();
		setNBT();
	}
	
	protected void setNBT() {
		ItemStack stack = player.getCurrentEquippedItem();
		if (stack!=null && stack.getItem() instanceof ItemPoweredBase) {
			stack.setTagCompound(tool.getTagCompound());
		}
	}
	
	@Override
	public void openInventory() {
		loadInventory();
	}
	
	public void loadInventory() {
		readFromNBT();
	}
	
	@Override
	public void closeInventory() {
		saveInventory();
	}
	
	protected void readFromNBT() {
		reading = true;

		NBTTagList itemList = (NBTTagList) ((NBTTagCompound) tool.stackTagCompound.getTag("Inventory")).getTag("Items");
		for (int t = 0; t < itemList.tagCount(); t++) {
			NBTTagCompound slotEntry = itemList.getCompoundTagAt(t);
			int j = slotEntry.getByte("Slot") & 0xff;
			if (j >= 0 && j < getSizeInventory()) {
				setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(slotEntry));
			}
		}
		reading = false;
	}
	
	@Override
	 public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack.getItem() instanceof BatteryBase;
		}

}
