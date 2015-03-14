package buildcraftAdditions.client.render.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class RendererSidedTextures implements ISimpleBlockRenderingHandler {

	public static int RENDER_ID;
	private final FakeBlock fakeBlock = new FakeBlock();

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		fakeBlock.setBlock(block);
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;
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
		ForgeDirection direction = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));

		switch (direction) {
			case DOWN:
				renderer.uvRotateSouth = 3;
				renderer.uvRotateNorth = 3;
				renderer.uvRotateEast = 2;
				renderer.uvRotateWest = 1;
				break;
			case NORTH:
				renderer.uvRotateSouth = 0;
				renderer.uvRotateNorth = 0;
				break;
			case SOUTH:
				renderer.uvRotateTop = 3;
				renderer.uvRotateBottom = 3;
				renderer.uvRotateNorth = 0;
				break;
			case WEST:
				renderer.uvRotateEast = 0;
				renderer.uvRotateWest = 0;
				renderer.uvRotateTop = 2;
				renderer.uvRotateBottom = 1;
				break;
			case EAST:
				renderer.uvRotateEast = 4;
				renderer.uvRotateWest = 0;
				renderer.uvRotateTop = 1;
				renderer.uvRotateBottom = 2;
				break;
			default:
				renderer.uvRotateEast = 1;
				renderer.uvRotateWest = 2;
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
		return RENDER_ID;
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
