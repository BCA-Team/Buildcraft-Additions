package buildcraftAdditions.blocks;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.api.configurableOutput.EnumPriority;
import buildcraftAdditions.api.configurableOutput.EnumSideStatus;
import buildcraftAdditions.api.configurableOutput.SideConfiguration;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.tileEntities.TileKineticEnergyBufferTier1;
import buildcraftAdditions.utils.PlayerUtils;
import buildcraftAdditions.utils.RenderUtils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockKineticEnergyBufferTier1 extends BlockBase {

	@SideOnly(Side.CLIENT)
	private IIcon icons[];

	public BlockKineticEnergyBufferTier1() {
		super("blockKEBT1");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		icons = new IIcon[10];
		for (int teller = 0; teller < 9; teller++) {
			icons[teller] = RenderUtils.registerIcon(register, "kineticEnergyBuffer" + teller);
		}
		icons[9] = RenderUtils.registerIcon(register, "kineticEnergyBufferCreative");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote)
			player.openGui(BuildcraftAdditions.instance, Variables.Gui.KEB.ordinal(), world, x, y, z);
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta > 9)
			return icons[8];
		return icons[meta];
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof TileKineticEnergyBufferBase)
			((TileKineticEnergyBufferBase) tileEntity).byeBye();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null || !(tile instanceof TileKineticEnergyBufferBase))
			return;
		if (stack.stackTagCompound != null) {
			NBTBase nbtBase = stack.stackTagCompound.copy();
			if (nbtBase instanceof NBTTagCompound) {
				NBTTagCompound tag = (NBTTagCompound) nbtBase;
				tag.setInteger("x", x);
				tag.setInteger("y", y);
				tag.setInteger("z", z);
				tile.readFromNBT(tag);
			}
		}
		if (entity instanceof EntityPlayer)
			((TileKineticEnergyBufferBase) tile).setOwner(PlayerUtils.getPlayerUUID((EntityPlayer) entity));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileKineticEnergyBufferTier1();
	}

	public ItemStack createEmptyKEB() {
		ItemStack stack = new ItemStack(this, 1, 0);
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setBoolean("creative", false);
		stack.stackTagCompound.setInteger("energy", 0);
		stack.stackTagCompound.setInteger("maxEnergy", 3000000);
		stack.stackTagCompound.setInteger("maxInput", 30000);
		stack.stackTagCompound.setInteger("maxOutput", 30000);
		new SideConfiguration().writeToNBT(stack.stackTagCompound);
		return stack;
	}

	public ItemStack createCreativeKEB() {
		ItemStack stack = new ItemStack(this, 1, 9);
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setBoolean("creative", true);
		stack.stackTagCompound.setInteger("energy", 3000000);
		stack.stackTagCompound.setInteger("maxEnergy", 3000000);
		stack.stackTagCompound.setInteger("maxInput", 30000);
		stack.stackTagCompound.setInteger("maxOutput", 30000);
		SideConfiguration configuration = new SideConfiguration();
		configuration.setAllStatus(EnumSideStatus.OUTPUT);
		configuration.setAllPriority(EnumPriority.NORMAL);
		configuration.writeToNBT(stack.stackTagCompound);
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(createEmptyKEB());
		list.add(createCreativeKEB());
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		return world.getBlockMetadata(x, y, z) == 9 ? createCreativeKEB() : super.getPickBlock(target, world, x, y, z, player);
	}
}
