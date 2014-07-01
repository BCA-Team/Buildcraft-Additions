package buildcraftAdditions.entities;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import buildcraft.api.gates.IOverrideDefaultTriggers;
import buildcraft.api.gates.ITrigger;
import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.inventories.CustomInventory;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.ItemFluidContainer;
import buildcraft.api.core.NetworkData;
import buildcraft.api.mj.MjBattery;
import buildcraft.core.TileBuildCraft;
import buildcraft.core.fluids.Tank;
import buildcraft.core.fluids.TankManager;
import buildcraft.core.network.IGuiReturnHandler;
import buildcraft.core.network.PacketGuiReturn;
import buildcraft.core.network.PacketPayload;
import buildcraft.core.network.PacketUpdate;
import buildcraftAdditions.utils.Utils;
import buildcraftAdditions.items.ItemCanister;


public class TileFluidicCompressor extends TileBuildCraft implements ISidedInventory, IFluidHandler, IGuiReturnHandler, IOverrideDefaultTriggers {

    private final CustomInventory inventory = new CustomInventory("FluidicCompressor", 2, 1, this);
    public final int maxLiquid = FluidContainerRegistry.BUCKET_VOLUME * 10;
    @MjBattery(maxCapacity = 800.0, maxReceivedPerCycle = 100.0)
    public double energyStored = 0;
    public Tank tank = new Tank("tank", maxLiquid, this);
    private TankManager tankManager = new TankManager();
    public @NetworkData
    boolean fill;

    public TileFluidicCompressor() {
        tankManager.add(tank);
    }

