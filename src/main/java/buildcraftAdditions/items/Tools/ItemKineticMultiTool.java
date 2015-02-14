package buildcraftAdditions.items.Tools;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.Constants;

import cofh.api.energy.IEnergyContainerItem;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.inventories.InventoryKineticMultiTool;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemKineticMultiTool extends ItemSword implements IEnergyContainerItem {

	@SideOnly(Side.CLIENT)
	private IIcon iconChainsaw, iconDigger, iconDrill, iconHoe;

	public ItemKineticMultiTool() {
		super(ToolMaterial.EMERALD);
		setUnlocalizedName("kineticMultiTool");
		setCreativeTab(BuildcraftAdditions.bcadditions);
		setMaxStackSize(1);
		setFull3D();
		setNoRepair();
	}

	@Override
	public boolean isItemTool(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote && player.isSneaking())
			player.openGui(BuildcraftAdditions.instance, Variables.Gui.KINETIC_MULTI_TOOL, world, (int) player.posX, (int) player.posY, (int) player.posZ);
		return stack;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		double maxEnergy = getMaxEnergyStored(stack);
		if (maxEnergy <= 0)
			return 1;
		return (maxEnergy - getEnergyStored(stack)) / maxEnergy;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		if (getEnergyStored(stack) <= player.worldObj.getBlock(x, y, z).getBlockHardness(player.worldObj, x, y, z)) {
			player.addChatComponentMessage(new ChatComponentTranslation("kineticTool.outOfPower"));
			return true;
		}
		return false;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity) {
		extractEnergy(stack, (int) (block.getBlockHardness(world, x, y, z) * (ConfigurationHandler.powerDifficultyModifiers[world.difficultySetting.getDifficultyId()]) * ConfigurationHandler.basePowerModifier), false);
		return false;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entityLivingBase1, EntityLivingBase entityLivingBase2) {
		extractEnergy(stack, (ConfigurationHandler.entityHitModifier * ConfigurationHandler.powerDifficultyModifiers[entityLivingBase1.worldObj.difficultySetting.getDifficultyId()] * ConfigurationHandler.basePowerModifier), false);
		return true;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.none;
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		String harvestTool = "nothing";
		if (block.getHarvestTool(0) != null)
			harvestTool = block.getHarvestTool(0);
		if (isUpgradeInstalled(stack, "drill") && (harvestTool.equals("pickaxe") || block.getMaterial() == Material.iron || block.getMaterial() == Material.rock)) {
			setLastUsedMode(stack, "pickaxe");
			return 40;
		}
		if (isUpgradeInstalled(stack, "chainsaw") && (harvestTool.equals("axe") || block.getMaterial() == Material.leaves || block.getMaterial() == Material.wood || block.getMaterial() == Material.vine)) {
			setLastUsedMode(stack, "axe");
			return 30;
		}
		if (isUpgradeInstalled(stack, "digger") && (harvestTool.equals("shovel") || block.getMaterial() == Material.clay || block.getMaterial() == Material.grass || block.getMaterial() == Material.ground || block.getMaterial() == Material.snow || block.getMaterial() == Material.sand || block.getMaterial() == Material.craftedSnow)) {
			setLastUsedMode(stack, "shovel");
			return 10;
		}
		return 1;
	}

	@Override
	public boolean canHarvestBlock(Block block, ItemStack stack) {
		String harvestTool = "nothing";
		if (block.getHarvestTool(0) != null)
			harvestTool = block.getHarvestTool(0);
		if (isUpgradeInstalled(stack, "drill") && (harvestTool.equals("pickaxe") || block.getMaterial() == Material.iron || block.getMaterial() == Material.rock)) {
			setLastUsedMode(stack, "pickaxe");
			return true;
		}
		if (isUpgradeInstalled(stack, "chainsaw") && (harvestTool.equals("axe") || block.getMaterial() == Material.leaves || block.getMaterial() == Material.wood || block.getMaterial() == Material.vine)) {
			setLastUsedMode(stack, "axe");
			return true;
		}
		if (isUpgradeInstalled(stack, "digger") && (harvestTool.equals("shovel") || block.getMaterial() == Material.clay || block.getMaterial() == Material.grass || block.getMaterial() == Material.ground || block.getMaterial() == Material.snow || block.getMaterial() == Material.sand || block.getMaterial() == Material.craftedSnow)) {
			setLastUsedMode(stack, "shovel");
			return true;
		}
		return false;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int geenIdee, float hitX, float hitY, float hitZ) {
		if (!isUpgradeInstalled(stack, "hoe"))
			return false;
		boolean tilled = false;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = z - 1; j <= z + 1; j++) {
				if ((world.getBlock(i, y, j) == Blocks.dirt || world.getBlock(i, y, j) == Blocks.grass) && extractEnergy(stack, 5, false) >= 5) {
					world.setBlock(i, y, j, Blocks.farmland);
					extractEnergy(stack, 5, false);
					setLastUsedMode(stack, "hoe");
					tilled = true;
				}
			}
		}
		return tilled;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean visible) {
		list.add(Utils.localizeFormatted("rf.info", getEnergyStored(stack), getMaxEnergyStored(stack)));
		if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode())) {
			if (isUpgradeInstalled(stack, "chainsaw"))
				list.add(Utils.localizeAllFormatted("tooltip.installed", "item.toolUpgradeChainsaw.name"));
			if (isUpgradeInstalled(stack, "digger"))
				list.add(Utils.localizeAllFormatted("tooltip.installed", "item.toolUpgradeDigger.name"));
			if (isUpgradeInstalled(stack, "drill"))
				list.add(Utils.localizeAllFormatted("tooltip.installed", "item.toolUpgradeDrill.name"));
			if (isUpgradeInstalled(stack, "hoe"))
				list.add(Utils.localizeAllFormatted("tooltip.installed", "item.toolUpgradeHoe.name"));

			int upgradesAllowed = getAllowedUpgrades(stack);
			if (upgradesAllowed > 0)
				list.add(Utils.localize("tooltip.upgradesPossible") + ": " + upgradesAllowed);
		} else
			list.add("<" + Utils.localize("tooltip.holdShiftForMoreInfo") + ">");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int pass) {
		String lastMode = getLastUsedMode(stack);
		if (lastMode != null) {
			if (lastMode.equalsIgnoreCase("pickaxe"))
				return iconDrill;
			if (lastMode.equalsIgnoreCase("axe"))
				return iconChainsaw;
			if (lastMode.equalsIgnoreCase("shovel"))
				return iconDigger;
			if (lastMode.equalsIgnoreCase("hoe"))
				return iconHoe;
		}
		return itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack) {
		String lastMode = getLastUsedMode(stack);
		if (lastMode != null) {
			if (lastMode.equalsIgnoreCase("pickaxe"))
				return iconDrill;
			if (lastMode.equalsIgnoreCase("axe"))
				return iconChainsaw;
			if (lastMode.equalsIgnoreCase("shovel"))
				return iconDigger;
			if (lastMode.equalsIgnoreCase("hoe"))
				return iconHoe;
		}
		return itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = register.registerIcon("bcadditions:base_tool");
		iconChainsaw = register.registerIcon("bcadditions:Chainsaw");
		iconDigger = register.registerIcon("bcadditions:Digger");
		iconDrill = register.registerIcon("bcadditions:Drill");
		iconHoe = register.registerIcon("bcadditions:Hoe");
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		InventoryKineticMultiTool inv = new InventoryKineticMultiTool(container);
		int received = 0;
		for (int i = 0; i < inv.getSizeInventory() && maxReceive - received > 0; i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem item = (IEnergyContainerItem) stack.getItem();
				received += item.receiveEnergy(stack, maxReceive - received, simulate);
			}
		}
		inv.writeToNBT();
		return received;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		InventoryKineticMultiTool inv = new InventoryKineticMultiTool(container);
		int extracted = 0;
		for (int i = 0; i < inv.getSizeInventory() && maxExtract - extracted > 0; i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem item = (IEnergyContainerItem) stack.getItem();
				extracted += item.extractEnergy(stack, maxExtract - extracted, simulate);
			}
		}
		inv.writeToNBT();
		return extracted;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		InventoryKineticMultiTool inv = new InventoryKineticMultiTool(container);
		int stored = 0;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem item = (IEnergyContainerItem) stack.getItem();
				stored += item.getEnergyStored(stack);
			}
		}
		inv.writeToNBT();
		return stored;
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		InventoryKineticMultiTool inv = new InventoryKineticMultiTool(container);
		int maxStored = 0;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem item = (IEnergyContainerItem) stack.getItem();
				maxStored += item.getMaxEnergyStored(stack);
			}
		}
		inv.writeToNBT();
		return maxStored;
	}

	public static void setLastUsedMode(ItemStack stack, String mode) {
		if (stack != null) {
			if (stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setString("lastUsedMode", mode);
		}
	}

	public static String getLastUsedMode(ItemStack stack) {
		if (stack != null && stack.stackTagCompound != null && stack.stackTagCompound.hasKey("lastUsedMode", Constants.NBT.TAG_STRING))
			return stack.stackTagCompound.getString("lastUsedMode");
		return null;
	}


	public static boolean isStickInstalled(ItemStack stack, String stick) {
		if (stack != null && stack.stackTagCompound != null && stack.stackTagCompound.hasKey(stick, Constants.NBT.TAG_BYTE))
			return stack.stackTagCompound.getBoolean(stick);
		return false;
	}

	public static void installStick(ItemStack stack, String stick) {
		if (stack != null && !isStickInstalled(stack, stick)) {
			if (stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setBoolean(stick, true);
			if (!stack.stackTagCompound.hasKey("upgradesAllowed", Constants.NBT.TAG_INT))
				stack.stackTagCompound.setInteger("upgradesAllowed", 1);
			stack.stackTagCompound.setInteger("upgradesAllowed", getAllowedUpgrades(stack) + 1);
		}
	}

	public static boolean isUpgradeInstalled(ItemStack stack, String upgrade) {
		if (stack != null && stack.stackTagCompound != null && stack.stackTagCompound.hasKey(upgrade, Constants.NBT.TAG_BYTE))
			return stack.stackTagCompound.getBoolean(upgrade);
		return false;
	}

	public static boolean canInstallUpgrade(ItemStack stack) {
		return getAllowedUpgrades(stack) > 0;
	}

	public static void installUpgrade(String upgrade, ItemStack stack) {
		if (stack != null && !isUpgradeInstalled(stack, upgrade)) {
			if (stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setBoolean(upgrade, true);
			if (stack.getItem() != null)
				stack.getItem().setHarvestLevel(getHarvestTool(upgrade), 3);
			if (!stack.stackTagCompound.hasKey("upgradesAllowed", Constants.NBT.TAG_INT))
				stack.stackTagCompound.setInteger("upgradesAllowed", 1);
			stack.stackTagCompound.setInteger("upgradesAllowed", getAllowedUpgrades(stack) - 1);
		}
	}

	public static int getAllowedUpgrades(ItemStack stack) {
		if (stack != null && stack.stackTagCompound != null && stack.stackTagCompound.hasKey("upgradesAllowed", Constants.NBT.TAG_INT))
			return stack.stackTagCompound.getInteger("upgradesAllowed");
		return 1;
	}

	public static String getHarvestTool(String upgrade) {
		if (upgrade != null) {
			if (upgrade.equalsIgnoreCase("drill"))
				return "pickaxe";
			if (upgrade.equalsIgnoreCase("chainsaw"))
				return "axe";
			if (upgrade.equalsIgnoreCase("digger"))
				return "shovel";
		}
		return null;
	}


}
