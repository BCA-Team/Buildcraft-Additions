package buildcraftAdditions.gui;

import buildcraftAdditions.stuff.TileFluidicCompressor;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		if (!world.blockExists(x, y, z))
			return null;

		TileEntity tile = world.getTileEntity(x, y, z);

		switch (ID) { 
		case 70:
			if (tile instanceof TileFluidicCompressor)
			return new GuiFluidicCompressor(player.inventory, (TileFluidicCompressor) tile);
			}
		return null;	
		}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (!world.blockExists(x, y, z))
			return null;

		TileEntity tile = world.getTileEntity(x, y, z);

		switch (ID) {
		case 70:
			if (tile instanceof TileFluidicCompressor)
			return new ContainerFluidicCompressor(player.inventory, (TileFluidicCompressor) tile);
		}
		return null;
	}

}
