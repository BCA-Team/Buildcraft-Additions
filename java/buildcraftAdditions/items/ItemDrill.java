package buildcraftAdditions.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import buildcraftAdditions.core.BuildcraftAdditions;

public class ItemDrill extends ItemPoweredBase{
	
	public ItemDrill(int maxEnergy){
		this.maxStackSize = 1;
		setCreativeTab(BuildcraftAdditions.bcadditions);
		setUnlocalizedName("drill");
		this.setMaxDamage(maxEnergy);
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta){
		if (getEnergy(stack) >= block.getBlockHardness(world, x, y, z))
			if(block.getHarvestTool(0) == "pickaxe")
				return 40;
		return 1;
	}

}
