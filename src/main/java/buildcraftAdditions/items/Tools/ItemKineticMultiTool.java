package buildcraftAdditions.items.Tools;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.UseHoeEvent;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.inventories.InventoryItem;
import buildcraftAdditions.inventories.InventoryKineticMultiTool;
import buildcraftAdditions.items.ItemInventoryPoweredBase;
import buildcraftAdditions.reference.ItemLoader;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemKineticMultiTool extends ItemInventoryPoweredBase {

	private static final Set<Material> effectiveMaterialsDrill = Sets.newHashSet(Material.rock, Material.iron, Material.ice, Material.glass, Material.piston, Material.anvil);
	private static final Set<Block> effectiveBlocksDrill = Sets.newHashSet(Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail, Blocks.anvil);
	private static final Set<Material> effectiveMaterialsDigger = Sets.newHashSet(Material.grass, Material.ground, Material.sand, Material.snow, Material.craftedSnow, Material.clay);
	private static final Set<Block> effectiveBlocksDigger = Sets.newHashSet(Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium);
	private static final Set<Material> effectiveMaterialsChainsaw = Sets.newHashSet(Material.coral, Material.leaves, Material.plants, Material.wood, Material.vine, Material.web);
	private static final Set<Block> effectiveBlocksChainsaw = Sets.newHashSet(Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin);

	@SideOnly(Side.CLIENT)
	private IIcon iconChainsaw, iconDigger, iconDrill, iconHoe;

	public ItemKineticMultiTool() {
		super("kineticMultiTool", "itemKineticMultiTool");
		setNoRepair();
		setFull3D();
	}

	public static Set<ItemStack> getInstalledUpgrades(ItemStack stack) {
		Set<ItemStack> set = Sets.newHashSet();
		InventoryKineticMultiTool inv = new InventoryKineticMultiTool(stack);
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack content = inv.getStackInSlot(i);
			if (content != null)
				set.add(content.copy());
		}
		if (isUpgradeInstalled(stack, "goldStick"))
			set.add(new ItemStack(ItemLoader.goldStick));
		if (isUpgradeInstalled(stack, "diamondStick"))
			set.add(new ItemStack(ItemLoader.diamondStick));
		if (isUpgradeInstalled(stack, "emeraldStick"))
			set.add(new ItemStack(ItemLoader.emeraldStick));
		if (isUpgradeInstalled(stack, "netherStarStick"))
			set.add(new ItemStack(ItemLoader.netherStarStick));
		if (isUpgradeInstalled(stack, "quartzStick"))
			set.add(new ItemStack(ItemLoader.quartzStick));
		if (isUpgradeInstalled(stack, "enderStick"))
			set.add(new ItemStack(ItemLoader.enderStick));
		if (isUpgradeInstalled(stack, "redstoneStick"))
			set.add(new ItemStack(ItemLoader.redstoneStick));
		if (isUpgradeInstalled(stack, "glowstoneStick"))
			set.add(new ItemStack(ItemLoader.glowstoneStick));
		if (isUpgradeInstalled(stack, "slimeStick"))
			set.add(new ItemStack(ItemLoader.slimeStick));
		if (isUpgradeInstalled(stack, "blazeStick"))
			set.add(new ItemStack(ItemLoader.blazeStick));
		if (isUpgradeInstalled(stack, "drill"))
			set.add(new ItemStack(ItemLoader.toolUpgradeDrill));
		if (isUpgradeInstalled(stack, "digger"))
			set.add(new ItemStack(ItemLoader.toolUpgradeDigger));
		if (isUpgradeInstalled(stack, "chainsaw"))
			set.add(new ItemStack(ItemLoader.toolUpgradeChainsaw));
		if (isUpgradeInstalled(stack, "hoe"))
			set.add(new ItemStack(ItemLoader.toolUpgradeHoe));
		if (isUpgradeInstalled(stack, "area"))
			set.add(new ItemStack(ItemLoader.toolUpgradeArea));
		if (isUpgradeInstalled(stack, "silky"))
			set.add(new ItemStack(ItemLoader.toolUpgradeSilky));
		if (isUpgradeInstalled(stack, "fortune1"))
			set.add(new ItemStack(ItemLoader.toolUpgradeFortune1));
		if (isUpgradeInstalled(stack, "fortune2"))
			set.add(new ItemStack(ItemLoader.toolUpgradeFortune2));
		if (isUpgradeInstalled(stack, "fortune3"))
			set.add(new ItemStack(ItemLoader.toolUpgradeFortune3));
		return set;
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

	public static void installStick(ItemStack stack, String stick) {
		if (stack != null && !isUpgradeInstalled(stack, stick)) {
			if (stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setBoolean(stick, true);
			if (!stack.stackTagCompound.hasKey("upgradesAllowed", Constants.NBT.TAG_INT))
				stack.stackTagCompound.setInteger("upgradesAllowed", 1);
			stack.stackTagCompound.setInteger("upgradesAllowed", getAllowedUpgrades(stack) + 1);
		}
	}

	public static boolean isUpgradeInstalled(ItemStack stack, String upgrade) {
		return stack != null && stack.stackTagCompound != null && stack.stackTagCompound.hasKey(upgrade, Constants.NBT.TAG_BYTE) && stack.stackTagCompound.getBoolean(upgrade);
	}

	public static boolean canInstallUpgrade(ItemStack stack, String upgrade) {
		return !("area".equalsIgnoreCase(upgrade) && !isUpgradeInstalled(stack, "drill") && !isUpgradeInstalled(stack, "digger") && !isUpgradeInstalled(stack, "chainsaw") && !isUpgradeInstalled(stack, "hoe")) && !("silky".equalsIgnoreCase(upgrade) && (isUpgradeInstalled(stack, "fortune1") || isUpgradeInstalled(stack, "fortune2") || isUpgradeInstalled(stack, "fortune3") || (!isUpgradeInstalled(stack, "drill") && !isUpgradeInstalled(stack, "digger") && !isUpgradeInstalled(stack, "chainsaw")))) && !("fortune1".equalsIgnoreCase(upgrade) && !isUpgradeInstalled(stack, "drill") && !isUpgradeInstalled(stack, "digger") && !isUpgradeInstalled(stack, "chainsaw")) && !("fortune2".equalsIgnoreCase(upgrade) && (isUpgradeInstalled(stack, "fortune1") || (!isUpgradeInstalled(stack, "drill") && !isUpgradeInstalled(stack, "digger") && !isUpgradeInstalled(stack, "chainsaw")))) && !("fortune3".equalsIgnoreCase(upgrade) && (isUpgradeInstalled(stack, "fortune2") || (!isUpgradeInstalled(stack, "drill") && !isUpgradeInstalled(stack, "digger") && !isUpgradeInstalled(stack, "chainsaw")))) && getAllowedUpgrades(stack) > 0;
	}

	public static void installUpgrade(String upgrade, ItemStack stack) {
		if (stack != null && upgrade != null && !upgrade.isEmpty() && !isUpgradeInstalled(stack, upgrade)) {
			if (stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setBoolean(upgrade, true);
			if ("silky".equalsIgnoreCase(upgrade)) {
				Map map = EnchantmentHelper.getEnchantments(stack);
				if (map == null)
					map = Maps.newLinkedHashMap();
				map.put(Enchantment.silkTouch.effectId, 1);
				EnchantmentHelper.setEnchantments(map, stack);
			} else if ("fortune1".equalsIgnoreCase(upgrade)) {
				Map map = EnchantmentHelper.getEnchantments(stack);
				if (map == null)
					map = Maps.newLinkedHashMap();
				map.put(Enchantment.fortune.effectId, 1);
				EnchantmentHelper.setEnchantments(map, stack);
			} else if ("fortune2".equalsIgnoreCase(upgrade)) {
				Map map = EnchantmentHelper.getEnchantments(stack);
				if (map == null)
					map = Maps.newLinkedHashMap();
				map.put(Enchantment.fortune.effectId, 2);
				EnchantmentHelper.setEnchantments(map, stack);
			} else if ("fortune3".equalsIgnoreCase(upgrade)) {
				Map map = EnchantmentHelper.getEnchantments(stack);
				if (map == null)
					map = Maps.newLinkedHashMap();
				map.put(Enchantment.fortune.effectId, 3);
				EnchantmentHelper.setEnchantments(map, stack);
			}
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
			if (upgrade.equalsIgnoreCase("digger"))
				return "shovel";
			if (upgrade.equalsIgnoreCase("chainsaw"))
				return "axe";
		}
		return null;
	}

	public static String getUpgrade(String harvestTool) {
		if (harvestTool != null) {
			if (harvestTool.equalsIgnoreCase("pickaxe"))
				return "drill";
			if (harvestTool.equalsIgnoreCase("shovel"))
				return "digger";
			if (harvestTool.equalsIgnoreCase("axe"))
				return "chainsaw";
		}
		return null;
	}

	public static Set<Material> getEffectiveMaterials(ItemStack stack) {
		Set<Material> set = Sets.newHashSet();
		if (isUpgradeInstalled(stack, "drill"))
			set.addAll(effectiveMaterialsDrill);
		if (isUpgradeInstalled(stack, "digger"))
			set.addAll(effectiveMaterialsDigger);
		if (isUpgradeInstalled(stack, "chainsaw"))
			set.addAll(effectiveMaterialsChainsaw);
		return set;
	}

	public static Set<Block> getEffectiveBlocks(ItemStack stack) {
		Set<Block> set = Sets.newHashSet();
		if (isUpgradeInstalled(stack, "drill"))
			set.addAll(effectiveBlocksDrill);
		if (isUpgradeInstalled(stack, "digger"))
			set.addAll(effectiveBlocksDigger);
		if (isUpgradeInstalled(stack, "chainsaw"))
			set.addAll(effectiveBlocksChainsaw);
		return set;
	}

	public static float getEfficiency(ItemStack stack, Block block) {
		float f = 1;
		if (isUpgradeInstalled(stack, "drill") && (effectiveMaterialsDrill.contains(block.getMaterial()) || effectiveBlocksDrill.contains(block))) {
			setLastUsedMode(stack, "pickaxe");
			f = ConfigurationHandler.toolEfficiencyPickaxe;
		} else if (isUpgradeInstalled(stack, "chainsaw") && (effectiveMaterialsChainsaw.contains(block.getMaterial()) || effectiveBlocksChainsaw.contains(block))) {
			setLastUsedMode(stack, "axe");
			f = ConfigurationHandler.toolEfficiencyAxe;
		} else if (isUpgradeInstalled(stack, "digger") && (effectiveMaterialsDigger.contains(block.getMaterial()) || effectiveBlocksDigger.contains(block))) {
			setLastUsedMode(stack, "shovel");
			f = ConfigurationHandler.toolEfficiencyShovel;
		}
		return f * (isUpgradeInstalled(stack, "area") ? ConfigurationHandler.toolEfficiencyAreaMultiplier : 1);
	}

	public static boolean isToolEffective(ItemStack stack, Block block, int meta) {
		return stack != null && stack.getItem() != null && stack.getItem().getHarvestLevel(stack, block.getHarvestTool(meta)) > block.getHarvestLevel(meta);
	}

	@Override
	public boolean isItemTool(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote && player.isSneaking())
			player.openGui(BuildcraftAdditions.instance, Variables.Gui.KINETIC_MULTI_TOOL.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
		return stack;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		if (getEnergyStored(stack) <= player.worldObj.getBlock(x, y, z).getBlockHardness(player.worldObj, x, y, z) * ConfigurationHandler.powerDifficultyModifiers[player.worldObj.difficultySetting.getDifficultyId()] * ConfigurationHandler.basePowerModifier) {
			if (!player.worldObj.isRemote)
				player.addChatComponentMessage(new ChatComponentTranslation("kineticTool.outOfPower"));
			return true;
		}
		if (!player.isSneaking() && isUpgradeInstalled(stack, "area")) {
			int range = 1;
			Block block = player.worldObj.getBlock(x, y, z);
			if (block.getBlockHardness(player.worldObj, x, y, z) == 0)
				return false;
			if (getEfficiency(stack, block) > 1) {
				MovingObjectPosition mop = getMovingObjectPositionFromPlayer(player.worldObj, player, true);
				if (mop == null)
					return false;
				switch (ForgeDirection.getOrientation(mop.sideHit)) {
					case UP:
					case DOWN:
						for (int xx = x - range; xx <= x + range; xx++) {
							for (int zz = z - range; zz <= z + range; zz++) {
								if (xx == x && zz == z)
									continue;
								block = player.worldObj.getBlock(xx, y, zz);
								if (isToolEffective(stack, block, player.worldObj.getBlockMetadata(xx, y, zz)) && getEfficiency(stack, block) > 1 && onBlockDestroyed(stack, player.worldObj, block, xx, y, zz, player))
									if (canHarvestBlock(block, player.getCurrentEquippedItem()))
										Utils.harvestBlock(player.worldObj, xx, y, zz, player);
							}
						}
						break;
					case NORTH:
					case SOUTH:
						for (int xx = x - range; xx <= x + range; xx++) {
							for (int yy = y - range; yy <= y + range; yy++) {
								if (xx == x && yy == y)
									continue;
								block = player.worldObj.getBlock(xx, yy, z);
								if (isToolEffective(stack, block, player.worldObj.getBlockMetadata(xx, yy, z)) && getEfficiency(stack, block) > 1 && onBlockDestroyed(stack, player.worldObj, block, xx, yy, z, player))
									if (canHarvestBlock(block, player.getCurrentEquippedItem()))
										Utils.harvestBlock(player.worldObj, xx, yy, z, player);
							}
						}
						break;
					case WEST:
					case EAST:
						for (int yy = y - range; yy <= y + range; yy++) {
							for (int zz = z - range; zz <= z + range; zz++) {
								if (yy == y && zz == z)
									continue;
								block = player.worldObj.getBlock(x, yy, zz);
								if (isToolEffective(stack, block, player.worldObj.getBlockMetadata(x, yy, zz)) && getEfficiency(stack, block) > 1 && onBlockDestroyed(stack, player.worldObj, block, x, yy, zz, player))
									if (canHarvestBlock(block, player.getCurrentEquippedItem()))
										Utils.harvestBlock(player.worldObj, x, yy, zz, player);
							}
						}
						break;
				}
			}
		}
		return false;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity) {
		if (getEffectiveMaterials(stack).contains(block.getMaterial()) || getEffectiveBlocks(stack).contains(block)) {
			if (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.isCreativeMode)
				extractEnergy(stack, (int) (block.getBlockHardness(world, x, y, z) * ConfigurationHandler.powerDifficultyModifiers[world.difficultySetting.getDifficultyId()] * ConfigurationHandler.basePowerModifier), false);
			return true;
		}
		return false;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entityLivingBase1, EntityLivingBase entityLivingBase2) {
		if (isUpgradeInstalled(stack, "chainsaw")) {
			setLastUsedMode(stack, "axe");
			extractEnergy(stack, (ConfigurationHandler.entityHitModifier * ConfigurationHandler.powerDifficultyModifiers[entityLivingBase1.worldObj.difficultySetting.getDifficultyId()] * ConfigurationHandler.basePowerModifier), false);
			return true;
		}
		return false;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack, int pass) {
		return false;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.common;
	}

	@Override
	public float func_150893_a(ItemStack stack, Block block) {
		return getEfficiency(stack, block);
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		return isToolEffective(stack, block, meta) ? getEfficiency(stack, block) : super.getDigSpeed(stack, block, meta);
	}

	@Override
	public boolean canHarvestBlock(Block block, ItemStack stack) {
		return func_150893_a(stack, block) > 1;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (!isUpgradeInstalled(stack, "hoe") || getEnergyStored(stack) < ConfigurationHandler.hoeCost)
			return false;
		boolean tilled = false;
		int range = isUpgradeInstalled(stack, "area") ? 1 : 0;
		for (int xx = x - range; xx <= x + range; xx++) {
			for (int zz = z - range; zz <= z + range; zz++) {
				if (player.canPlayerEdit(xx, y, zz, side, stack)) {
					if (getEnergyStored(stack) < ConfigurationHandler.hoeCost)
						return tilled;
					UseHoeEvent event = new UseHoeEvent(player, stack, world, xx, y, zz);
					if (!MinecraftForge.EVENT_BUS.post(event)) {
						if (event.getResult() == Event.Result.ALLOW) {
							extractEnergy(stack, ConfigurationHandler.hoeCost, false);
							setLastUsedMode(stack, "hoe");
							tilled = true;
						} else {
							Block block = world.getBlock(xx, y, zz);

							if (side != 0 && world.getBlock(xx, y + 1, zz).isAir(world, xx, y + 1, zz) && (block == Blocks.grass || block == Blocks.dirt)) {
								Block block1 = Blocks.farmland;
								world.playSoundEffect((double) ((float) xx + 0.5F), (double) ((float) y + 0.5F), (double) ((float) zz + 0.5F), block1.stepSound.getStepResourcePath(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getPitch() * 0.8F);
								if (!world.isRemote)
									world.setBlock(xx, y, zz, block1);
								extractEnergy(stack, ConfigurationHandler.hoeCost, false);
								setLastUsedMode(stack, "hoe");
								tilled = true;
							}
						}
					}
				}
			}
		}
		return tilled;
	}

	@Override
	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
		return false;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		Multimap map = HashMultimap.create();
		if (isUpgradeInstalled(stack, "chainsaw"))
			map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", ConfigurationHandler.entityDamage, 0));
		return map;
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass) {
		return isUpgradeInstalled(stack, getUpgrade(toolClass)) ? ConfigurationHandler.toolHarvestLevel : -1;
	}

	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		Set<String> set = Sets.newHashSet();
		if (isUpgradeInstalled(stack, "drill"))
			set.add(getHarvestTool("drill"));
		if (isUpgradeInstalled(stack, "digger"))
			set.add(getHarvestTool("digger"));
		if (isUpgradeInstalled(stack, "chainsaw"))
			set.add(getHarvestTool("chainsaw"));
		return set;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedTooltips) {
		super.addInformation(stack, player, list, advancedTooltips);
		if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode())) {
			boolean drill = isUpgradeInstalled(stack, "drill");
			boolean digger = isUpgradeInstalled(stack, "digger");
			boolean chainsaw = isUpgradeInstalled(stack, "chainsaw");
			boolean hoe = isUpgradeInstalled(stack, "hoe");
			boolean area = isUpgradeInstalled(stack, "area");
			boolean silky = isUpgradeInstalled(stack, "silky");
			boolean fortune1 = isUpgradeInstalled(stack, "fortune1");
			boolean fortune2 = isUpgradeInstalled(stack, "fortune2");
			boolean fortune3 = isUpgradeInstalled(stack, "fortune3");
			list.add(Utils.localize("tooltip.installed"));
			if (!drill && !digger && !chainsaw && !hoe && !area && !silky && !fortune1 && !fortune2 && !fortune3)
				list.add(Utils.localize("tooltip.none"));
			else {
				if (drill)
					list.add(ItemLoader.toolUpgradeDrill.getItemStackDisplayName(new ItemStack(ItemLoader.toolUpgradeDrill)));
				if (digger)
					list.add(ItemLoader.toolUpgradeDigger.getItemStackDisplayName(new ItemStack(ItemLoader.toolUpgradeDigger)));
				if (chainsaw)
					list.add(ItemLoader.toolUpgradeChainsaw.getItemStackDisplayName(new ItemStack(ItemLoader.toolUpgradeChainsaw)));
				if (hoe)
					list.add(ItemLoader.toolUpgradeHoe.getItemStackDisplayName(new ItemStack(ItemLoader.toolUpgradeHoe)));
				if (area)
					list.add(ItemLoader.toolUpgradeArea.getItemStackDisplayName(new ItemStack(ItemLoader.toolUpgradeArea)));
				if (silky)
					list.add(ItemLoader.toolUpgradeSilky.getItemStackDisplayName(new ItemStack(ItemLoader.toolUpgradeSilky)));
				else if (fortune3)
					list.add(ItemLoader.toolUpgradeFortune3.getItemStackDisplayName(new ItemStack(ItemLoader.toolUpgradeFortune3)));
				else if (fortune2)
					list.add(ItemLoader.toolUpgradeFortune2.getItemStackDisplayName(new ItemStack(ItemLoader.toolUpgradeFortune2)));
				else if (fortune1)
					list.add(ItemLoader.toolUpgradeFortune1.getItemStackDisplayName(new ItemStack(ItemLoader.toolUpgradeFortune1)));
			}

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
	public InventoryItem getInventory(ItemStack stack) {
		return new InventoryKineticMultiTool(stack);
	}


}
