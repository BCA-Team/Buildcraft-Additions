package buildcraftAdditions.blocks;

import buildcraft.core.IItemPipe;
import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.utils.Utils;
import buildcraftAdditions.variables.Variables;
import buildcraftAdditions.entities.TileHeatedFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
public class BlockHeatedFurnace extends BlockContainer {
    public IIcon front, back, sides, top, bottom, frontActivated;
    public boolean isActivated;

    public BlockHeatedFurnace() {
        super(Material.iron);
        setHardness(5F);
        setResistance(10F);
        isActivated = false;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileHeatedFurnace();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
        super.onBlockActivated(world, x, y, z, entityplayer, par6, par7, par8, par9);

        // Drop through if the player is sneaking
        if (entityplayer.isSneaking())
            return false;

        if (entityplayer.getCurrentEquippedItem() != null) {
            if (entityplayer.getCurrentEquippedItem().getItem() instanceof IItemPipe)
                return false;
        }

        if (!world.isRemote)
            entityplayer.openGui(BuildcraftAdditions.instance, Variables.GuiHeatedFurnace, world, x, y, z);

        return true;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block){
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileHeatedFurnace){
            TileHeatedFurnace furnace = (TileHeatedFurnace) tile;
            furnace.updateCoils();
        }
    }

    @Override
    public void onPostBlockPlaced(World world, int x, int y, int z, int meta){
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileHeatedFurnace){
            TileHeatedFurnace furnace = (TileHeatedFurnace) tile;
            furnace.updateCoils();
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
        super.onBlockPlacedBy(world, i, j, k, entityliving, stack);

        ForgeDirection orientation = Utils.get2dOrientation(entityliving);
        world.setBlockMetadataWithNotify(i, j, k, orientation.getOpposite().ordinal(), 1);

    }
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta){
        TileHeatedFurnace heatedFurnace = (TileHeatedFurnace) world.getTileEntity(x, y, z);
        for (int t = 0; t < 2; t++){
            ItemStack stack = heatedFurnace.getStackInSlot(t);
            if (stack != null) {
                heatedFurnace.setInventorySlotContents(t, null);
                Utils.dropItemstack(world, x, y, z, stack);
            }
        }
        super.breakBlock(world, x, y, z, block, meta);

    }


    @Override
    public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side){
        int meta = access.getBlockMetadata(x, y, z);
        if (meta == 0 && side == 3)
            return front;

        if (side == meta && meta > 1) {
            TileHeatedFurnace furnace = (TileHeatedFurnace) access.getTileEntity(x, y, z);
            if (furnace.isCooking) {
                return frontActivated;
            }
            return front;
        }

        switch (side) {
            case 0:
                return bottom;
            case 1:
                return top;
            case 5:
                return back;
            default:
                return sides;
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        // If no metadata is set, then this is an icon.
        if (meta == 0 && side == 3)
            return front;

        if (side == meta && meta > 1)
            return front;

        switch (side) {
            case 0:
                return bottom;
            case 1:
                return top;
            case 5:
                return back;
            default:
                return sides;
        }
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        front = par1IconRegister.registerIcon("bcadditions:furnaceFront");
        frontActivated = par1IconRegister.registerIcon("bcadditions:furnaceFront_on");
        top = par1IconRegister.registerIcon("bcadditions:furnaceTop");
        back = par1IconRegister.registerIcon("bcadditions:furnaceBack");
        bottom = par1IconRegister.registerIcon("bcadditions:furnaceBottom");
        sides = par1IconRegister.registerIcon("bcadditions:furnaceSide");
    }


}
