package buildcraftAdditions.items.Tools;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import cofh.api.energy.IEnergyContainerItem;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.reference.Variables;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@Deprecated
public class ItemKineticTool extends ItemPoweredBase implements IEnergyContainerItem {

	@SideOnly(Side.CLIENT)
	public IIcon iconAlt, overlayChainsaw, overlayDigger, overlayDrill, overlayHoe;
	@SideOnly(Side.CLIENT)
	private IIcon iconChainsaw, iconDigger, iconDrill, iconHoe;
	public boolean chainsaw, digger, drill, hoe, goldStick, diamondStick, emeraldStick;
	public int upgradesAllowed;
	String lastMode;


	public ItemKineticTool() {
		super();
		setUnlocalizedName("kineticMultiTool");
		setMaxStackSize(1);
		setFull3D();
		chainsaw = false;
		digger = false;
		drill = false;
		hoe = false;
		upgradesAllowed = 1;
		goldStick = false;
		diamondStick = false;
		emeraldStick = false;
		lastMode = "nothing";
	}

	@Override
	public boolean isItemTool(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (stack.getTagCompound() == null)
			stack.setTagCompound(new NBTTagCompound());
		if (player.isSneaking()) {
			if (!world.isRemote)
				player.openGui(BuildcraftAdditions.instance, Variables.Gui.KINETIC_TOOL.ordinal(), world, x, y, z);
		}
		readUpgrades(stack);
		readBateries(stack);
		showDurabilityBar(stack);
		return stack;
	}

	public boolean isStickInstalled(ItemStack stack, String Stick) {
		readUpgrades(stack);
		if (Stick.equals("goldStick"))
			return goldStick;
		if (Stick.equals("diamondStick"))
			return diamondStick;
		if (Stick.equals("emeraldStick"))
			return emeraldStick;

		return false;
	}

	public void installStick(ItemStack stack, String Stick) {
		readUpgrades(stack);
		if (!isStickInstalled(stack, Stick)) {
			if (Stick.equals("goldStick"))
				goldStick = true;
			if (Stick.equals("diamondStick"))
				diamondStick = true;
			if (Stick.equals("emeraldStick"))
				emeraldStick = true;
		}
		upgradesAllowed++;
		writeUpgrades(stack);
	}

	public boolean isUpgradeInstalled(ItemStack stack, String upgrade) {
		readUpgrades(stack);
		if (upgrade.equals("Chainsaw"))
			return chainsaw;
		if (upgrade.equals("Digger"))
			return digger;
		if (upgrade.equals("Drill"))
			return drill;
		if (upgrade.equals("Hoe"))
			return hoe;
		return false;
	}

