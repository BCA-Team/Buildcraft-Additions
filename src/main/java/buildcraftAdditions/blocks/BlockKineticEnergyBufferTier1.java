package buildcraftAdditions.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.tileEntities.TileKineticEnergyBufferTier1;
import buildcraftAdditions.reference.Variables;
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
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons = new IIcon[9];
		for (int teller = 0; teller < 9; teller++){
			icons[teller] = iconRegister.registerIcon("bcadditions:kineticEnergyBuffer" + teller);
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
		if (world.getTileEntity(x, y, z) instanceof TileKineticEnergyBufferBase)
			player.openGui(BuildcraftAdditions.instance, Variables.GuiKEB, world, x, y, z);
		return true;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta > 8)
			return icons[8];
		return icons[meta];
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
}