    @Override
    public void updateEntity() {
        ItemStack itemstack = inventory.getStackInSlot(0);
        if (itemstack != null) {
            ItemFluidContainer item = null;
            Item itemInSlot = itemstack.getItem();
            if (itemInSlot instanceof ItemCanister) {
                item = (ItemCanister) itemstack.getItem();
            }
            if (item != null) {
                int amount = 100;
                if (fill && !tank.isEmpty()) {
                    if (tank.getFluid().amount < 100)
                        amount = tank.getFluid().amount;
                    if (energyStored >= amount) {
                        tank.drain(item.fill(itemstack, new FluidStack(tank.getFluid(), amount), true), true);
                        energyStored = energyStored - amount;
                        FluidStack fluid = Utils.getFluidStackFromItemStack(itemstack);
                        if (fluid != null) {
                            if (getProgress() == 16){
                                if (inventory.getStackInSlot(1) == null) {
                                    inventory.setInventorySlotContents(1, itemstack);
                                    inventory.setInventorySlotContents(0, null);
                                }
                                else if (inventory.getStackInSlot(1).getItem() == inventory.getStackInSlot(0).getItem() && inventory.getStackInSlot(1).stackSize < 4){
                                    inventory.getStackInSlot(1).stackSize++;
                                    inventory.setInventorySlotContents(0, null);
                                }
                            }
                        }
                    }
                } else {
                    amount = 50;
                    if (!fill && !tank.isFull() && Utils.getFluidStackFromItemStack(itemstack) != null) {
                        if (!tank.isEmpty()) {
                            if ((tank.getCapacity() - tank.getFluid().amount) < 1000) {
                                amount = tank.getCapacity() - tank.getFluid().amount;
                            }
                        }
                        if (amount > Utils.getFluidStackFromItemStack(itemstack).amount) {
                            amount = Utils.getFluidStackFromItemStack(itemstack).amount;
                        }
                        tank.fill(item.drain(itemstack, amount, true), true);
                        if (getProgress() == 16){
                            itemstack.getTagCompound().removeTag("Fluid");
                            if (inventory.getStackInSlot(1) == null) {
                                inventory.setInventorySlotContents(1, itemstack);
                                inventory.setInventorySlotContents(0, null);
                            }
                            else if (inventory.getStackInSlot(1).getItem() == inventory.getStackInSlot(0).getItem() && inventory.getStackInSlot(1).stackSize < 4){
                                inventory.getStackInSlot(1).stackSize++;
                                inventory.setInventorySlotContents(0, null);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        inventory.readNBT(nbtTagCompound);
        tankManager.readFromNBT(nbtTagCompound);
        fill = nbtTagCompound.getBoolean("fill");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        inventory.writeNBT(nbtTagCompound);
        tankManager.writeToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("fill", fill);
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slotId) {
        return inventory.getStackInSlot(slotId);
    }

    @Override
    public ItemStack decrStackSize(int slotId, int count) {
        return inventory.decrStackSize(slotId, count);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return inventory.getStackInSlotOnClosing(var1);
    }

    @Override
    public void setInventorySlotContents(int slotId, ItemStack itemstack) {
        inventory.setInventorySlotContents(slotId, itemstack);
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
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this
                && entityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D,
                zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {
        inventory.openInventory();
    }

    @Override
    public void closeInventory() {
        inventory.openInventory();
    }

    @Override
    public boolean isItemValidForSlot(int slotid, ItemStack itemStack) {
        if (itemStack == null)
            return false;
        Item item = itemStack.getItem();
        return slotid == 0 && item instanceof ItemCanister;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource,
                            boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return tank.getFluidType() == fluid;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return tankManager.getTankInfo(from);
    }

    public FluidStack getFluid() {
        return tank.getFluid();
    }

    public int getScaledLiquid(int i) {
        return tank.getFluid() != null ? (int) (((float) this.tank.getFluid().amount / (float) (maxLiquid)) * i)
                : 0;
    }

    @Override
    public PacketPayload getPacketPayload() {
        PacketPayload payload = new PacketPayload(
                new PacketPayload.StreamWriter() {
                    @Override
                    public void writeData(ByteBuf data) {
                        tankManager.writeData(data);
                        data.writeBoolean(fill);
                    }
                });
        return payload;
    }

    @Override
    public void handleUpdatePacket(PacketUpdate packet) throws IOException {
        ByteBuf stream = packet.payload.stream;
        tankManager.readData(stream);
        fill = stream.readBoolean();
    }

    @Override
    public void writeGuiData(ByteBuf data) {
    }

    @Override
    public void readGuiData(ByteBuf data, EntityPlayer player) {
        fill = data.readBoolean();
    }

    public void sendModeUpdatePacket() {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            DataOutputStream data = new DataOutputStream(bytes);
            data.writeBoolean(fill);
            PacketGuiReturn pkt = new PacketGuiReturn(this, bytes.toByteArray());
            pkt.sendPacket();
        } catch (Exception e) {
        }
    }

    public int getProgress() {
        ItemStack itemstack = inventory.getStackInSlot(0);
        if (itemstack == null)
            return 0;
        Item item = itemstack.getItem();
        if (!(item instanceof ItemCanister))
            return 0;
        FluidStack fluidstack = Utils.getFluidStackFromItemStack(itemstack);
        ItemCanister canister = (ItemCanister) itemstack.getItem();
        if (fluidstack == null) {
            if (fill) {
                return 0;
            } else {
                return 0;
            }
        }
        int capacity = canister.getCapacity(itemstack);
        if (fill)
            return (fluidstack.amount * 16)/ capacity;
        return ((capacity - fluidstack.amount) * 16) / capacity;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return Utils.createSlotArray(0, 2);
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return side != 0 && isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return (slot == 1);
    }

    public double getEnergyStored() {
        return energyStored;
    }

    public int getFluidStored() {
        if (tank.getFluid() != null) {
            return tank.getFluid().amount;
        }
        return 0;
    }

    @Override
    public LinkedList<ITrigger> getTriggers() {
        LinkedList<ITrigger> list = new LinkedList<ITrigger>();
        list.add(BuildcraftAdditions.triggerCanAcceptCanister);
        list.add(BuildcraftAdditions.triggerHasEmptyCanister);
        list.add(BuildcraftAdditions.triggerhasFullCanister);
        return list;
    }
}
