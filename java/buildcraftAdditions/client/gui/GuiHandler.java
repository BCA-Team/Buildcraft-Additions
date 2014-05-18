package buildcraftAdditions.client.gui;

import buildcraftAdditions.entities.TileChargingStation;
import buildcraftAdditions.entities.TileFluidicCompressor;
import buildcraftAdditions.items.ItemMegaDigger;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {


		TileEntity tile = world.getTileEntity(x, y, z);

		switch (ID) { 
		case 70:
			if (tile instanceof TileFluidicCompressor)
			return new GuiFluidicCompressor(player.inventory, (TileFluidicCompressor) tile);
		case 71:
			if (tile instanceof TileChargingStation)
				return new GuiChargingStation(player.inventory, (TileChargingStation) tile);
		case 72: ItemMegaDigger digger = (ItemMegaDigger) player.getCurrentEquippedItem().getItem(); 
			return new GuiDigger(player.inventory, digger, digger.getInventory(player), player.getCurrentEquippedItem(), player);
			}
		return null;	
		}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {

		TileEntity tile = world.getTileEntity(x, y, z);

		switch (ID) {
		case 70:
			if (tile instanceof TileFluidicCompressor)
			return new ContainerFluidicCompressor(player.inventory, (TileFluidicCompressor) tile);
		case 71:
			if (tile instanceof TileChargingStation)
				return new ContainerChargingStation(player.inventory, (TileChargingStation) tile);
		case 72: ItemMegaDigger digger = (ItemMegaDigger) player.getCurrentEquippedItem().getItem();
			return new ContainerDigger(player.inventory, digger, digger.getInventory(player), player.getCurrentEquippedItem(), player);
		}
		return null;
	}

}
