package buildcraftAdditions.items;

import net.minecraft.item.ItemStack;
import buildcraftAdditions.core.BuildcraftAdditions;

public class ItemPowerCapsuleTier3 extends BatteryBase {
	
	public ItemPowerCapsuleTier3(){
		this.maxStackSize = 1;
		setCreativeTab(BuildcraftAdditions.bcadditions);
		this.setUnlocalizedName("PowerCapsuleTier3");
		this.setMaxDamage(4000);
	}
	
	@Override
	public int getCapacity(){
		return 4000;
	}
	
	@Override
	public int getDisplayDamage(ItemStack stack){
		return (int) (getCapacity() - getEnergy(stack));
	}

}
