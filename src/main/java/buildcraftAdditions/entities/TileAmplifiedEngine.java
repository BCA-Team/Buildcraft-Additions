package buildcraftAdditions.entities;

import buildcraft.BuildCraftEnergy;
import buildcraft.api.fuels.IronEngineCoolant;
import buildcraft.api.fuels.IronEngineFuel;
import buildcraft.core.GuiIds;
import buildcraft.core.IItemPipe;
import buildcraft.core.inventory.SimpleInventory;
import buildcraft.energy.TileEngine;
import buildcraftAdditions.client.gui.ContainerAmplifiedEngine;
import buildcraftAdditions.utils.Utils;
import buildcraftAdditions.items.ItemCanister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileAmplifiedEngine extends TileEngine implements IInventory {

    public static float HEAT_PER_MJ = 0.0023F;
    public static float COOLDOWN_RATE = 0.05F;
    public static int MAX_COOLANT_PER_TICK = 40;
    private int burnTime = 0;
    private IronEngineFuel.Fuel currentFuel = null;
    private int penaltyCooling = 0;
    private boolean lastPowered = false;
    private BiomeGenBase biomeCache;
    private final SimpleInventory inventory = new SimpleInventory(2, "EngineInventory", 1);

    public TileAmplifiedEngine() {

    }

    @Override
    public boolean onBlockActivated(EntityPlayer player, ForgeDirection side) {
        if (player.getCurrentEquippedItem() != null) {
            if (player.getCurrentEquippedItem().getItem() instanceof IItemPipe) {
                return false;
            }
        }
        if (!worldObj.isRemote) {
            player.openGui(BuildCraftEnergy.instance, GuiIds.ENGINE_IRON, worldObj, xCoord, yCoord, zCoord);
        }
        return true;
    }

    @Override
    public ResourceLocation getBaseTexture() {
        return BASE_TEXTURES[2];
    }

    @Override
    public ResourceLocation getChamberTexture() {
        return CHAMBER_TEXTURES[2];
    }

    @Override
    public float getPistonSpeed() {
        if (!worldObj.isRemote) {
            return Math.max(0.07f * getHeatLevel(), 0.01f);
        }
        switch (getEnergyStage()) {
            case BLUE:
                return 0.04F;
            case GREEN:
                return 0.05F;
            case YELLOW:
                return 0.06F;
            case RED:
                return 0.07F;
            default:
                return 0;
        }
    }

    private float getBiomeTempScalar() {
        if (biomeCache == null) {
            biomeCache = worldObj.getBiomeGenForCoords(xCoord, zCoord);
        }
        float tempScalar = biomeCache.temperature - 1.0F;
        tempScalar *= 0.5F;
        tempScalar += 1.0F;
        return tempScalar;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        biomeCache = null;
    }

    @Override
    public boolean isBurning() {
        ItemStack stack = getStackInSlot(0);
        if (stack == null)
            return false;
        if (!(stack.getItem() instanceof ItemCanister))
            return false;
        FluidStack fuel = Utils.getFluidStackFromItemStack(stack);
        return fuel != null && fuel.amount > 0 && penaltyCooling == 0 && isRedstonePowered;
    }

    @Override
    public void burn() {
        ItemStack stack = getStackInSlot(0);
        if (stack == null)
            return;
        if (!(stack.getItem() instanceof ItemCanister))
            return;
        FluidStack fuel = Utils.getFluidStackFromItemStack(stack);
        if (currentFuel == null && fuel != null) {
            currentFuel = IronEngineFuel.getFuelForFluid(fuel.getFluid());
        }

        if (currentFuel == null) {
            return;
        }

        if (penaltyCooling <= 0 && isRedstonePowered) {

            lastPowered = true;

            if (burnTime > 0 || (fuel != null && fuel.amount > 0)) {
                if (burnTime > 0) {
                    burnTime--;
                }
                if (burnTime <= 0) {
                    if (fuel != null) {
                        if (--fuel.amount <= 0) {
                            //no idea what should go here
                        }
                        burnTime = currentFuel.totalBurningTime / FluidContainerRegistry.BUCKET_VOLUME;
                    } else {
                        currentFuel = null;
                        return;
                    }
                }
                currentOutput = currentFuel.powerPerCycle; // Comment out for constant power
                addEnergy(currentFuel.powerPerCycle);
                heat += currentFuel.powerPerCycle * HEAT_PER_MJ * getBiomeTempScalar();
            }
        } else if (penaltyCooling <= 0) {
            if (lastPowered) {
                lastPowered = false;
                penaltyCooling = 10;
                // 10 tick of penalty on top of the cooling
            }
        }
    }

    @Override
    public void engineUpdate() {
        if (heat > MIN_HEAT && (penaltyCooling > 0 || !isRedstonePowered)) {
            heat -= COOLDOWN_RATE;
            coolEngine(MIN_HEAT);
            getEnergyStage();
        } else if (heat > IDEAL_HEAT) {
            coolEngine(IDEAL_HEAT);
        }

        if (heat <= MIN_HEAT && penaltyCooling > 0) {
            penaltyCooling--;
        }

        if (heat <= MIN_HEAT) {
            heat = MIN_HEAT;
        }
    }

    private void coolEngine(float idealHeat) {
        float extraHeat = heat - idealHeat;

        ItemStack stack = getStackInSlot(0);
        if (stack == null)
            return;
        if (!(stack.getItem() instanceof ItemCanister))
            return;
        ItemCanister canister = (ItemCanister) stack.getItem();
        FluidStack coolant = Utils.getFluidStackFromItemStack(stack);
        if (coolant == null) {
            return;
        }

        int coolantAmount = Math.min(MAX_COOLANT_PER_TICK, coolant.amount);
        IronEngineCoolant.Coolant currentCoolant = IronEngineCoolant.getCoolant(coolant);
        if (currentCoolant != null) {
            float cooling = currentCoolant.getDegreesCoolingPerMB(heat);
            cooling /= getBiomeTempScalar();
            if (coolantAmount * cooling > extraHeat) {
                canister.drain(stack, Math.round(extraHeat / cooling), true);
                heat -= extraHeat;
            } else {
                canister.drain(stack, coolantAmount, true);
                heat -= coolantAmount * cooling;
            }
        }
    }

    @Override
    public int getScaledBurnTime(int i) {
        ItemStack stack = getStackInSlot(0);
        if (stack == null)
            return 0;
        if (!(stack.getItem() instanceof ItemCanister))
            return 0;
        ItemCanister canister = (ItemCanister) stack.getItem();
        return canister.getFluid(stack) != null ? (int) (((float) canister.getFluid(stack).amount / (float) canister.getCapacity(stack)) * i): 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        inventory.readFromNBT(data);
        burnTime = data.getInteger("burnTime");
        penaltyCooling = data.getInteger("penaltyCooling");

    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        inventory.writeToNBT(data);
        data.setInteger("burnTime", burnTime);
        data.setInteger("penaltyCooling", penaltyCooling);

    }

    @Override
    public double maxEnergyReceived() {
        return 2000;
    }

    @Override
    public double maxEnergyExtracted() {
        return 500;
    }

    @Override
    public float explosionRange() {
        return 5;
    }

    @Override
    public double getMaxEnergy() {
        return 10000;
    }

    @Override
    public double getCurrentOutput() {
        if (currentFuel == null) {
            return 0;
        }
        return currentFuel.powerPerCycle;
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return inventory.decrStackSize(slot, amount);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return inventory.getStackInSlotOnClosing(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory.setInventorySlotContents(slot, stack);
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
    public boolean isUseableByPlayer(EntityPlayer player) {
        return inventory.isUseableByPlayer(player);
    }

    @Override
    public void openInventory() {
        inventory.openInventory();
    }

    @Override
    public void closeInventory() {
        inventory.closeInventory();
    }

    @Override
    public boolean isItemValidForSlot(int var1, ItemStack var2) {
        return true;
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        super.getGUINetworkData(id, value);
        ItemStack stack = getStackInSlot(0);
        if (stack == null)
            return;
        if (!(stack.getItem() instanceof ItemCanister))
            return;
        ItemCanister canister = (ItemCanister) stack.getItem();
        ItemStack stack2 = getStackInSlot(1);
        if (stack2 == null)
            return;
        if (!(stack2.getItem() instanceof ItemCanister))
            return;
        ItemCanister canister2 = (ItemCanister) stack.getItem();
        switch (id) {
            // Fluid Fuel ID
            case 15:
                if (canister.getFluid(stack) == null) {
                    canister.setFluid(stack, new FluidStack(value, 0));
                } else {
                    canister.getFluid(stack).fluidID = value;
                }
                break;
            // Fluid Coolant ID
            case 16:
                if (canister2.getFluid(stack2) == null) {
                    canister2.setFluid(stack2, new FluidStack(value, 0));
                } else {
                    canister2.getFluid(stack2).fluidID = value;
                }
                break;
            // Fluid Fuel amount
            case 17:
                if (canister.getFluid(stack) == null) {
                    canister.setFluid(stack, new FluidStack(0, value));
                } else {
                    canister.getFluid(stack).amount = value;
                }
                break;
            // Fluid Coolant amount
            case 18:
                if (canister2.getFluid(stack2) == null) {
                    canister2.setFluid(stack2, new FluidStack(0, value));
                } else {
                    canister2.getFluid(stack2).amount = value;
                }
                break;
        }
    }

    public void sendGUINetworkData(ContainerAmplifiedEngine containerEngine, ICrafting iCrafting) {
        ItemStack stack = getStackInSlot(0);
        if (stack == null)
            return;
        if (!(stack.getItem() instanceof ItemCanister))
            return;
        ItemCanister canister = (ItemCanister) stack.getItem();
        ItemStack stack2 = getStackInSlot(1);
        if (stack2 == null)
            return;
        if (!(stack2.getItem() instanceof ItemCanister))
            return;
        ItemCanister canister2 = (ItemCanister) stack.getItem();
        iCrafting.sendProgressBarUpdate(containerEngine, 15, canister.getFluid(stack) != null ? canister.getFluid(stack).fluidID : 0);
        iCrafting.sendProgressBarUpdate(containerEngine, 16, canister2.getFluid(stack2) != null ? canister2.getFluid(stack2).fluidID : 0);
        iCrafting.sendProgressBarUpdate(containerEngine, 17, canister.getFluid(stack) != null ? canister.getFluid(stack).amount : 0);
        iCrafting.sendProgressBarUpdate(containerEngine, 18, canister2.getFluid(stack2) != null ? canister2.getFluid(stack2).amount : 0);
    }

    @Override
    public boolean isActive() {
        return penaltyCooling <= 0;
    }
}
