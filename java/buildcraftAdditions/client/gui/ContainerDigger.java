package buildcraftAdditions.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import buildcraft.core.gui.BuildCraftContainer;
import buildcraft.core.gui.slots.SlotOutput;
import buildcraft.core.gui.slots.SlotValidated;
import buildcraftAdditions.entities.TileFluidicCompressor;
import buildcraftAdditions.items.ItemMegaDigger;

public class ContainerDigger extends Container{
	
	IInventory playerIInventory;
	ItemMegaDigger digger;

	public ContainerDigger(InventoryPlayer inventory, ItemMegaDigger digger) {
		this.digger = digger;
		this.playerIInventory = inventory;
		
		this.addSlotToContainer(new Slot(digger, 0, 60, 29));
		this.addSlotToContainer(new SlotValidated (digger, 1, 78, 29));
		this.addSlotToContainer(new SlotValidated(digger, 2, 96, 29));
		
		for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(new Slot(inventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 71 + inventoryRowIndex * 18));
            }
        }
		for (int hotbbarIndex = 0; hotbbarIndex < 9; ++hotbbarIndex)
        {
            this.addSlotToContainer(new Slot(inventory, hotbbarIndex, 8 + hotbbarIndex * 18, 129));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return digger.isUseableByPlayer(var1);
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex){
        ItemStack newItemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()){
            ItemStack stack = slot.getStack();
            newItemStack = stack.copy();
            if (slotIndex < 3){
                if (!this.mergeItemStack(stack, 3, inventorySlots.size(), false)){
                    return null;
                }
            } else if (!this.mergeItemStack(stack, 0, 3, false)){
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }
        digger.save();
        return newItemStack;
    }
	
	public void onContainerClosed(EntityPlayer player){
        super.onContainerClosed(player);

        if (!player.worldObj.isRemote) {
        	digger.save();
        }
    }

}
