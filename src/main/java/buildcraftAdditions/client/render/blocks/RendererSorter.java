package buildcraftAdditions.client.render.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.TileItemSorter;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class RendererSorter implements ISimpleBlockRenderingHandler {

	private final FakeBlock fakeBlock = new FakeBlock();

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		fakeBlock.setBlock(block);
		renderer.uvRotateEast = 1;
		renderer.uvRotateWest = 2;
		renderer.uvRotateTop = 2;
		renderer.uvRotateBottom = 1;
		renderer.renderBlockAsItem(fakeBlock, 4, 1.0F);
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (!(te instanceof TileItemSorter))
			return false;
		TileItemSorter tile = (TileItemSorter) te;

		switch (tile.getRotation()) {
			case DOWN:
				renderer.uvRotateSouth = 3;
				renderer.uvRotateNorth = 3;
				renderer.uvRotateEast = 3;
				renderer.uvRotateWest = 3;
				break;
			case NORTH:
				renderer.uvRotateSouth = 1;
				renderer.uvRotateNorth = 2;
				break;
			case SOUTH:
				renderer.uvRotateSouth = 2;
				renderer.uvRotateNorth = 1;
				renderer.uvRotateTop = 3;
				renderer.uvRotateBottom = 3;
				break;
			case WEST:
				renderer.uvRotateEast = 1;
				renderer.uvRotateWest = 2;
				renderer.uvRotateTop = 2;
				renderer.uvRotateBottom = 1;
				break;
			case EAST:
				renderer.uvRotateEast = 2;
				renderer.uvRotateWest = 1;
				renderer.uvRotateTop = 1;
				renderer.uvRotateBottom = 2;
				break;
		}

		boolean ret = renderer.renderStandardBlock(block, x, y, z);
		renderer.uvRotateSouth = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;

		return ret;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return Variables.RenderIDs.SORTER;
	}

	private class FakeBlock extends Block {
		private Block block;

		protected FakeBlock() {
			super(Material.rock);
		}

		public void setBlock(Block block) {
			this.block = block;
		}

		@Override
		public IIcon getIcon(int side, int meta) {
			return block.getIcon(side, meta);
		}
	}
}