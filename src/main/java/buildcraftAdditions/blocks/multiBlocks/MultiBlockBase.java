package buildcraftAdditions.blocks.multiBlocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.multiBlocks.MultiBlockPatern;
import buildcraftAdditions.utils.RenderUtils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class MultiBlockBase extends BlockContainer {

	public final char identifier;
	public final MultiBlockPatern patern;
	public final String altTexture;
	@SideOnly(Side.CLIENT)
	private IIcon altIcon;

	public MultiBlockBase(String blockName, char identifier, MultiBlockPatern patern, String textureName, String gameRegistryName) {
		this(blockName, identifier, patern, textureName, "multiBlockSeeInvisible", gameRegistryName);
	}

	public MultiBlockBase(String blockName, char identifier, MultiBlockPatern patern, String textureName, String altTexture, String gameregistryName) {
		super(Material.iron);
		setHardness(5);
		setResistance(10);
		setBlockName(blockName);
		setBlockTextureName("bcadditions:" + textureName);
		setCreativeTab(BuildcraftAdditions.bcadditions);
		this.identifier = identifier;
		this.patern = patern;
		this.altTexture = altTexture;
		GameRegistry.registerBlock(this, gameregistryName);
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		super.registerBlockIcons(register);
		altIcon = RenderUtils.registerIcon(register, altTexture);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return meta != 0 ? altIcon : blockIcon;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		world.scheduleBlockUpdate(x, y, z, this, 80);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if (world.getBlockMetadata(x, y, z) == 0) {
			patern.checkPatern(world, x, y, z);
			world.scheduleBlockUpdate(x, y, z, this, 80);
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(x, y, z);
		return tile != null && tile instanceof IMultiBlockTile && ((IMultiBlockTile) tile).onBlockActivated(player);
	}

	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof IMultiBlockTile)
			((IMultiBlockTile) tile).invalidateMultiblock();
	}

	@Override
	public abstract TileEntity createNewTileEntity(World world, int meta);

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
