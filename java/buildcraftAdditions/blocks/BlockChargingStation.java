package buildcraftAdditions.blocks;

import buildcraft.core.IItemPipe;
import buildcraftAdditions.core.BuildcraftAdditions;
import buildcraftAdditions.core.Utils;
import buildcraftAdditions.entities.TileChargingStation;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockChargingStation extends BlockContainer {

	public BlockChargingStation() {
		super(Material.iron);
		setHardness(5F);
		setResistance(10F);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileChargingStation();
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
			entityplayer.openGui(BuildcraftAdditions.instance, 71, world, x, y, z);

		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
		super.onBlockPlacedBy(world, i, j, k, entityliving, stack);

		ForgeDirection orientation = Utils.get2dOrientation(entityliving);
		world.setBlockMetadataWithNotify(i, j, k, orientation.getOpposite().ordinal(), 1);
	}

}
