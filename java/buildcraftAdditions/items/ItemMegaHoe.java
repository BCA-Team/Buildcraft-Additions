package buildcraftAdditions.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import buildcraftAdditions.core.BuildcraftAdditions;

public class ItemMegaHoe extends ItemPoweredBase{
	
	public ItemMegaHoe(int maxEnergy){
		this.maxStackSize = 1;
		setCreativeTab(BuildcraftAdditions.bcadditions);
		setUnlocalizedName("megaHoe");
		this.setMaxDamage(maxEnergy);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int geenIdee, float hitX, float hitY, float hitZ){
		if ((world.getBlock(x, y, z) == Blocks.dirt || world.getBlock(x, y, z) == Blocks.grass) && getEnergy(stack) >=5){
			world.setBlock(x, y, z, Blocks.farmland);
			decreaseEnergy(stack, 5, player);
			return true;
		}
        return false;
    }

}
