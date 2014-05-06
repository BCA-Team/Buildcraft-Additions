package buildcraftAdditions.stuff;

import buildcraft.api.transport.PipeWire;
import buildcraft.builders.TileAbstractBuilder;
import buildcraftAdditions.core.BuildcraftAdditions;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMjMeter extends Item {
	
	public IIcon icon;
	
	public ItemMjMeter(){
		super();
		setMaxDamage(0);
		setUnlocalizedName("mjMeter");
		this.setMaxStackSize(1);
		this.setCreativeTab(BuildcraftAdditions.bcadditions);
		
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity ==null)
			return false;
		if (entity instanceof TileAbstractBuilder){
			TileAbstractBuilder machine = (TileAbstractBuilder) entity;
			double energy = machine.energyAvailable();
			//player.addChatMessage(var1);
		}
			
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		par1IconRegister.registerIcon("bcadditions:ItemMjMeter.png");
	}

}
