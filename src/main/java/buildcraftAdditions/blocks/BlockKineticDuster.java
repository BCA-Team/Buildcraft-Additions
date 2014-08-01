package buildcraftAdditions.blocks;

import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftTransport;
import buildcraft.core.IItemPipe;
import buildcraftAdditions.tileEntities.TileKineticDuster;
import buildcraftAdditions.utils.Utils;
import buildcraftAdditions.variables.Variables;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eureka.core.EurekaKnowledge;
import eureka.interfaces.IEurekaBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockKineticDuster extends BlockBase implements IEurekaBlock {
	public IIcon bottom, sides[], top;

    public BlockKineticDuster() {
        super(Material.iron);
    }

    @Override
    public boolean isAllowed(EntityPlayer player) {
        return EurekaKnowledge.isUnlocked(player, Variables.DustT2Key2);
    }

    @Override
    public ItemStack[] getComponents() {
        return new ItemStack[]{new ItemStack(Blocks.glass, 3), new ItemStack(BuildCraftTransport.pipeItemsGold, 2), new ItemStack(BuildCraftCore.goldGearItem, 2), new ItemStack(BuildCraftCore.diamondGearItem)};
    }

    @Override
    public String getMessage() {
        return Utils.localize("eureka.missingKnowledge");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int getal) {
        return new TileKineticDuster();
    }

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
		if (player.isSneaking())
			return false;
		if (player.getCurrentEquippedItem() != null) {
			if (player.getCurrentEquippedItem().getItem() instanceof IItemPipe)
				return false;
		}
		TileKineticDuster duster = (TileKineticDuster) world.getTileEntity(x, y, z);
		if (duster != null && duster.getStackInSlot(0) == null && player.getCurrentEquippedItem() != null) {
			ItemStack stack = player.getCurrentEquippedItem().copy();
			stack.stackSize = 1;
			duster.setInventorySlotContents(0, stack);
			player.getCurrentEquippedItem().stackSize--;
			if (player.getCurrentEquippedItem().stackSize <= 0)
				player.setCurrentItemOrArmor(0, null);
		} else {
			if (duster.getStackInSlot(0) != null){
				if (!world.isRemote)
					Utils.dropItemstack(world, x, y, z, duster.getStackInSlot(0));
				duster.setInventorySlotContents(0, null);
			}
		}
		world.markBlockForUpdate(x, y, z);
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
		super.onBlockPlacedBy(world, i, j, k, entityliving, stack);
		if (entityliving instanceof EntityPlayer)
			EurekaKnowledge.eurekaBlockEvent(world, this, i, j, k, (EntityPlayer) entityliving);

		ForgeDirection orientation = Utils.get2dOrientation(entityliving);
		world.setBlockMetadataWithNotify(i, j, k, orientation.getOpposite().ordinal(), 1);

	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side){
		switch (side) {
			case 0:
				return bottom;
			case 1:
				return top;
		}
		TileKineticDuster duster = (TileKineticDuster) access.getTileEntity(x, y, z);
		return sides[duster.progressStage];
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		// If no metadata is set, then this is an icon.


		switch (side) {
			case 0:
				return bottom;
			case 1:
				return top;
		}
		return sides[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		sides = new IIcon[4];
		sides[0] = par1IconRegister.registerIcon("bcadditions:dusterKineticSide0");
		sides[1] = par1IconRegister.registerIcon("bcadditions:dusterKineticSide1");
		sides[2] = par1IconRegister.registerIcon("bcadditions:dusterKineticSide2");
		sides[3] = par1IconRegister.registerIcon("bcadditions:dusterKineticSide3");
		bottom = par1IconRegister.registerIcon("bcadditions:dusterKineticBottom");
		top= par1IconRegister.registerIcon("bcadditions:dusterKineticTop");
	}
}