	public void readUpgrades(ItemStack stack) {
		if (stack.stackTagCompound == null || !stack.stackTagCompound.hasKey("chainsaw")) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setBoolean("chainsaw", false);
			stack.stackTagCompound.setBoolean("digger", false);
			stack.stackTagCompound.setBoolean("drill", false);
			stack.stackTagCompound.setBoolean("hoe", false);
			stack.stackTagCompound.setInteger("upgradesAllowed", 1);
			stack.stackTagCompound.setBoolean("goldStick", false);
			stack.stackTagCompound.setBoolean("diamondStick", false);
			stack.stackTagCompound.setBoolean("emeraldStick", false);
			stack.stackTagCompound.setString("lastUsedMode", "nothing");
		}
		chainsaw = stack.stackTagCompound.getBoolean("chainsaw");
		digger = stack.stackTagCompound.getBoolean("digger");
		drill = stack.stackTagCompound.getBoolean("drill");
		hoe = stack.stackTagCompound.getBoolean("hoe");
		upgradesAllowed = stack.stackTagCompound.getInteger("upgradesAllowed");
		goldStick = stack.stackTagCompound.getBoolean("goldStick");
		diamondStick = stack.stackTagCompound.getBoolean("diamondStick");
		emeraldStick = stack.stackTagCompound.getBoolean("emeraldStick");
		lastMode = stack.stackTagCompound.getString("lastUsedMode");
	}

	public void writeUpgrades(ItemStack stack) {
		stack.stackTagCompound.setBoolean("chainsaw", chainsaw);
		stack.stackTagCompound.setBoolean("digger", digger);
		stack.stackTagCompound.setBoolean("drill", drill);
		stack.stackTagCompound.setBoolean("hoe", hoe);
		stack.stackTagCompound.setInteger("upgradesAllowed", upgradesAllowed);
		stack.stackTagCompound.setBoolean("goldStick", goldStick);
		stack.stackTagCompound.setBoolean("diamondStick", diamondStick);
		stack.stackTagCompound.setBoolean("emeraldStick", emeraldStick);

	}

	public void setLastUsedMode(ItemStack stack, String string) {
		stack.stackTagCompound.setString("lastUsedMode", string);
		lastMode = string;
	}

	public boolean canInstallUpgrade(ItemStack stack) {
		readUpgrades(stack);
		return upgradesAllowed > 0;
	}

	public void installUpgrade(String upgrade, ItemStack stack) {
		readUpgrades(stack);
		if (!isUpgradeInstalled(stack, upgrade)) {
			if (upgrade.equals("Drill"))
				drill = true;
			if (upgrade.equals("Chainsaw"))
				chainsaw = true;
			if (upgrade.equals("Digger"))
				digger = true;
			if (upgrade.equals("Hoe"))
				hoe = true;
			upgradesAllowed--;
		}
		writeUpgrades(stack);
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		readUpgrades(stack);
		String harvestTool = "nothing";
		if (block.getHarvestTool(0) != null)
			harvestTool = block.getHarvestTool(0);
		if (drill && (harvestTool.equals("pickaxe") || block.getMaterial() == Material.iron || block.getMaterial() == Material.rock)) {
			stack.getItem().setHarvestLevel("pickaxe", 3);
			setLastUsedMode(stack, "pickaxe");
			return 40;
		}
		if (chainsaw && (harvestTool.equals("axe") || block.getMaterial() == Material.leaves || block.getMaterial() == Material.wood || block.getMaterial() == Material.vine)) {
			stack.getItem().setHarvestLevel("axe", 3);
			setLastUsedMode(stack, "axe");
			return 30;
		}
		if (digger && (harvestTool.equals("shovel") || block.getMaterial() == Material.clay || block.getMaterial() == Material.grass || block.getMaterial() == Material.ground || block.getMaterial() == Material.snow || block.getMaterial() == Material.sand || block.getMaterial() == Material.craftedSnow)) {
			stack.getItem().setHarvestLevel("shovel", 3);
			setLastUsedMode(stack, "shovel");
			return 10;
		}
		return 1;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public boolean canHarvestBlock(Block block, ItemStack stack) {
		String harvestTool = "nothing";
		if (block.getHarvestTool(0) != null)
			harvestTool = block.getHarvestTool(0);
		if (drill && (harvestTool.equals("pickaxe") || block.getMaterial() == Material.iron || block.getMaterial() == Material.rock)) {
			stack.getItem().setHarvestLevel("pickaxe", 3);
			setLastUsedMode(stack, "pickaxe");
			return true;
		}
		if (chainsaw && (harvestTool.equals("axe") || block.getMaterial() == Material.leaves || block.getMaterial() == Material.wood || block.getMaterial() == Material.vine)) {
			stack.getItem().setHarvestLevel("axe", 3);
			setLastUsedMode(stack, "axe");
			return true;
		}
		if (digger && (harvestTool.equals("shovel") || block.getMaterial() == Material.clay || block.getMaterial() == Material.grass || block.getMaterial() == Material.ground || block.getMaterial() == Material.snow || block.getMaterial() == Material.sand || block.getMaterial() == Material.craftedSnow)) {
			stack.getItem().setHarvestLevel("shovel", 3);
			setLastUsedMode(stack, "shovel");
			return true;
		}
		return false;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		readBateries(stack);
		if (getCapacity() == 0)
			return 0;
		return (getCapacity() - getEnergy()) / getCapacity();
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int geenIdee, float hitX, float hitY, float hitZ) {
		readUpgrades(stack);
		if (!hoe)
			return false;
		boolean tilted = false;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = z - 1; j <= z + 1; j++) {
				if ((world.getBlock(i, y, j) == Blocks.dirt || world.getBlock(i, y, j) == Blocks.grass) && getEnergy() >= 5) {
					world.setBlock(i, y, j, Blocks.farmland);
					decreaseEnergy(stack, 5);
					setLastUsedMode(stack, "hoe");
					tilted = true;
				}
			}
		}
		return tilted;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean visible) {
		list.add("Deprecated item, please place this in a crafting grid to convert this to the new tool.");
		list.add("All upgrades and the inventory will be saved.");
		list.add("My apologies for this and any issues this might have caused");
	}

	public void setPlayer(EntityPlayer player) {
		this.player = player;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		overlayChainsaw = register.registerIcon("bcadditions:bit_chainsaw");
		overlayDigger = register.registerIcon("bcadditions:bit_digger");
		overlayDrill = register.registerIcon("bcadditions:bit_drill");
		overlayHoe = register.registerIcon("bcadditions:bit_hoe");
		itemIcon = register.registerIcon("bcadditions:base_tool");
		iconAlt = register.registerIcon("bcadditions:base_tool_alt");
		iconChainsaw = register.registerIcon("bcadditions:Chainsaw");
		iconDigger = register.registerIcon("bcadditions:Digger");
		iconDrill = register.registerIcon("bcadditions:Drill");
		iconHoe = register.registerIcon("bcadditions:Hoe");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage) {
		if (lastMode.equals("pickaxe"))
			return iconDrill;
		if (lastMode.equals("axe"))
			return iconChainsaw;
		if (lastMode.equals("shovel"))
			return iconDigger;
		if (lastMode.equals("hoe"))
			return iconHoe;
		return itemIcon;
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		int b1 = energyB1;
		int b2 = energyB2;
		int b3 = energyB3;
		int received = 0;
		if (maxReceive > storageB1 - b1) {
			received += storageB1 - b1;
			b1 = storageB1;
		} else {
			b1 += maxReceive;
			received = maxReceive;
		}
		if (maxReceive - received > storageB2 - b2) {
			received += storageB2 - b3;
			b2 = storageB2;
		} else {
			b2 += maxReceive - received;
			received = maxReceive;
		}
		if (maxReceive - received > storageB3 - b3) {
			received += storageB3 - b3;
			b3 = storageB3;
		} else {
			b3 += maxReceive - received;
			received = maxReceive;
		}
		if (!simulate) {
			energyB1 = b1;
			energyB2 = b2;
			energyB3 = b3;
			writeBateries(container);
		}
		return received;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		readBateries(container);
		return getEnergy();
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return getCapacity();
	}
}
