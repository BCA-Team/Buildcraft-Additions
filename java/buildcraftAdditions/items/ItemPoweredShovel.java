package buildcraftAdditions.items;

import ibxm.Player;
import buildcraftAdditions.core.BuildcraftAdditions;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemPoweredShovel extends ItemPoweredBase {
	
	private int x, y, z;
	private World world;
	
	public ItemPoweredShovel(){
		this.maxStackSize = 1;
		this.setCreativeTab(BuildcraftAdditions.bcadditions);
		this.setUnlocalizedName("poweredShovel");
		setCapacity(1000);
		this.setMaxDamage(1000);
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta){
		if (getEnergy(stack) >= block.getBlockHardness(world, x, y, z))
			if(block.getHarvestTool(0) == "shovel")
				return 5;
		return 1;
	}
	
	
	
}
