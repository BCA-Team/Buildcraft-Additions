package buildcraftAdditions.blocks;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.tileEntities.TileKineticEnergyBufferTier1;
import buildcraftAdditions.utils.EnumPriority;
import buildcraftAdditions.utils.EnumSideStatus;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.SideConfiguration;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockKineticEnergyBufferTier1 extends BlockContainer {
	public IIcon icons[];

	public BlockKineticEnergyBufferTier1() {
		super(Material.iron);
		this.setResistance(2f);
		this.setHardness(2f);
		this.setHarvestLevel(null, 0);
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		icons = new IIcon[10];
		for (int teller = 0; teller < 9; teller++) {
			icons[teller] = RenderUtils.registerIcon(register, "kineticEnergyBuffer" + teller);
		}
		icons[9] = RenderUtils.registerIcon(register, "kineticEnergyBufferCreative");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
		if (world.getTileEntity(x, y, z) instanceof TileKineticEnergyBufferBase)
			player.openGui(BuildcraftAdditions.instance, Variables.Gui.KEB, world, x, y, z);
		return true;
	}

	@Override
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
		if (stack.stackTagCompound != null) {
			stack.stackTagCompound.setInteger("x", x);
			stack.stackTagCompound.setInteger("y", y);
			stack.stackTagCompound.setInteger("z", z);
			world.getTileEntity(x, y, z).readFromNBT(stack.stackTagCompound);
		}
		if (entity instanceof EntityPlayer) {
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileKineticEnergyBufferBase)
				((TileKineticEnergyBufferBase) tileEntity).setOwner(((EntityPlayer) entity).getDisplayName());
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileKineticEnergyBufferTier1();
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
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		super.getSubBlocks(item, tab, list);
		list.add(createCreativeKEB());
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		if (world.getBlockMetadata(x, y, z) == 9) {
			return createCreativeKEB();
		}
		return super.getPickBlock(target, world, x, y, z);
	}
}
